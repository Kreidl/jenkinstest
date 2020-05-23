node {
  def app
  def mvnTool = tool 'localMaven'
  def containerBuild = "luke19/jenkinstest:${BUILD_NUMBER}"
	
  stage ('Check Secrets Stage') {
    sh "rm trufflehog || true"  
    try {
      sh 'docker run dxa4481/trufflehog --regex https://github.com/Kreidl/jenkinstest_spring.git > trufflehog' 	  
  	}catch (exc) {
    }   
    sh "cat trufflehog"
  }	


  stage ('Source Composition Analysis Stage') {
    try {
      sh 'rm owasp* || true'
      sh 'wget "https://raw.githubusercontent.com/kreidl/jenkinstest_spring/master/owasp-dependency-check.sh" '
      sh 'chmod +x owasp-dependency-check.sh'
      sh 'bash owasp-dependency-check.sh'
      sh 'cat /var/lib/jenkins/OWASP-Dependency-Check/reports/dependency-check-report.xml'
  	}
    catch (exc) {
      error('Source Composition Analysis failed' + exc.message)
    }
  }
  
  
  
  stage ('Compile Stage') {
    try {
  	  sh "${mvnTool}/bin/mvn clean compile"
  	}
    catch (exc) {
      error('Clean compile failed' + exc.message)
    }
  }	

  stage ('Testing Stage') {
    try {
  	  sh "${mvnTool}/bin/mvn test"
  	}
    catch (exc) {
      error('Testing failed' + exc.message)
    }
  }
  

  stage ('Packaging Stage') {
    try {
	  withDockerRegistry(credentialsId: 'docker', toolName: 'localDocker', url: 'https://index.docker.io/v1/') {
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