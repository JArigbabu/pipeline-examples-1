This repo contains example Jenkins Pipelines inside the jobs directory.

The related pipeline jobs in Jenkins can be generated by JobDSL

# Start

1. install the  JobDSL[https://wiki.jenkins.io/display/JENKINS/Job+DSL+Plugi] Plugin
 
2. Create and run a new Pipeline job which points to `Jenkinsfile`  (which does some pre-work such as gathering GHAPI libs in the Classpath)
(http://github-api.kohsuke.org/ is used to scan the GH organisation, see `Seed.groovy` to adjust the values, if required)

3. jobs are generated in the pipeline-example folder 


if you try to run the Jenkinsfiles standalone, you have to fix the path to the yamlFile of some `jobs/Jenkinsfile-*` in this area to your belongings
```
agent {
    kubernetes {
        yamlFile 'yaml/podTemplate.yml'
    }
}
```



## Pre-requirerments: Set up credentials

Setup the following credentials:  (used by some pipelines)

* githubssh=GH User and SSHkey (secret text)
* githubaccesstoken=GH Access token
* as well as the dockerhub  credentials for the kaniko  docker build/push job: (see  jobs/Jenkinsfile-docker-build-kaniko.groovy)
(GCR is not implemented yet, docker hub is used in the example pipeline)  See instructions below to set up 

## A simple-docker-kaniko-pipeline-example
A simple Dockerfile to build with kaniko

### Configure

### rename kubctl-create-secret.sh.default
```
cp -f scripts/kubctl-create-secret.sh.default kubctl-create-secret.sh
```
#### adjust your docker registry values
NORTE: Special characters in password must escape!
```
kubectl create secret docker-registry docker-credentials \
    --docker-username=><USER>  \
    --docker-password=<PASSWORD> \
    --docker-email=<EMAIL>
```
#### create the scercet
```
./kubctl-create-secret.sh
```
#### docker push manually
```
docker login
sudo docker build -t caternberg/hellonode:1.1 .
docker push caternberg/hellonode:1.1
```
see
* https://go.cloudbees.com/docs/cloudbees-core/cloud-install-guide/kubernetes-using-kaniko/
* https://support.cloudbees.com/hc/en-us/articles/360019236771-How-to-build-my-own-docker-images-in-CloudBees-Core-on-Modern-Cloud-Platforms
* https://support.cloudbees.com/hc/en-us/articles/360031223512-What-you-need-to-know-when-using-Kaniko-from-Kubernetes-Jenkins-Agents
* https://kubernetes.io/docs/concepts/configuration/secret/#decoding-a-secret
* https://kubernetes.io/docs/concepts/configuration/secret/#overview-of-secrets
* https://www.thenativeweb.io/blog/2018-08-14-11-32-kubernetes-building-docker-images-within-a-cluster/


