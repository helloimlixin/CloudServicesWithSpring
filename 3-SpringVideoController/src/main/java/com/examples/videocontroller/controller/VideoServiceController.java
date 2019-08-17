package com.examples.videocontroller.controller;

import com.examples.videocontroller.client.VideoServiceControllerAPI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * This simple `VideoServiceController` class allows clients to send HTTP `POST` requests with videos
 * that are stored in memory using a list. Clients can send HTTP `GET` requests to receive a JSON
 * listing of videos that have been sent to the controller so far. Stopping the controller will
 * cause it to lose the history of videos that have been sent to it because they are stored in memory.
 *
 * Notice how much simpler this `VideoServiceController` is than the original `VideoServlet`
 * implementation. Spring allows us to dramatically simplify our service code. Another important
 * aspect of this version is that we have defined a `VideoServiceControllerAPI` that provides strong
 * typing on both the client and service interface to ensure that we don't send the wrong parameters,
 * etc.
 *
 * @author xinli
 *
 * From Coursera course ...
 */
// Tell Spring that this class is a Controller that should handle certain HTTP requests for the
// DispatcherServlet, here we don't need to do the inheritance thing we did for HttpServlet again
// because of this annotation.
@Controller
public class VideoServiceController implements VideoServiceControllerAPI {

    // An in-memory list that the servlet uses to store the videos that are sent to it by clients.
    private List<Video> videos = new ArrayList<>();

    /**
     * Receives HTTP `POST` requests to path "/video" and converts the HTTP request body, which should
     * contain JSON, into a `Video` object before adding it to the list. The @RequestBody annotation on
     * the `Video` parameter is what tells Spring to interpret the HTTP request body as JSON and convert
     * it into a `Video` object to pass into the method. The @ResponseBody annotation tells Spring to
     * cover the return value from the method back into JSON and put it into the body of the HTTP
     * response to the client.
     *
     * The `VIDEO_SERVICE_CONTROLLER_PATH` is set to "/video" in the `VideoServiceControllerAPI`
     * interface. We use this constant to ensure that the client and service paths for the
     * `VideoServiceController` are always in synchronized.
     *
     * @param v video object received as HTTP request
     * @return HTTP response as a boolean indicating whether adding the video was successful
     */
    @RequestMapping(value = VIDEO_SERVICE_CONTROLLER_PATH, method = RequestMethod.POST)
    public @ResponseBody boolean addVideo(@RequestBody Video v) {
        return videos.add(v);
    }

    /**
     * This method receives HTTP `GET` requests to "/video" and returns the current list of videos in
     * memory. Spring automatically converts the list of videos in JSON because of the `@ResponseBody`
     * annotation.
     *
     * @return HTTP response as a list of videos added to the server.
     */
    @RequestMapping(value = VIDEO_SERVICE_CONTROLLER_PATH, method = RequestMethod.GET)
    public @ResponseBody List<Video> getVideoList() {
        return videos;
    }
}
