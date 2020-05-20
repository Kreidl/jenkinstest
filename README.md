# jenkinstesting
A repo for testing jenkins 

PlugIns needed:
Pipeline Maven Integration<br/>
Anchore Container Image Scanner Plugin<br/>

Additional:
Change the name of the configured Maven in pipeline to the maven name you gave in jenkins
Docker and Docker-compose (Anchore container) installed<br/>
Add the docker installation and username/password config for dockerregistry and also the url for dockerregistry (atm it is dockerhub)

anchore docker container installed (https://github.com/anchore/anchore-cli)<br/>

For Anchore container use these two commands:
curl https://docs.anchore.com/current/docs/engine/quickstart/docker-compose.yaml > docker-compose.yaml
docker-compose up -d

Also configure the anchoreengine parameters in jenkins (under configure system)