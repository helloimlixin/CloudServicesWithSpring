# Running the `VideoServlet`

## To run the servlet:

Open Terminal in IntelliJ, and run command `gradle appRun`.

## To stop the servlet:

Press any key to end.

# Accessing the Service

After launching the servlet, open your browser to the URL below to see a list of the videos that have been added:

[http://localhost:8080/2-VideoServlet/video](http://localhost:8080/2-VideoServlet/video)

In order to add a video that you can see in the list, run the `VideoServletTest` `JUnit4` test and then refresh your browser.

# Viewing the HTML Page

After launching the servlet, visit [http://localhost:8080/2-VideoServlet/view/video](http://localhost:8080/2-VideoServlet/view/video)
to visit the HTML page of the server, however, the HTML version does not share the same memory as the VideoServlet
version, so adding `Video` object here and using `VideoServlet` results different lists of videos added.