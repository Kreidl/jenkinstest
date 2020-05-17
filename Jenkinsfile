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
		stage ('Analysis')
		{
	        
			withMaven(maven: 'localMaven')
			{
	        	sh "${mvnHome}/bin/mvn --batch-mode -V -U -e checkstyle:checkstyle pmd:pmd pmd:cpd findbugs:findbugs"
			}
	        def checkstyle = scanForIssues tool: checkStyle(pattern: '**/target/checkstyle-result.xml')
	        publishIssues issues: [checkstyle]
	   
	        def pmd = scanForIssues tool: pmdParser(pattern: '**/target/pmd.xml')
	        publishIssues issues: [pmd]
	        
	        def cpd = scanForIssues tool: cpd(pattern: '**/target/cpd.xml')
	        publishIssues issues: [cpd]
	        
	        def spotbugs = scanForIssues tool: spotBugs(pattern: '**/target/findbugsXml.xml')
	        publishIssues issues: [spotbugs]
	
	        def maven = scanForIssues tool: mavenConsole()
	        publishIssues issues: [maven]
	        
	        publishIssues id: 'analysis', name: 'All Issues', 
	            issues: [checkstyle, pmd, spotbugs], 
	            filters: [includePackage('io.jenkins.plugins.analysis.*')]
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