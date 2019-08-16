package org.examples.cloudservice.video.servlet.test;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.examples.cloudservice.video.servlet.VideoServlet;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This test sends a `POST` request to the `VideoServlet` to add a new video and then sends a second
 * `GET` request to check that the video showed up in the list of videos.
 *
 * The test requires that the servlet be running first (see the directions in the README.md file for
 * how to launch the servlet in a web container.
 *
 * The test uses the Apache HttpClient that is part of the HTTP Components project.
 */
public class VideoServletTest {
    private final String TEST_URL = "http://localhost:8080/2-VideoServlet/video";

    // The HTTP Client or "fake browser" that we are going to use to send requests to the `VideoServlet`.
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Test
    public void testVideoAddAndList() throws Exception {
        // Information about the video.
        // We create a random String for the title so that we can ensure that the video is added after
        // every run of this test.
        String myRandomID = UUID.randomUUID().toString();
        String title = "Video - " + myRandomID;
        String videoUrl = "https://www.youtube.com/some/video-" + myRandomID;
        long duration = 60 * 10 * 1000; // 10 min in milliseconds

        // Create the HTTP POST request to send the video to the server.
        HttpPost post = createVideoPostRequest(title, videoUrl, duration);

        // Use our HttpClient to send the POST request and obtain the HTTP response that the server
        // sends back.
        HttpResponse response = httpClient.execute(post);

        // Check if we got an HTTP 200 OK response code.
        assertEquals(200, response.getStatusLine().getStatusCode());

        // Retrieve the HTTP response body from the HTTP response.
        String responseBody = extractResponseBody(response);

        // Make sure that the response is what we expect. Rather than trying to keep the response
        // message from the VideoServlet in synchronized with this test, we simply use a public static
        // final variable on the VideoServlet so that we can refer to the message in both places and
        // avoid the test and servlet definition of the message drifting out of synchronization.
        assertEquals(VideoServlet.VIDEO_ADDED, responseBody);

        // Now that we have posted the video to the server, we construct an HTTP GET request to fetch
        // the list of videos from the VideoServlet.
        HttpGet getVideoList = new HttpGet(TEST_URL);
        // Execute our GET request and obtain the server's HTTP response.
        HttpResponse listResponse = httpClient.execute(getVideoList);

        // Check if we got an HTTP 200 OK response code.
        assertEquals(200, listResponse.getStatusLine().getStatusCode());

        // Extract the HTTP response body from the HTTP response.
        String receivedVideoListData = extractResponseBody(listResponse);

        // Construct a representation of the text that we are expecting to see in the response
        // representing our video.
        String expectedVideoEntry = title + " : " + videoUrl + "\n";

        // Check if our video shows up in the list by searching for the expectedVideoEntry in the text
        // of the response body.
        assertTrue(receivedVideoListData.contains(expectedVideoEntry));
    }

    @Test
    public void testMissingRequestParameter() throws Exception {
        // Information about the video.
        // We create a random String for the title so that we can ensure that the video is added after
        // every run of this test.

        // We are going to purposely send an empty String for the title in this test and ensure that
        // the VideoServlet generates a 400 reponse code.
        String myRandomID = UUID.randomUUID().toString();
        String title = "";
        String videoUrl = "https://www.youtube.com/some/video-" + myRandomID;
        long duration = 60 * 10 * 1000; // 10 min in milliseconds

        // Create the HTTP POST request to send the video to the server.
        HttpPost post = createVideoPostRequest(title, videoUrl, duration);

        // Use our HttpClient to send the POST request and obtain the HTTP response that the server
        // sends back.
        HttpResponse response = httpClient.execute(post);

        // The VideoServlet should generate an error 400 BAD REQUEST response.
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    /**
     * This method extracts the HTTP response body into a string.
     *
     * @param response received HTTP response
     * @return a string represents the content of the received HTTP response body
     * @throws IOException if the content of the HTTP response body is empty, throws an IOException
     */
    private String extractResponseBody(HttpResponse response) throws IOException {
        return IOUtils.toString(response.getEntity().getContent());
    }

    /**
     * This method constructs a properly formatted `POST` request that can be sent to the `VideoServlet`
     * to add a video.
     *
     * @param title name of the video
     * @param videoUrl url for the video
     * @param duration duration (long) for the video
     * @return a properly formatted HTTP `POST` request with the video information given
     */
    private HttpPost createVideoPostRequest(String title, String videoUrl, long duration) {
        // Create a POST request object using the Apache HttpClient library.
        HttpPost post = new HttpPost(TEST_URL);

        // Populate the request with the name value pairs that we want to send to the server
        // representing our Video data.
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("name", title));
        params.add(new BasicNameValuePair("url", videoUrl));
        params.add(new BasicNameValuePair("duration", "" + duration));

        // Encode the data into a url encoded request body that we can send to the server with our
        // request.
        UrlEncodedFormEntity requestBody = new UrlEncodedFormEntity(params, Consts.UTF_8);

        // Attach our url encoded Video data to the request.
        post.setEntity(requestBody);

        return post;
    }
}