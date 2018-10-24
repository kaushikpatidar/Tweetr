package com.tweetr.dropwizard.application;

import com.tweetr.dropwizard.configuration.TweetrAppConfiguration;
import com.tweetr.spring.SpringContextLoaderListener;
import com.tweetr.spring.configuration.TwitterAppSpringConfiguration;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.ws.rs.Path;
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
        for(Map.Entry entry : pathResources.entrySet()) {
            environment.jersey().register(entry.getValue());
        }

        environment.servlets().addServletListeners(new SpringContextLoaderListener(ctx));
    }

}