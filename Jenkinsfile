pipeline{
    agent any
    stages{
        stage("Build Jar"){
            steps{
                echo "========executing Build Jar========"
                sh "ssh -V"
                sh "mvn -version"
            }
        }
        stage("Build Image"){
            steps{
                echo "========executing Build Image========"
            }
        }
        stage("Run container"){
            steps{
                echo "========executing Run container========"
            }
        }
        stage("Deployment"){
            steps{
                echo "========executing Deployment========"
            }
        }
    }
}
