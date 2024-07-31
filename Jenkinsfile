pipeline {
	agent any
    parameters {
        string (
            name: 'email' ,
            defaultValue: 'guiromanno@gmail.com' ,
            description: 'Endereço de e-mail para enviar notificação' )
    }
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
	}
	post {
           failure {
              emailext (
                 subject : "${JOB_NAME}.${BUILD_NUMBER} Falhou" ,
                 mimeType : 'text/html' ,
                 to : " $email " ,
                 body : "${JOB_NAME}.${BUILD_NUMBER} Falhou"
                 )
                 }
              success {
                 emailext (
                   subject : "${JOB_NAME}.${BUILD_NUMBER} Passou com Sucesso!" ,
                   mimeType : 'text/html' ,
                   to : " $email " ,
                   body : "${JOB_NAME}.${BUILD_NUMBER} Passou com Sucesso!"
                   )
         }
    }
}