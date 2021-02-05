Business Documentation for the Interview question
==================

## General

1. This is a very basic spring-boot app. It imitates the functionality of Forum.
2. Application uses Java 8, Spring, H2 database and Spock.

## API documentation

1. API documentation was written using the RAML language. All the schema and the examples are located in the file [api.raml](api-specification/api.raml). 
   
2. From RAML files the HTML documentation was generated. It is available in the file [api_documentation.html](api_documentation.html). Open it in the browser to
see it properly.
3. Use file [postman_collection.json](postman_collection.json) in order to import the API collection to the postman.  

## How to start the application?

1. Build it using `mvn clean install`
2. Run it using `mvn spring-boot:run`
3. Application is available under url `http://localhost:5000/`
4. This project uses [lombok](https://projectlombok.org/).