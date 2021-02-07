# diff-api

Base64 encoded data comparing application. 

This application is made on Spring Boot Framework using reactive
web stack with embedded Netty and MongoDB so it's self-contained.  

You will require compatibility with Java 8+ to run this application.

<h3>How to build and run diff-api application</h3>

Prior to run any command go to the application folder 'diff-api/'. <br>

First ensure application builds and  all tests run correctly. To do so, in a linux/unix console run:

`./gradlew build`

This also runs Jacoco Test Report that can be found in `build/reports/jacoco/index.html`

Second, to run the application just execute:

`./gradlew bootRun`

<h3>Request Samples</h3>

Next I provide a basic set of requests. Two POST requests to create both sides of the Diff and then
a GET request to check the results. 

<h4>Create Diff</h4>

Add left element

`curl --location --request POST 'http://localhost:8080/v1/diff/7/left' \
--header 'Content-Type: application/json' \
--data-raw '{
"value":"dGV4eHRlc3R4eHh0dGVzeHRlc3Q="
}'`

Add right element

`curl --location --request POST 'http://localhost:8080/v1/diff/7/right' \
--header 'Content-Type: application/json' \
--data-raw '{
"value":"dGVzdHRlc3R0ZXN0dGVzdHRlc3Q="
}'`

<h4>Get the results of a Diff</h4>

`curl --location --request GET 'http://localhost:8080/v1/diff/7' \
--header 'Content-Type: application/json' \
--data-raw '{
"value":"dGVzdGZvcm15d2Flc2ZyaWVuZHM="
}'`