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
		stage('Initialize Docker'){
		    def dockerHome = tool 'DOCKER_LOCAL'
		    env.PATH = "${dockerHome}/bin:${env.PATH}"
		}
		stage ('Build Docker Image'){
		    steps{
		        script{
		            sh 'docker build -t guilhermesantosdocker/ordem-servico-backend .'
		        }
		    }
		}
	}
}