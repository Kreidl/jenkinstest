node {
  def app
  def mvnTool = tool 'localMaven'
  def sonar = tool 'sonar'
  def containerBuild = "luke19/jenkinstest:${BUILD_NUMBER}"
  
  checkout scm
	
  stage ('Check Secrets Stage') {
    sh "rm trufflehog.txt || true"
    try {
      sh 'docker run --rm --name trufflehog dxa4481/trufflehog --regex https://github.com/Kreidl/jenkinstest_spring.git > trufflehog.txt' 	  
  	}catch (exc) {
    }   
    
    publishHTML (target: [
        allowMissing: false,
        alwaysLinkToLastBuild: false,
        keepAll: true,
        reportDir: './',
        reportFiles: 'trufflehog.txt',
        reportName: "Trufflehog Report"
      ])
  }	


  stage ('Source Composition Analysis Stage') {
    try {
      sh 'rm owasp* || true'
      sh 'wget "https://raw.githubusercontent.com/kreidl/jenkinstest_spring/master/owasp-dependency-check.sh" '
      sh 'chmod +x owasp-dependency-check.sh'
      sh 'bash owasp-dependency-check.sh'
      
      publishHTML (target: [
        allowMissing: false,
        alwaysLinkToLastBuild: false,
        keepAll: true,
        reportDir: 'odc-reports',
        reportFiles: 'dependency-check-report.html',
        reportName: "OWASP Report"
      ])
  	}
    catch (exc) {
      error('Source Composition Analysis failed' + exc.message)
    }
  }
 

  /*stage ('Source Composition Analysis Stage') {
    try {
  	  sh "${mvnTool}/bin/mvn org.owasp:dependency-check-maven:check"
  	}
    catch (exc) {
      error('Source Composition Analysis failed' + exc.message)
    }
  }	*/
  
  
  stage ('SAST') {
    sh "${mvnTool}/bin/mvn sonar:sonar"
    
    publishHTML (target: [
        allowMissing: false,
        alwaysLinkToLastBuild: false,
        keepAll: true,
        reportDir: 'target/sonar',
        reportFiles: 'report-task.txt',
        reportName: "Sonarscan Report"
      ])
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
  
  
  stage ('Deploying Stage') {
    try {
	  sh "docker run -d -p 8081:8081 --name tester ${containerBuild}"
  	}
    catch (exc) {
      error('Deploying failed' + exc.message)
    }
  }
 
  stage ('DAST') {
    try {
	  sh "docker run --rm -t owasp/zap2docker-stable zap-baseline.py -t http://35.228.190.112:8081/ > zap.txt"
	  
	  publishHTML (target: [
        allowMissing: false,
        alwaysLinkToLastBuild: false,
        keepAll: true,
        reportDir: './',
        reportFiles: 'zap.txt',
        reportName: "OWASP ZAP Report"
      ])
  	}
    catch (exc) {
      sh "docker stop tester && docker rm tester"
      error('DAST failed' + exc.message)
    }
  } 


  
}