node {
  def app
  def mvnTool = tool 'localMaven'
	
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
  	  sh "docker build -t jenkinstest:${BUILD_NUMBER} ."
  	}
    catch (exc) {
      error('Packaging failed')
    }
  }				


  def imageLine = 'jenkinstest:${BUILD_NUMBER}'
  writeFile file: 'anchore_images', text: imageLine
  anchore name: 'anchore_images'
}