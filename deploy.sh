#!/bin/bash
set -e

# Build the JAR file
./mvnw package

docker stop client-api-container
docker rm client-api-container


# Build Docker image
docker build -t client-api .

# Run the container, mapping port 8080 on the host to port 8080 in the container
docker run -d -p 8080:8080 --name client-api-container client-api