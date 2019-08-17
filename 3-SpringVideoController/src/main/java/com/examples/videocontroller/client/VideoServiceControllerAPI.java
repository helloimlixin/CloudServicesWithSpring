package com.examples.videocontroller.client;

import com.examples.videocontroller.controller.Video;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

/**
 * This interface defines an API for a `VideoServiceController`. The interface is used to provide
 * a contract for client/server interactions. The interface is annotated with `Retrofit` annotations
 * so that clients can automatically convert the interface into a client object. See
 * `VideoServiceClientAPITest` for an example of how `Retrofit` is used to turn this interface into
 * a client.
 *
 * @author xinli
 */
public interface VideoServiceControllerAPI {

    // The path where we expect the VideoServiceController to live.
    public static final String VIDEO_SERVICE_CONTROLLER_PATH = "/video";

    @GET(VIDEO_SERVICE_CONTROLLER_PATH)
    public List<Video> getVideoList();

    @POST(VIDEO_SERVICE_CONTROLLER_PATH)
    public boolean addVideo(@Body Video v);
}
