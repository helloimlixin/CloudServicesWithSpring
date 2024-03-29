package org.examples.cloudservice.video.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Adds an HTML form to capture and display the video metadata.
 * Allows clients to send HTTP `POST` requests with videos that are stored in memory using a list.
 * Clients can send HTTP `GET` requests to receive a listing of the videos that have been sent to the servlet so far.
 * Stopping the servlet will cause it to lose the history of videos that hae been sent to it because they are stored in memory.
 *
 * @author xinli
 *
 * From Coursera Build Cloud Services With Java Spring Framework.
 */
public class HtmlVideoServlet extends HttpServlet // Servlets should inherit HttpServlet
{
    private static final long serialVersionUID = 1L;
    public static final String VIDEO_ADDED = "Video added.";

    // An in-memory list that the servlet uses to store the videos that are sent to it by clients.
    private List<Video> videos = new java.util.concurrent.CopyOnWriteArrayList<Video>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Make sure and set the content-type header so that the client can properly (and securely!) display the content that
        // you send back.
        response.setContentType("text/html");

        // This PrintWriter allows us to write data to the HTTP response body that is going to be sent to the Client.
        PrintWriter sendToClient = response.getWriter();

        // UI form
        sendToClient.write(
                "<form name='formvideo' method='POST' target='_self'>" +
                        "<fieldset><legend>Video Data</legend>" +
                        "<table><tr>" +
                        "<td><label for='name'>Name:&nbsp;</label></td>" +
                        "<td><input type='text' name='name' id='name' size='64' maxlength='64' /></td>" +
                        "</tr><tr>" +
                        "<td><label for='url'>URL:&nbsp;</label></td>" +
                        "<td><input type='text' name='url' id='url' size='64' maxlength='256' /></td>" +
                        "</tr><tr>" +
                        "<td><label for='duration'>Duration:&nbsp;</label></td>" +
                        "<td><input type='text' name='duration' id='duration' size='16' maxlength='16' /></td>" +
                        "</tr><tr>" +
                        "<td style='text-align: right;' colspan=2><input type='submit' value='Add Video' /></td>" +
                        "</tr></table></fieldset></form>");

        // Loop through all of the stored videos and print them out for the client to see.
        for (Video v : this.videos) {
            // For each video, write its name and URL into the HTTP response body.
            sendToClient.write(v.getName() + " : " + v.getUrl() + " (" + v.getDuration() + ")<br />");
        }
        sendToClient.write("</body></html>");
    }

    /**
     * This method handles all of the HTTP `GET` requests routed to the servlet by the web container. This method loops
     * through the lists of videos that have been sent to it and generates a text/plain list of the videos that is sent
     * back to the client.
     *
     * @param request HTTP `GET` request received
     * @param response response to the `GET` request
     * @throws ServletException throws exception when servlet fails
     * @throws IOException throws exception when IO fails
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("<html><body>");
        processRequest(request, response);
    }

    /**
     * This method handles all HTTP `POST` requests that are routed to the servlet by the web container.
     *
     * Sending a post to the servlet with 'name', 'duration', and 'url' parameters causes a new video to be created and added to
     * the list of videos.
     *
     * If the client fails to send one of these parameters, the servlet generates an HTTP error 400 (BAD REQUEST) response indicating
     * that a required request parameter was missing.
     * @param req HTTP `POST` request received.
     * @param resp HTTP response to the HTTP `POST` request
     * @throws ServletException throws exception when servlet fails
     * @throws IOException throws exception when IO fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // First, extract the HTTP request parameters that we are expecting from either the URL query string or the
        // url encoded form body.
        String name = req.getParameter("name");
        String url = req.getParameter("url");
        String durationStr = req.getParameter("duration");

        // Check the validity of the duration parameter provided by the client (if it's actually a number or not).
        long duration = -1;
        try {
            duration = Long.parseLong(durationStr);
        } catch (NumberFormatException e) {
            // The client sent us a duration value that was not a number.
            resp.sendError(400, "The duration you entered is not a valid number!");
        }

        // Now, the servlet has to look at each request parameter that the client was expected to provide and make sure
        // that it isn't null, empty, etc.
        if (name == null || url == null || durationStr == null || name.trim().length() < 1 || url.trim().length() < 10 || durationStr.trim().length() < 1
                || duration <= 0) {
            // If the parameters pass the above basic validation, we need to send an HTTP 400 BAD REQUEST to the client and give
            // a hint about where went wrong.
            resp.setContentType("text/html");
            resp.sendError(400, "Missing ['name', 'duration', 'url'].");
        } else {
            // It looks like the client provided all of the data that we need, use that data to construct a new Video object.
            Video v = new Video(name, url, duration);

            // Add the video to our in-memory list of videos.
            videos.add(v);

            // Let the client know that the video has been successfully added by writing a message into the HTTP response body.
            resp.getWriter().write("<html><body>" + VIDEO_ADDED);

            processRequest(req, resp);
        }
    }
}
