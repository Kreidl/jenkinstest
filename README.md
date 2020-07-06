# jenkinstesting
A repo for testing jenkins


The Repo uses these branches (each branch have its own Jenkinsfile):
* production (Jenkinsfile_Production) --> creates docker container and Runs production code (last step)
* integration (Jenkinsfile_Security) --> get merges from unittesting do securitytesting and merge code into production
* unittesting (Jenkinsfile_Testing) --> unittesting and then merge automatically into integration

For merge you need to add the Git credentials in the credentials with the variablename defined in the merging stage of testing


Security Mechanisms:
*  **Secret Scanning with (https://hub.docker.com/r/dxa4481/trufflehog)**
*  **OWASP Dependency Check with (https://hub.docker.com/r/owasp/dependency-check)** --> Will be very slow on first run due to CVE updates
*  **SonarQube Scanner**
*  **Anchore Container Image Scanner For Container Check on Policy and Vulernabilities**
*  **ZAP Scanner** --> See More Scanner Methods on https://www.zaproxy.org/docs/docker/



PlugIns needed:
Pipeline Maven Integration<br/>
SonarQube Scanner<br/>
Anchore Container Image Scanner Plugin<br/>
HTML Publisher (For Showing Logdata in the build)<br/>


Additional:
Change the name of the configured Maven in pipeline to the maven name you gave in jenkins<br/>
Docker and Docker-compose (Anchore container) installed<br/>
Add the docker installation and username/password config for dockerregistry and also the url for dockerregistry (atm it is dockerhub)

If the Registry is not public then there is this command needed:<br/>
docker-compose exec api anchore-cli registry add REGISTRYURL REGISTRY_USERNAME REGISTRY_PW<br/>
For dockerhub it is: docker-compose exec api anchore-cli registry add docker.io REGISTRY_USERNAME REGISTRY_PW<br/>


If there is this error: Cannot autolaunch D-Bus without X11 $DISPLAY<br/>
Use: sudo apt-get remove -y golang-docker-credential-helpers

anchore docker container installed (https://github.com/anchore/anchore-cli)<br/>

For Anchore container use these two commands:<br/>
curl https://docs.anchore.com/current/docs/engine/quickstart/docker-compose.yaml > docker-compose.yaml<br/>
docker-compose up -d

Also configure the anchoreengine parameters in jenkins (under configure system)


For Trufflehog (Secret Checking) pull this container to increase execution speed:
docker pull dxa4481/trufflehog:latest

Also add your git path to the script


For Sonarqube create this docker container:
docker run -d -p 9000:9000 --name sonarqube sonarqube

Configure sonarqube in jenkins after installing plugin
Configure / System

Add SonarQube installation. For name Choose sonar
For Server set http://localhost:9000 if on same server

For Credentials add a Secret Text with the token from this:<br/>
Login to Sonarqube (HOST_IP:9000) with default creds admin:admin.
Go to my Account. Select Security and Generate Tokens. Choose a name and click generate

Additionally configure Global tools
Go to SonarQube Scanner add, Set Name to sonar and check the checkbox on install automatically


For ZAP Scanning there should be a running application and the docker container must reach the application

