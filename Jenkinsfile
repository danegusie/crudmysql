
pipeline {
    environment {
        PROJECT_NAME = 'crud repo'
        PROJECT_TEAM = 'tutorial team'

    }

    agent { 
        // docker {   image 'maven:3.9.0-eclipse-temurin-11'   }
        docker {   image 'maven:latest'   }
    }


    // parameters {
    //     choice(
    //         name: 'DEPLOY_TARGET',
    //         choices: ['none', 'dev', 'tst', 'int', 'prd'], description: 'Select deployment target'
    //     )

    // }

    stages {
        stage('Set Up Environment') {
            steps {
                // setEnv()
                echo 'setting up environment'
            }
        }

        stage('maven Build') {

            steps {
               sh  ' mvn ${maven_proxy_deploy} clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                script {
                    maven.mvnTest(extraArgs: '-s settings.xml verify')
                }
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    junit 'target/failsafe-reports/*.xml'
                }
            }
        } 


        stage('Deploy DEV') {
            when {
                expression { params.DEPLOY_TARGET == 'dev' }
            }
            steps {
              
                    script {
                        pcf.deploy(
                            extraArgs: "--var dbpasswd=${DB_PASS} --var fileBrokerAuthUserName=${AUTH_USERNAME} --var fileBrokerAuthPassword=${AUTH_PASSWORD} --var encodedCreds=${ENCODED_CREDS_PASS}"
                        )
                    }
                
            }
        }

        stage('Deploy TEST') {

              when {
                beforeAgent true
                //branch 'service-value-rpt-month-1.0.0.0'
                /*anyOf {
                    branch 'stage'
                    branch 'master'
                }*/
                expression { params.DEPLOY_TARGET == 'tst' }
            }
            steps {

                
                    script {
                        pcf.deploy(

                            extraArgs: "--var dbpasswd=${DB_PASS} --var fileBrokerAuthUserName=${AUTH_USERNAME} --var fileBrokerAuthPassword=${AUTH_PASSWORD} --var encodedCreds=${ENCODED_CREDS_PASS}"
                        )
                    }
                
            }
        }



    }
}
