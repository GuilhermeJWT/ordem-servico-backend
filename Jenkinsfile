pipeline {
	agent any
	tools {
	    maven 'Maven 3.8.7'
	}
	stages {
		stage('Clonando o Projeto'){
			steps {
				git branch: 'main', url: 'https://github.com/GuilhermeJWT/ordem-servico-backend.git'
			}
		}
		stage('Build') {
		    steps {
		        sh "mvn package"
		    }
		}
		stage('Tests') {
		    steps {
		        sh "mvn test"
		    }
		}
	}
}