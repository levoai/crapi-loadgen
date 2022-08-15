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



**Step By Step Cypress Installation**
1. Install Node.js. After Installation:
  ## In MAC: 
          Go to Terminal, `echo $PATH`, /usr/local/bin should be present in the path. If not present, `vim ~/.bash_profile` and click enter
          Click "i" to insert. `export PATH=$PATH:/usr/local/bin` and save using(esc:wq) and click enter.
          To save changes globally, `source ~/.bash_profile` and enter
  ## In Windows: 
          Open System environment variables, Under System Variables. Click New. Add Variable name:NODE_HOME and value:C:\Program Files\nodejs and OK
2. Download Visual studio Code and install
3. Clone the repo
4. In VS Code Terminal, Navigate to app/ and run `npm install`(Installs all dependencies in package.json)
5. To open testRunner,In VSCode Terminal enter `node_modules/.bin/cypress open`. select E2E Testing. Select Chrome and Click Start E2E testing in Chrome.
6. Click on the test and run it.and we can see the results.
