pipeline {
    agent none
    stages {
        stage('init-stage') {
            steps {
                echo "This is the init stage"
            }
        }
        stage('run-parallel-branches') {
            steps {
                parallel(
                        a: {
                            echo "This is branch a"
                        },
                        b: {
                            echo "This is branch b"
                        }
                )
            }
        }
        stage('last-stage') {
            steps {
                echo "This is the init stage"
            }
        }
    }
}