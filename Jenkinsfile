pipeline {
	environment {
     checkstyle = scanForIssues tool: checkStyle(pattern: '**/target/checkstyle-result.xml')
     pmd = scanForIssues tool: pmdParser(pattern: '**/target/pmd.xml')
     cpd = scanForIssues tool: cpd(pattern: '**/target/cpd.xml')
     spotbugs = scanForIssues tool: spotBugs(pattern: '**/target/findbugsXml.xml')
     maven = scanForIssues tool: mavenConsole()
   	}
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
				withCredentials([string(credentialsId: 'docker-pwd', variable: 'dockerHubPwd')])
				{
					sh 'docker login -u luke19 -p %dockerHubPwd%'
				}
				catchError
				{
					withMaven(maven: 'localMaven')
					{
						sh 'mvn compile jib:build'
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