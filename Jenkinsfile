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
		stage('Quality Gate'){
		    steps {
		         timeout(time: 1, unit: 'HOURS') {
		           script  {
                   def qg = waitForQualityGate()
                   if (qg.status != 'OK') {
                         error "Pipeline aborted due to quality gate failure: ${qg.status}"
                   }
               }
            }
		    }
		}
	}
}