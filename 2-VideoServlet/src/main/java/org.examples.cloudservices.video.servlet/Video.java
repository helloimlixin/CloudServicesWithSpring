package org.examples.cloudservices.video.servlet;

/**
 * A simple class to represent a video and its URL for viewing.
 *
 * @author xinli
 */
public class Video {

    private String name;
    private String url;
    private long duration;

    public Video(String name, String url, long duration) {
        super();
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
}