node {
  def app
  def mvnTool = tool 'localMaven'
  def containerBuild = 'jenkinstest:${BUILD_NUMBER}'
	
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
  	  sh "docker build -t ${containerBuild} ."
  	}
    catch (exc) {
      error('Packaging failed')
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