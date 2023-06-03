@Library('dot-shared-lib') _

pipeline {
    environment {
        PROJECT_NAME = 'service-value-rpt-week'
        PROJECT_TEAM = 'athlon-germany'
        REGISTRY = 'reg-dhc.app.corpintra.net'
        SONAR_CREDENTIALS = "SONAR_TOKEN"
        SONAR_HOST = "https://itfto-sonar.dot.daimler.com"
        maven_proxy_deploy = "-Dhttp.proxyHost=security-proxy.emea.svc.corpintra.net -Dhttp.proxyPort=3128 -Dhttps.proxyHost=security-proxy.emea.svc.corpintra.net -Dhttps.proxyPort=3128 -Dhttp.nonProxyHosts=*.dot-dhc-int.app.corpintra.net|*.daimler.com|53.0.0.0/8 -Dhttps.nonProxyHosts=*.dot-dhc-int.app.corpintra.net|*.daimler.com|53.0.0.0/8"
    }

    agent { label 'dot-playground' }
    parameters {
        choice(
            name: 'DEPLOY_TARGET',
            choices: ['none', 'dev', 'tst', 'int', 'prd'], description: 'Select deployment target'
        )
        credentials(
            credentialType: 'com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl',
            defaultValue: '',
            description: 'Deployment Credentials for DEV/TST/INT',
            name: 'athde.paas.user-scoped.p03',
            required: false
        )

        credentials(
            credentialType: 'com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl',
            defaultValue: '',
            description: 'Deployment Credentials PRD',
            name: 'athde.paas.user-scoped.p04',
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
            agent {
                dockerfile {
                    dir 'dockerbuild'
                    args "--user root"
                    reuseNode true
                }
            }
            steps {
               sh  ' mvn ${maven_proxy_deploy} clean install -DskipTests'
            }
        }

        /* stage('Test') {
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
        } */

        // stage('Scan with SonarQube'){
        //     steps {
        //         script {
        //             sonar.scan()
        //         }
        //     }
        // }

        stage('Deploy to DHC PaaS P03 - DEV') {
            when {
                expression { params.DEPLOY_TARGET == 'dev' }
            }
            steps {
                withCredentials([
                    string(
                        credentialsId: 'athde.proj.service-value-rpt-week.db.pass.dev',
                        variable: 'DB_PASS'
                    ),
                    usernamePassword(
                        credentialsId: 'athde.proj.service-value-rpt-week.ida-schnittstelle.pass.dev',
                        usernameVariable: 'ENCODED_CREDS_USER',
                        passwordVariable: 'ENCODED_CREDS_PASS'
                    ),
                    usernamePassword(
                        credentialsId: 'athde.proj.athlon-file-broker.basic-auth.service-value-weekly.dev',
                        usernameVariable: 'AUTH_USERNAME',
                        passwordVariable: 'AUTH_PASSWORD'
                    )
                ])
                {
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
        }

        stage('Deploy to DHC PaaS P03 - TST') {

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
                withCredentials([
                    string(
                        credentialsId: 'athde.proj.service-value-rpt-week.db.pass.tst',
                        variable: 'DB_PASS'
                    ),
                    usernamePassword(
                        credentialsId: 'athde.proj.service-value-rpt-week.ida-schnittstelle.pass.tst',
                        usernameVariable: 'ENCODED_CREDS_USER',
                        passwordVariable: 'ENCODED_CREDS_PASS'
                    ),
                    usernamePassword(
                        credentialsId: 'athde.proj.athlon-file-broker.basic-auth.service-value-weekly.tst',
                        usernameVariable: 'AUTH_USERNAME',
                        passwordVariable: 'AUTH_PASSWORD'
                    )
                ])
                {
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

        stage('Deploy to DHC PaaS P03 - INT') {
              when {
                beforeAgent true
                //branch 'service-value-rpt-month-1.0.0.0'
                /*anyOf {
                    branch 'stage'
                    branch 'master'
                }*/
                expression { params.DEPLOY_TARGET == 'int' }
            }
            steps {
                withCredentials([
                    string(
                        credentialsId: 'athde.proj.service-value-rpt-week.db.pass.int',
                        variable: 'DB_PASS'
                    ),
                    usernamePassword(
                        credentialsId: 'athde.proj.service-value-rpt-week.ida-schnittstelle.pass.int',
                        usernameVariable: 'ENCODED_CREDS_USER',
                        passwordVariable: 'ENCODED_CREDS_PASS'
                    ),
                    usernamePassword(
                        credentialsId: 'athde.proj.athlon-file-broker.basic-auth.service-value-weekly.int',
                        usernameVariable: 'AUTH_USERNAME',
                        passwordVariable: 'AUTH_PASSWORD'
                    )
                ])
                {
                    script {
                        pcf.deploy(
                            paasApi: 'https://api.dhcpaas-sys-p03-edc5.dhc.corpintra.net',
                            paasCredentialsId: 'athde.paas.user-scoped.p03',
                            paasOrg: 'P03T100028',
                            paasCloudSpace: 'service-value-rpt',
                            paasAppName: 'service-value-rpt-week-int',
                            manifestPath: 'manifest-int.yml',
                            extraArgs: "--var dbpasswd=${DB_PASS} --var fileBrokerAuthUserName=${AUTH_USERNAME} --var fileBrokerAuthPassword=${AUTH_PASSWORD} --var encodedCreds=${ENCODED_CREDS_PASS}"
                        )
                    }
                }
            }
        }

        stage('Deploy to DHC PaaS P04 - PROD') {
            when {
                beforeAgent true
                branch 'master'
                /*anyOf {
                    branch 'stage'
                    branch 'master'
                }*/
                expression { params.DEPLOY_TARGET == 'prd' }
            }
            steps {
                withCredentials([
                    string(
                        credentialsId: 'athde.proj.service-value-rpt-week.db.pass.prd',
                        variable: 'DB_PASS'
                    ),
                    usernamePassword(
                        credentialsId: 'athde.proj.service-value-rpt-week.ida-schnittstelle.pass.prd',
                        usernameVariable: 'ENCODED_CREDS_USER',
                        passwordVariable: 'ENCODED_CREDS_PASS'
                    ),
                    usernamePassword(
                        credentialsId: 'athde.proj.athlon-file-broker.basic-auth.service-value-weekly.prd',
                        usernameVariable: 'AUTH_USERNAME',
                        passwordVariable: 'AUTH_PASSWORD'
                    )
                ])
                {
                    script {
                        pcf.deploy(
                            paasApi: 'https://api.dhcpaas-sys-p04-edc5.dhc.corpintra.net',
                            paasCredentialsId: 'athde.paas.user-scoped.p04',
                            paasOrg: 'P04T005083',
                            paasCloudSpace: 'service-value-rpt',
                            paasAppName: 'service-value-rpt-week',
                            manifestPath: 'manifest-prd.yml',
                            extraArgs: "--var dbpasswd=${DB_PASS} --var encodedCreds=${ENCODED_CREDS_PASS} --var fileBrokerAuthUserName=${AUTH_USERNAME} --var fileBrokerAuthPassword=${AUTH_PASSWORD}"
                        )
                    }
                }
            }
        }
    }
}
