#!/bin/bash
set -e

# Build the JAR file
./mvnw package

docker rm -f client-api-container 2>/dev/null || true


# Build Docker image
docker build -t client-api .

# Run the container, mapping port 8080 on the host to port 8080 in the container
docker run -d -p 8080:8080 --name client-api-container client-api