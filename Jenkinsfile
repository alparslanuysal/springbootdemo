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
                }
            }
        }

        stage('Publish JUnit Test Results') {
            steps {
                // Publish JUnit test results to Jenkins
                junit "**/test-reports/*.xml"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    // Execute Maven clean and install
                    def mavenHome = tool 'Maven'
                    def mavenCMD = "${mavenHome}/bin/mvn"
                    //sh "${mavenCMD} clean install"

                    // Execute SonarQube scan
                    withSonarQubeEnv('sonarqube') {
                        sh "${mavenCMD} sonar:sonar"
                    }
                }
            }
        }

        stage('Quality Gate Check') {
            steps {
                script {
                    // Execute Quality Gate check
                    withSonarQubeEnv('sonarqube') {
                        def qualityGate = waitForQualityGate()
                        if (qualityGate.status != 'OK') {
                            error "Quality Gate check failed: ${qualityGate.status}"
                        }
                    }
                }
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
