package org.examples.simplecloud.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An example of a simple servlet that looks for a single request parameter and echos the parameter
 * value back to the client using the "text/plain" content type.
 *
 * @author xinli
 *
 * From Coursera Course: Building Cloud Services with the Java Spring Framework.
 */
public class EchoServlet extends HttpServlet // Servlets should inherit from HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // First we need to set the content type header that is going to be returned in the HTTP
        // response so that the client will know how to display the response result.
        resp.setContentType("text/plain");

        // Next we look inside of the HTTP request for either a query parameter or a url encoded form
        // parameter in the body that is named using "msg".
        // For instance: http://foo.bar?msg=abcd
        String msg = req.getParameter("msg");

        // Echo a response back to the client with the msg that was sent.
        resp.getWriter().write("Echo:" + msg);
    }

    /*
     * This servlet does not override the doPost(), doPut(), etc methods in the parent class, so
     * this servlet can't handle the corresponding HTTP methods. If you need to support POST
     * requests, you would override doPost().
     */
}
