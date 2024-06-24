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
		        withSonarQubeEnv('Sonarqube') {
                    sh './mvnw clean package sonar:sonar'
		        }
		    }
		}
	}
}