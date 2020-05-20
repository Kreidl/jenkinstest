node {
  def app
	
  stage ('Compile Stage') {
  	try {
  	  sh 'mvn clean compile'
  	}
    catch (exc) {
      echo 'Clean compile failed'
      throw
    }
  }	
	
	
	stage ('Testing Stage') {			
		steps{				
			withMaven(maven: 'localMaven'){
				catchError{
					sh 'mvn test'
				}
			}
		}
		post{
	        success{
				echo 'Testing stage successfull' 
	        }
	        failure{
	            script{
	                sh 'exit 1' 
	            }
	        }
	        unstable{
				script{
					sh 'exit 1'                  
	            }
	        }
	    }
	}
	
	
	stage ('Packaging Stage') {			
		steps{				
			catchError{
				sh 'docker build -t jenkinstest:${BUILD_NUMBER} . '
			}
		}
		post{
	        success{
				 echo 'Packaging stage successful'
	        }
	        failure{
	            script{
	                sh 'exit 1'
	            }
	        }
	        unstable{
	            script{
					sh 'exit 1'                 
	            }
	        }
	    }
	}
	
	
	
	def imageLine = 'jenkinstest:${BUILD_NUMBER}'
	writeFile file: 'anchore_images', text: imageLine
	anchore name: 'anchore_images'
}