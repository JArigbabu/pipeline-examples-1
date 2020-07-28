pipeline {
    agent none

    stages {
        stage('Stash') {
            agent {
                kubernetes {
                    yamlFile 'resources/yaml/podTemplate-maven.yml'
                }
            }
            steps {
                container('maven') {
                    git credentialsId: 'githubuserssh', url: 'git@github.com:cccaternberg/maven-project.git'
                    sh 'mvn clean package'
                    stash includes: '**/target/*.war', name: 'myapp'
                    //sh 'ls -l'
                    //sh 'echo Some value from Stage1: $BUILD_NUMBER > buildnumber.txt'
                    // stash includes: '**/buildnumber.txt', name: 'myapp'
                }
            }
        }
        stage('Unstash') {
            agent {
                kubernetes {
                    yamlFile 'resources/yaml/podTemplate-tools-os.yml'
                }
            }
            steps {
                container('curl') {
                    unstash 'myapp'
                    sh 'ls -lR'
                    //sh 'cat buildnumber.txt'
                }
            }
        }
    }
}
