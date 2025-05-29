Hi! 

This is a example of how you can use openapi generator to make code for a react + vite and Jetty + Jersey webserver. 

When the openapi.json file have been updated or changed, just run ```mvn generate-resources``` and that will generate new code based on the openapi.json file. 

Before running it localy for development you need to run 
``` mvn clean package```

And to make the server start you need:
  1. A running postgres server (the project inlcudes a docker-compose file for that)
  2. Fill inn the application.properties based on the application.properties.template

Happy Coding! 
