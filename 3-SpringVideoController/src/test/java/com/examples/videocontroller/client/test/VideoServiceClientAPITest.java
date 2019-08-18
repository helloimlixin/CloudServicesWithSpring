package com.examples.videocontroller.client.test;

import com.examples.videocontroller.client.VideoServiceControllerAPI;

import com.examples.videocontroller.controller.Video;
import org.junit.Test;
import retrofit.RestAdapter;

import java.util.List;

import static org.junit.Assert.*;

/**
 * This test sends a `POST` request to the `VideoServlet` to add a new video and then sends a second
 * `GET` request to check that the video showed up in the list of videos.
 *
 * The test requires that the application be running first (see the directions in the README.md file
 * for how to launch the application.
 *
 * Pay attention to how this test that actually uses HTTP and the test that just directly makes method
 * calls on a `VideoServiceController` object are essentially identical. All that changes is the setup
 * of the `videoService` variable. Yes, this could be refactored to eliminate code duplication...but
 * the goal was to show how much Retrofit simplifies interaction with our service!
 *
 * @author xinli
 */
public class VideoServiceClientAPITest {

    private final String TEST_URL = "http://localhost:8080";

    /**
     * Retrofit is a type-safe HTTP client for Android and Java, and turns HTTP API into a Java
     * Interface. The `Retrofit` class can then generate an implementation of the interface.
     *
     * This is how we turn the `VideoServiceControllerAPI` into an object that will translate method
     * calls on the `VideoServiceControllerAPI`'s interface methods into HTTP requests on the server.
     * Parameters / Return values are being marshaled to/from JSON.
     */
    private VideoServiceControllerAPI videoService = new RestAdapter.Builder()
            .setEndpoint(TEST_URL)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build()
            .create(VideoServiceControllerAPI.class);

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