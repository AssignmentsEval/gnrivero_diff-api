# diff-api

<h3>How to build and run diff-api application</h3>

Prior to run any command go to the application folder 'diff-api/'. <br>

First ensure application builds and  all tests run correctly. To do so, in a linux/unix console run:

`./gradlew build`

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