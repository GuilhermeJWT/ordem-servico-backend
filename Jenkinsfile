pipeline {
	agent any
	stages {
		stage('Clonando o Projeto'){
			steps {
				git branch: 'main', url: 'https://github.com/GuilhermeJWT/ordem-servico-backend.git'
			}
		}
		stage('Build') {
		    steps {
		        tool name: 'GUI_MAVEN_JENKINS', type: 'maven'
		        sh "mvn -B -DskipTests clean package"
		    }
		}
	}
}