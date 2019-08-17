package com.examples.videocontroller.controller;


import com.google.common.base.Objects;

/**
 * A simple class representing a video and its URL.
 *
 * @author xinli
 *
 * From Coursera course Building Cloud Service with Java Spring Framework.
 */
public class Video {

    private String name;
    private String url;
    private long duration;

    /**
     * Class constructor.
     */
    public Video() {}

    /**
     * Class constructors with provided parameters.
     * @param name name of the video
     * @param url url of the video
     * @param duration duration (long) of the video
     */
    public Video(String name, String url, long duration) {
        this.name = name;
        this.url = url;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * Two videos will generate the same hashcode if they have exactly
     * the same values for their name, url, and duration.
     * @return integer value of generated hashcode.
     */
    @Override
    public int hashCode() {
        // Google Guava provides utilities for hashing.
        return Objects.hashCode(name, url, duration);
    }

    /**
     * If two videos have the same name, url, and duration, then we
     * consider them as equal.
     * @param obj `Video` (supposedly) object to be compared.
     * @return boolean value indicating the two objects is equal or not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Video) {
            Video other = (Video)obj;
            // Google Guava provides utilites for equals too.
            return Objects.equal(name, other.name)
                    && Objects.equal(url, other.url)
                    && duration == other.duration;
        } else {
            return false;
        }
    }
}