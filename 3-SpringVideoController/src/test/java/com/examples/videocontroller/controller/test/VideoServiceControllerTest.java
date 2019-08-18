package com.examples.videocontroller.controller.test;

import com.examples.videocontroller.controller.Video;
import com.examples.videocontroller.controller.VideoServiceController;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * This test constructs a `VideoServiceController` object, adds a `Video`
 * to it, and then checks that the `Video` is returned when `getVideoList()`
 * is called.
 *
 * Pay attention to how the test that actually uses HTTP and this test that
 * just directly makes method calls on a `VideoServiceController` object are
 * essentially identical. All that changes is the setup of the `videoService`
 * variable. Yes, this could be refactored to eliminate code duplication...
 * but the goal was to show how much Retrofit simplifies interaction with
 * our service!
 *
 * @author xinli
 *
 */
public class VideoServiceControllerTest {
    private VideoServiceController videoService = new VideoServiceController();

    /**
     * This test sends a HTTP `POST` request to the `VideoServlet` to add a new video and then sends
     * a second HTTP `GET` request to check that the video showed up in the list of videos.
     *
     * @throws Exception when needed
     */
    @Test
    public void testVideoAddAndList() throws Exception {
        // Information about the video.
        String title = "Recession Threat Hurts Trump's Popularity";
        String url = "http://www.youtube.com/watch?v=qK9Hd7bfbgU";
        long duration = 60 * 10 * 1000; // 10 min in milliseconds
        Video video = new Video(title, url, duration);

        boolean ok = videoService.addVideo(video);
        assertTrue(ok);

        // Send the POST request to the VideoServlet using Retrofit to add the video. Notice how
        // Retrofit provides a nice strongly-typed interface to our HTTP-accessible video service
        // that is much cleaner than trying to deal with URL query parameters, etc.
        List<Video> videos = videoService.getVideoList();
        assertTrue(videos.contains(video));
    }
}