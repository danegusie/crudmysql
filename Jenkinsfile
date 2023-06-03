
pipeline {
    environment {
        PROJECT_NAME = 'crud repo'
        PROJECT_TEAM = 'tutorial team'

    }

    agent { 
        docker {   image 'ubuntu:18.04'   }
    }

    parameters {
        choice(
            name: 'DEPLOY_TARGET',
            choices: ['none', 'dev', 'tst', 'int', 'prd'], description: 'Select deployment target'
        )
        credentials(

            required: false
        )

    }

    stages {
        stage('Set Up Environment') {
            steps {
                setEnv()
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
              
                {}
                    script {
                        pcf.deploy(
                            paasApi: 'https://api.dhcpaas-sys-p03-edc5.dhc.corpintra.net',
                            paasCredentialsId: 'athde.paas.user-scoped.p03',
                            paasOrg: 'P03T100023',
                            paasCloudSpace: 'service-value-rpt-week-dev',
                            paasAppName: 'service-value-rpt-week-dev',
                            manifestPath: 'manifest-dev.yml',
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
                            paasApi: 'https://api.dhcpaas-sys-p03-edc5.dhc.corpintra.net',
                            paasCredentialsId: 'athde.paas.user-scoped.p03',
                            paasOrg: 'P03T100023',
                            paasCloudSpace: 'service-value-rpt-week-tst',
                            paasAppName: 'service-value-rpt-week-tst',
                            manifestPath: 'manifest-tst.yml',
                            extraArgs: "--var dbpasswd=${DB_PASS} --var fileBrokerAuthUserName=${AUTH_USERNAME} --var fileBrokerAuthPassword=${AUTH_PASSWORD} --var encodedCreds=${ENCODED_CREDS_PASS}"
                        )
                    }
                
            }
        }



    }
}
