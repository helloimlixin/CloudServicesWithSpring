# Running the `EchoServlet`

## To run the servlet:

Open Terminal in IntelliJ, `cd` into current project folder, and run `gradle appRun`, here we are using the *Gretty* plugin for Gradle.

## To stop the servlet:

Press any key to stop.

# Accessing the Service
After launching the `EchoServlet`, you can interact with it by opening a web browser to:

[http://localhost:8080/java-file/echo?msg=1234test](http://localhost:8080/java-file/echo?msg=1234test)

Changing the `msg` URL query parameter will allow you to send different values to echo to the `EchoServlet`. Note here it works only when the number of query parameter is one (no spaces allowed).

# What to Pay Attention to
Take a look at the web.xml file in `src/main/webapp/WEB-INF/web.xml` and see how the routing of requests to the `EchoServlet` was setup
Notice how the `EchoServlet` extracts the `msg` parameter from the request
Notice that the `EchoServlet` explicitly sets a **content-type** for the response so that the client will know how to interpret the data in the body of the response. If you change this content-type, it will affect how your browser displays the result.
Look at the `EchoServletTest` for an example of how to programmatically send an HTTP `GET` request to the servlet.