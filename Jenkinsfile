pipeline {
	agent any
	stages {
		stage('Build Backend'){
			steps {
				sh "./mvnw package"
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
		            withCredentials([string(credentialsId: 'ordem-servico-backend', variable: 'SONAR_TOKEN')]){
		                sh './mvnw clean package sonar:sonar -Dsonar.host.url=http://192.168.18.15:9000/ -Dsonar.login=${SONAR_TOKEN}'
		            }
		        }
		    }
		}
		stage ('Build Docker Image'){
		    steps{
		        withDockerContainer(image: 'ordem-servico-backend.jar', toolName: 'DOCKER_LOCAL') {
                    script{
                        sh 'docker build -t guilhermesantosdocker/ordem-servico-backend .'
                    }
                }
		    }
		}
	}
}