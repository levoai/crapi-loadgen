# crapi-loadgen
A simple load generator for the crapi application that uses Selenium to exercise different pages of the UI.

## Build docker image and run

```bash
./gradlew buildDockerImage
docker run -e CRAPI_URL=http://host.docker.internal:8090 levoai/crapi-loadgen

# Run using a proxy
docker run -e CRAPI_URL=http://host.docker.internal:8090 -e HTTP_PROXY=http://host.docker.internal:8080 levoai/crapi-loadgen
```

## Run using docker-compose
```bash
./gradlew buildDockerImage
export CRAPI_URL=<crapi url>
docker-compose up
```