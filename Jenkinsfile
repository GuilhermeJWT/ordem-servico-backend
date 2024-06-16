pipeline {
	agent any
	stages {
		stage('Build do Projeto'){
			steps {
				sh "mvn clean install -DskipTests"
			}
		}
		stage('Execução dos Testes'){
			steps{
				sh "mvn test"
			}
		}
	}
}