package org.examples.cloudservices.video.servlet;

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
    public static final String VIDEO_ADDED = "Vidoe added.";

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
}
