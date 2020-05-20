node {
  def app
  def mvnTool = tool 'localMaven'
  def containerBuild = "luke19/jenkinstest:${BUILD_NUMBER}"
	
  stage ('Compile Stage') {
    try {
  	  sh "${mvnTool}/bin/mvn clean compile"
  	}
    catch (exc) {
      error('Clean compile failed')
    }
  }	

  stage ('Testing Stage') {
    try {
  	  sh "${mvnTool}/bin/mvn test"
  	}
    catch (exc) {
      error('Testing failed')
    }
  }
  

  stage ('Packaging Stage') {
    try {
	  docker.withDockerRegistry(credentialsId: 'docker', toolName: 'localDocker', url: 'https://index.docker.io/v1/') {
	    app = docker.build(containerBuild)
        app.push()
	  }
  	}
    catch (exc) {
      error('Packaging failed' + exc.message)
    }
  }
  
  
  stage ('Analyzing Stage') {
    
    try {
  	  writeFile file: 'anchore_images', text: containerBuild
  	  anchore name: 'anchore_images'
  	}
    catch (exc) {
      error('Packaging failed. ' + exc.message)
    }
  }			


  
}