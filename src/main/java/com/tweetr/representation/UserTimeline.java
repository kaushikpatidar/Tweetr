package com.tweetr.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserTimeline {
    @JsonProperty
    private List<String> timelineStatusList;

    public UserTimeline() {
    }

    public UserTimeline(List<String> timelineStatusList) {
        this.timelineStatusList = timelineStatusList;
    }

    public List<String> getTimelineStatusList() {
        return timelineStatusList;
    }
}
