pipeline {
	agent any
	stages {
		stage('Compile Backend'){
			steps {
				sh "./mvnw compile"
			}
		}
		stage('Unit Test') {
        	steps {
        		 sh "./mvnw test"
        	 }
        }
		stage('Sonarqube Analysis'){
		    steps {
		        script {
		           sh './mvnw clean package sonar:sonar -Dsonar.host.url=http://192.168.18.15:9000/ -Dsonar.login=admin -Dsonar.password=sonarqube -Dsonar.token=${SONAR_TOKEN}'
		        }
		    }
		}
		stage ('Quality Gate'){
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
		}
		stage('Build Backend'){
        	steps {
        		sh "./mvnw package"
        	}
        }
		stage ('Build Docker Image'){
		    steps{
		        script{
		            sh 'docker build -t guilhermesantosdocker/ordem-servico-backend .'
		        }
		    }
		}
		stage ('Push Docker Hub'){
		    steps{
		        script{
		            withDockerRegistry(credentialsId: 'dockerhub', toolName: 'Docker') {
		              sh 'docker push guilhermesantosdocker/ordem-servico-backend'
		            }
		        }
		    }
		}
	}
	post {
	    always {
	        junit allowEmptyResults: true, stdioRetention: '', testResults: 'target/surefire-reports/*.xml'
	    }
	}
}