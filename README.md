# crapi-loadgen
A simple load generator for the crapi application that uses Selenium to exercise different pages of the UI.

## Build docker image and run

```bash
./gradlew buildDockerImage
docker run -e CRAPI_URL=<crapi url> levoai/crapi-loadgen
```

## Run using docker-compose
```bash
export CRAPI_URL=<crapi url>
docker-compose up
```