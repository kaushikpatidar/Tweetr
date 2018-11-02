package com.tweetr.dropwizard.application;

import com.tweetr.dropwizard.application.configuration.TweetrAppConfiguration;
import com.tweetr.spring.SpringContextLoaderListener;
import com.tweetr.spring.configuration.TwitterAppSpringConfiguration;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.Path;
import java.util.EnumSet;
import java.util.Map;

public class TweetrApp extends Application<TweetrAppConfiguration> {
    public static void main(String[] args) throws Exception {
        new TweetrApp().run(args);
    }

    @Override
    public String getName() {
        return "TweetrApp";
    }

    @Override
    public void initialize(Bootstrap<TweetrAppConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/html", "/twitter", "tweetrHome.html"));
        bootstrap.addBundle(new AssetsBundle("/assets/css", "/twitter/css", null, "css"));
        bootstrap.addBundle(new AssetsBundle("/assets/js", "/twitter/js", null, "js"));
    }

    @Override
    public void run(TweetrAppConfiguration configuration,
                    Environment environment) {

        AnnotationConfigWebApplicationContext parentContext = new AnnotationConfigWebApplicationContext();
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();

        parentContext.refresh();
        parentContext.getBeanFactory().registerSingleton("configuration", configuration);
        parentContext.registerShutdownHook();
        parentContext.start();

        ctx.setParent(parentContext);
        ctx.register(TwitterAppSpringConfiguration.class);
        ctx.refresh();
        ctx.registerShutdownHook();
        ctx.start();

        Map<String, Object> pathResources = ctx.getBeansWithAnnotation(Path.class);
        pathResources.forEach((key, value) -> environment.jersey().register(value));

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "*");
        cors.setInitParameter("allowedMethods", "GET,HEAD,OPTIONS");

        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        /*environment.addFilter(CrossOriginFilter.class, "/*")
                .setInitParam("allowedOrigins", "*")
                .setInitParam("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin")
                .setInitParam("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");*/

        environment.servlets().addServletListeners(new SpringContextLoaderListener(ctx));
    }

}