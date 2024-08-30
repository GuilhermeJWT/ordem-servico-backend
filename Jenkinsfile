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
        stage('Deploy to EC2') {
            steps {
                sshagent([SSH_CREDENTIALS]) {
            sh """
                scp -o StrictHostKeyChecking=no target/ordem-servico-backend.jar ubuntu@${EC2_HOST}:${DEPLOY_DIR}
                ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} << 'EOF'
                cd ${DEPLOY_DIR}
                rm -f ordem-servico-backend.jar ordemservicobackend.log
                mv ordem-servico-backend.jar ordemservicobackend.jar

                # Executa o JAR em segundo plano e redireciona a saída para um log
                nohup java -jar ordemservicobackend.jar --spring.profiles.active=prod > ordemservicobackend.log 2>&1 || exit 1
                EOF
            """
                }
            }
        }
        stage('Health Check') {
            steps {
                script {
                    retry(5) {  // Tenta 5 vezes
                        sleep(time: 25, unit: 'SECONDS')  // Espera 25 segundos entre cada tentativa
                        def response = sh(script: "curl -s http://${EC2_HOST}:8085/api/health/check", returnStdout: true).trim()
                        if (response != "UP") {
                            error("Aplicação ainda não iniciou, ou ocorrou algum erro durante o processo, observe os logs.")
                        }
                    }
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