package org.examples.cloudservice.video.servlet;

import org.examples.cloudservice.video.servlet.Video;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This simple `VideoServlet` allows clients to send HTTP `POST` requests with videos that are stored
 * in memory using a list. Clients can send HTTP `GET` requests to receive a `text/plain' listing of
 * the videos that have been sent to the servlet so far. Stopping the servlet will cause it to lose
 * the history of videos that have been sent to it because they are stored in memory.
 *
 * @author xinli
 */
public class VideoServlet extends HttpServlet // Servlets should inherit HttpServlet
{
    public static final String VIDEO_ADDED = "Video added.";

    // In-memory list that servlet uses to store the videos that are sent to it by clients.
    private List<Video> videos = new ArrayList<>();

    /**
     * This method processes all of the HTTP `GET` requests routed to the servlet by the web
     * container. This method loops through the lists of videos that have been sent to it and
     * generates a `text/plain` list of the videos that is sent back to the client.
     * @param req HTTP request received
     * @param resp HTTP response to be sent
     * @throws ServletException throws exception while servlet fails
     * @throws IOException throws exception while IO fails.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // First set the content-type header so that the client can properly (and securely!) display
        // the content that you send back.
        resp.setContentType("text/plain");

        // The `PrintWriter` class allows us to write data to the HTTP response body that is going to
        // be sent back to the client.
        PrintWriter sendToClient = resp.getWriter();

        // Loop through all of the stored videos and print them out for the client to see.
        for (Video v: this.videos) {
            // For each video, write its name and URL into the HTTP response body.
            sendToClient.write(v.getName() + " : " + v.getUrl() + "\n");
        }
    }

    /**
     * This method handles all HTTP `POST` requests that are routed to the servlet by the web container.
     *
     * Sending a post to the servlet with `name`, `duration`, and `url` parameters causes a new video to
     * be created and added to the list of videos.
     *
     * If the client fails to send one of these parameters, the servlet generates an HTTP error 400 (Bad
     * request) response indicating that a required request parameter is missing.
     *
     * @param req request received
     * @param resp response to be sent
     * @throws ServletException if the servlet fails, throws an exception
     * @throws IOException if IO fails, throws an exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // First, extract the HTTP request parameters that we are expecting from either the URL query
        // string or the URL encoded form body
        String name = req.getParameter("name");
        String url = req.getParameter("url");
        String durationStr = req.getParameter("duration");

        // Check that the duration parameter provided by the client is actually a number.
        long duration = -1;
        try {
            duration = Long.parseLong(durationStr);
        } catch (NumberFormatException e) {
            System.out.println("The duration value received is not a number!");
        }

        // Make sure and set the content-type header so that the client can properly (and
        // securely!) display the content that you send back.
        resp.setContentType("text/plain");

        // Now, the servlet will look at each request parameter and make sure that it isn't null,
        // empty, etc.
        if (name == null || url == null || durationStr == null || name.trim().length() < 1
                || url.trim().length() < 10 || durationStr.trim().length() < 1 || duration <= 0) {
            // If the parameters pass the above validation, we send an HTTP 400 Bad request to the
            // client and give it a hint as to what might got wrong.
            resp.sendError(400, "Invalid request parameters: ['name', 'url', 'duration']");
        } else {
            // With all the valid data provided by the client, we construct a new `Video` object.
            Video v = new Video(name, url, duration);

            // Append the video to our video list.
            videos.add(v);

            // Notify the client that the video has been successfully added.
            resp.getWriter().write(VIDEO_ADDED);
        }
    }
}
