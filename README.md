Hi!

This is an example of how you can use openapi generator to generate code for a React + Vite and Jetty + Jersey webserver.

When the openapi.json file has been updated or changed, just run ```mvn generate-resources``` and that will generate new code based on the openapi.json file.

Before starting local development you need to run
``` mvn clean package```

And to make the server start, you need:

1. A running postgres server (the project includes a docker-compose file for that)
2. Fill inn the application.properties based on the application.properties.template

Happy Coding! 
