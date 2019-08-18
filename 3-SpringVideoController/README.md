# Running the Video Service Application

## To run the application

Click on the `VideocontrollerApplication`
class in the ``com.examples.videocontroller`` package, and run as
`Java Application`. It will automatically start the Spring Application.

## To stop the application
Stop the execution of `VideocontrollerApplication`.

# Accessing the Service

To view the list of videos added to the service, open your browser and
navigate to the following URL:

[http://localhost:8080/video](http://localhost:8080/video)

To add a test video, run the JUnit test `VideoServiceClientAPITest`.

# What to Pay Attention to

Notice how much work has been saved using the Spring Framework:

1. The `web.xml` file has been eliminated and replaced by the `@EnableWebMvc` annotation, now we just have to run `VideocontrollerApplication.java` now.
2. Our `VideoServiceController` has been dramatically simplified compared to the past `VideoServlet` and now relies on Spring Framework to automatically
marshall/unmarshall data sent to/from the client.
3. We have a *type-safe* interface for interacting with our `VideoServiceController` from a client. This interface, when combined wth `Retrofit`, dramatically
simplifies client/server interactions.
4. Look at the two tests in `src/test/java`. The tests are vastly simpler than with the `VideoServlet` version. In fact, the `Retrofit` version of the test
(`VideoServiceControllerAPITest`) is essentially identical to the version that just constructs and tests `VideoServiceController` object. Using `Retrofit`,
it looks like you are interacting with a local object -- even though that object is actually sending HTTP requests to the server in response to your method calls.
