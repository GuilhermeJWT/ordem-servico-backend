pipeline {
	agent any
	tool = name: 'JDK_LOCAL', type: 'jdk'
	tool name: 'GUI_MAVEN_JENKINS', type: 'maven'
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