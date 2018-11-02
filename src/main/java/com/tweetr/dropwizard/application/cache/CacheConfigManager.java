package com.tweetr.dropwizard.application.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.tweetr.model.twitter.TwitterPost;
import com.tweetr.service.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class CacheConfigManager {
    private Logger log = LoggerFactory.getLogger(CacheConfigManager.class);

    @Autowired
    private TwitterService twitterService;

    private static LoadingCache<String, Optional<List<TwitterPost>>> responseCache;

    public CacheConfigManager() {
        initResponseCache();
    }

    public void initResponseCache() {
        if (responseCache == null) {
            responseCache =
                    CacheBuilder.newBuilder()
                            .concurrencyLevel(10)
                            .maximumSize(200)
                            .expireAfterAccess(1, TimeUnit.MINUTES)
                            .recordStats()
                            .build(new CacheLoader<String, Optional<List<TwitterPost>>>() {

                                @Override
                                public Optional<List<TwitterPost>> load(String resource) throws Exception {
                                    log.info("Fetching Response Data from Twitter due to Cache Miss");
                                    try {
                                        return twitterService.getTimelineTwitterPost();
                                    } catch (Exception e) {
                                        throw new Exception("Unable to fetch response from the Cache", e);
                                    }
                                }
                            });
        }
    }

    public Optional<List<TwitterPost>> getObtainTimelineResponseDataFromCache(String key) throws Exception {
        try {
            CacheStats cacheStats = responseCache.stats();
            log.info("CacheStats = {} ", cacheStats);
            return responseCache.get(key);
        } catch (ExecutionException e) {
            throw new Exception("Error Retrieving Elements from the Response Cache", e);
        } catch (Exception e) {
            throw new Exception("Error occured" + e.getMessage(), e);
        }
    }
}
