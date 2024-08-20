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
		stage ('Quality Gate'){
            steps {
                sh './mvnw sonar:sonar sonar-quality-gate:check -Dsonar.host.url=http://192.168.18.15:9000/ -Dsonar.login=admin -Dsonar.password=sonarqube -Dsonar.token=${SONAR_TOKEN}'
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
}