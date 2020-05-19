pipeline {
	agent any
	
	stages
	{
		stage ('Compile Stage')
		{			
			steps
			{			
				withMaven(maven: 'localMaven')
				{
					catchError
					{
						sh 'mvn clean compile'
					}
				}				
			}
			post
			{
                success
				{
					echo 'Compile stage successfull'   
                }
                failure
				{
                    script
					{
                        sh 'exit 1'
                    }
                }
                unstable
				{
                    script
					{
                        sh 'exit 1'                  
                     }
                }
            }
		}	
		stage ('Testing Stage')
		{			
			steps
			{				
				withMaven(maven: 'localMaven')
				{
					catchError
					{
						sh 'mvn test'
					}
				}
			}
			post
			{
                success
				{
					echo 'Testing stage successfull' 
                }
                failure
				{
                    script
					{
                        sh 'exit 1' 
                    }
                }
                unstable
				{
					script
					{
						sh 'exit 1'                  
                    }
                }
            }
		}
		
		stage ('Packaging Stage')
		{			
			steps
			{				
				catchError
				{
					sh 'docker build -t jenkinstest:${BUILD_NUMBER} . '
				}
			}
			post
			{
                success
				{
					 echo 'Packaging stage successful'
                }
                failure
				{
                    script
					{
                        sh 'exit 1'
                    }
                }
                unstable
				{
                    script
					{
						sh 'exit 1'                 
                    }
                }
            }
		}
		stage ('Image Analyzing stage')
		{			
			steps
			{				
				catchError
				{
					sh 'anchore-cli image add jenkinstest:${BUILD_NUMBER}' //add image to anchore
					sh 'anchore-cli image wait jenkinstest:${BUILD_NUMBER}' //wait for analyzing
					sh 'anchore-cli image vuln jenkinstest:${BUILD_NUMBER} os' //get vulnerabilities
					sh 'anchore-cli evaluate check jenkinstest:${BUILD_NUMBER} --detail' //perform policy evaluation
				}
			}
			post
			{
                success
				{
					 echo 'Packaging stage successful'
                }
            }
		}
	}
}