#!/usr/bin/env bash
# https://github.com/travis-ci/travis-ci/issues/8549



# Getting current framework version
SDK_VERSION=$(egrep -o "sdkVersion.*=.*" sdk.gradle | egrep -o "'(.*)'" | tr -d "\'")
echo "Running SDK publishing script for $SDK_VERSION version."

# Getting publishing credentials
if [ -z "$CI" ]; then
    # Getting auth properties for artifactory
    . ~/.gradle/gradle.properties
else
    # Getting artifactory from CI
    artifactory_username=$ARTIFACTORY_USER
    artifactory_password=$ARTIFACTORY_PASS
fi

# Verify that we have proper credentials

if [ -z "$artifactory_username" ]; then
    echo "Can not resolve artifactory_username for framework $SDK_VERSION publishing."
    exit 1
fi

if [ -z "$artifactory_password" ]; then
    echo "Can not resolve $artifactory_password for framework $SDK_VERSION publishing."
    exit 1
fi

### Check if it is snapshot build
if [[ $SDK_VERSION == *"SNAPSHOT"* ]]; then
  echo "$SDK_VERSION is development build and should not be published."
  exit 0
fi


### Checking is it already published



POM_URL="https://dl.bintray.com/dekalo-stanislav/heershingenmosiken/com/heershingenmosiken/multimodule-analytics/$SDK_VERSION/multimodule-analytics-$SDK_VERSION.pom"

if curl --output /dev/null --silent --head --fail -u $artifactory_username:$artifactory_password "$CORE_API_POM_URL"; then
  echo "SDK version $SDK_VERSION already published."
  exit 0
fi


### Publishing

if ./gradlew artifactoryPublish artifactoryDeploy; then
  git tag $SDK_VERSION -a -m "$SDK_VERSION" HEAD
  git push -q origin $SDK_VERSION
  echo "$SDK_VERSION successfully published."
else
  echo "Failed to publish SDK $SDK_VERSION"
  exit 1
fi



