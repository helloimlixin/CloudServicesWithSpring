package org.examples.simplecloud.servlet.test;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * This test sends a simple GET request to the EchoServlet. The test requires that the servlet be
 * running first (see the directions in the README.md file for how to launch the servlet in a web
 * container.
 *
 * @author xinli
 *
 */
public class EchoServletTest {
    // By default, the test server will be running on localhost and listening to port 8080. If the
    // server is running and you can't connect to it with this test, ensure that a firewall (e.g.
    // Windows Firewall) isn't blocking access to it.
    private final String TEST_URL = "http://localhost:8080/java-file/echo";

    /**
     * This test sends a GET request with a msg parameter and ensures that the servlet replies with
     * exactly "Echo:" + msg.
     *
     * @throws Exception throws exception when needed
     */
    @Test
    public void testMsgEchoing() throws Exception {
        // The message to send to the EchoServlet
        String msg = "I_Love_Cherilyn";

        // Append our message to the URL so that the EchoServlet will send the message back to us
        String url = TEST_URL + "?msg=" + msg;

        // Send an HTTP GET request to the EchoServer and convert the response body to a String
        URL urlobj = new URL(url);
        String content = IOUtils.toString(urlobj.openStream());

        // Ensure that the body of the HTTP response met our expectations (e.g., it was "Echo:" + msg)
        assertEquals("Echo:" + msg, content);
    }
}