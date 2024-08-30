pipeline {
	agent any
	environment {
      SSH_KEY_PATH = '/home/guilherme/AWS-KEYS/ordem-servico-backend-ec2-chave.pem' //colocar a chave em uma variavel mais segura
      DEPLOY_DIR = '/app/ordemservicobackend' //diretório criador no ec2, onde vai ficar o jar da aplicação - sudo mkdir -p /app/ordemservicobackend
      APP_NAME = 'ordemservicobackend' //nome da aplicação
      JAR_FILE = 'ordem-servico-backend.jar' //jar da aplicação
      EC2_HOST = '3.90.65.250' //ec2
      SSH_CREDENTIALS = 'EC2-SSH-Credentials' //credentialsId configurada no plugin SSH Agent
    }
	stages {
		stage('Build Backend'){
			steps {
				sh "./mvnw package"
			}
		}
        stage('Transfer Artifact to EC2') {
            steps {
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: SSH_CREDENTIALS,
                            transfers: [
                                sshTransfer(
                                    sourceFiles: "target/${JAR_FILE}",
                                    removePrefix: 'target',
                                    remoteDirectory: DEPLOY_DIR
                                )
                            ],
                            verbose: true
                        )
                    ]
                )
            }
        }
		stage ('Deploy to EC2'){
            steps{
                sshagent(['EC2-SSH-Credentials']) {
                    sh """
                    ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} << EOF
                    cd ${DEPLOY_DIR}
                    pgrep -f ${JAR_FILE} | xargs -r kill -9
                    nohup java -jar -Dspring.profiles.active=prod ${JAR_FILE} > ${APP_NAME}.log 2>&1 &
                    EOF
                    """
                }
            }
		}
		stage('Unit Test') {
        	steps {
        		 sh "./mvnw test"
        	 }
        }
        stage ('OWASP Check Vulnerabilities'){
        	steps{
        		 dependencyCheck additionalArguments: '''
                  -o './'
                 -s './'
                 -f 'ALL'
                 --prettyPrint''', odcInstallation: 'OWASP-Check-Vulnerabilities'

                 dependencyCheckPublisher pattern: 'dependency-check-report.xml'
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
                    sh './mvnw sonar:sonar sonar-quality-gate:check -Dsonar.host.url=http://192.168.18.15:9000/ -Dsonar.login=admin -Dsonar.password=sonarqube -Dsonar.token=${SONAR_TOKEN}'
                }
            }
		}
		stage ('Build Docker Image'){
		    steps{
		        script{
		            withDockerRegistry(credentialsId: 'dockerhub', toolName: 'Docker') {
		              sh 'docker build -t guilhermesantosdocker/ordem-servico-backend .'
		            }
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
	    unsuccessful {
	        emailext attachLog: true, body: 'Olhar os Logs de Erro:', subject: 'Erro no Build - $PROJECT_NAME - $BUILD_NUMBER - $BUILD_STATUS', to: 'guiromanno@gmail.com'
	    }
	    fixed {
        	emailext attachLog: true, body: 'Olhar os Logs de Sucesso:', subject: 'Build $BUILD_NUMBER - $BUILD_STATUS!!!', to: 'guiromanno@gmail.com'
        }
	}
}