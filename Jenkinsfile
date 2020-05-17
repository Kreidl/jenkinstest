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
					withMaven(maven: 'localMaven')
					{
						withCredentials([string(credentialsId: 'docker-pwd', variable: 'dockerHubPwd')])
						{
							sh 'mvn compile jib:build'
						}	
					}
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
	}
}