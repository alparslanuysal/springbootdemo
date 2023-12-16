pipeline { 
    agent any 

    environment {
        DOCKER_IMAGE = 'springbootdemo'
        HOST_PATH = '${WORKSPACE}/test-reports'
        CONTAINER_PATH = '/workspace/test-reports/surefire-reports'
    }
    
    stages {
        
        stage('Build and Test in Docker') {
            steps {
                script {
                    // Build Docker image
                    sh "docker build -t ${DOCKER_IMAGE} ."

                    // Get tests inside the Docker container
                    sh "docker create --name temp-container ${DOCKER_IMAGE}:latest"
                    sh "docker cp temp-container:${CONTAINER_PATH} ${HOST_PATH}"
                    sh "docker rm temp-container"
                }
            }
        }

        stage('Publish JUnit Test Results') {
            steps {
                // Publish JUnit test results to Jenkins
                junit "${WORKSPACE}/test-reports/*.xml"
            }
        }
    }

post {
        always {
            // Cleanup: remove the Docker container
            script {
                sh 'docker container rm -f $(docker container ls -a -q --filter ancestor=${DOCKER_IMAGE})'
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
