pipeline {
    agent any
    environment {
        work_dir='/home/jenkins/work'
        bundle='/home/jenkins/bin/bundle'
        deploy_dir='/home/jenkins/deploy'
    }
    stages { // 私の場合、特にステージごとの環境変数は必要なかったです。
        stage('build') {
            environment { 
                LOCAL_VAR='/home/jenkins/target_dir' 
            }
            steps {
                sh 'scp -i ~/kikkawa-aws.pem -p ./HelloWorld.java ec2-user@172.31.29.70:/tmp'
                sh 'ssh -i ~/kikkawa-aws.pem ec2-user@172.31.29.70 sudo mv /tmp/HelloWorld.java /opt/apache-tomcat/webapps/hellosample/WEB-INF/classes'
                sh 'ssh -i ~/kikkawa-aws.pem ec2-user@172.31.29.70 sudo javac -classpath /opt/apache-tomcat/lib/servlet-api.jar /opt/apache-tomcat/webapps/hellosample/WEB-INF/classes/HelloWorld.java'
            }
        }
    }
}
