pipeline { 
    agent any 
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Clone') { 
            steps { 
                sh 'echo Clone' 
            }
        }
        stage('Build'){
            steps {
                sh 'docker build -t springbootdemo:latest .'
            }
        }
    }
}
