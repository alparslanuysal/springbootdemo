pipeline { 
    agent any 

    environment {
        DOCKER_IMAGE = 'springbootdemo'
        HOST_PATH = '${WORKSPACE}/junit-reports'
        CONTAINER_PATH = '/workspace/app/target/surefire-reports'
    }
    
    stages {
        
        stage('Build and Test in Docker') {
            steps {
                script {
                    // Build Docker image
                    sh "docker build -t ${DOCKER_IMAGE} ."

                    // Run tests inside the Docker container
                    sh "docker run -v ${HOST_PATH}:${CONTAINER_PATH} ${DOCKER_IMAGE} mvn clean test"
                }
            }
        }

        stage('Publish JUnit Test Results') {
            steps {
                // Publish JUnit test results to Jenkins
                junit "${HOST_PATH}/**/*.xml"
            }
        }
    }

post {
        always {
            // Cleanup: remove the Docker container
            script {
                sh "docker container rm -f $(docker container ls -a -q --filter ancestor=${DOCKER_IMAGE})"
            }
            
            // Cleanup: remove the Docker image
            script {
                sh "docker rmi ${DOCKER_IMAGE}"
            }

            // Cleanup: remove JUnit reports directory
            script {
                sh "rm -rf ${HOST_PATH}"
            }
        }
    }

}
