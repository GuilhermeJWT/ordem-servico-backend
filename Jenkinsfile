pipeline {
	agent any
	stages {
		stage('Build Backend'){
			steps {
				sh "mvn package"
			}
		}
		stage ('Build Docke Image'){
        	steps{
        		script{
        		    sh 'docker build -t guilhermesantosdocker/ordem-servico-backend .'
        		}
        	}
        }
        stage ('Push Docker Hub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerhub', variable: 'PASSWORD_DOCKER_HUB')]){
                        sh 'docker login -u guilhermesantosdocker -p ${PASSWORD_DOCKER_HUB}'
                        sh 'docker push guilhermesantosdocker/ordem-servico-backend'
                    }
                }
            }
        }
		stage('Unit Test') {
		    steps {
		       sh "mvn test"
		    }
		}
		stage('Sonarqube Analysis'){
		    steps {
		        script {
		            withCredentials([string(credentialsId: 'ordem-servico-backend', variable: 'SONAR_TOKEN')]){
		                sh 'mvn clean package sonar:sonar -Dsonar.host.url=http://192.168.18.15:9000/ -Dsonar.login=${SONAR_TOKEN}'
		            }
		        }
		    }
		}
		stage ('Quality Gate'){
            steps {
                sh 'mvn sonar:sonar sonar-quality-gate:check -Dsonar.host.url=http://192.168.18.15:9000/ -Dsonar.login=admin -Dsonar.password=sonarqube -Dsonar.token=${SONAR_TOKEN}'
            }
		}
	}
}