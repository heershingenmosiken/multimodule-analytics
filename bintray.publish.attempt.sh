#!/usr/bin/env bash
# https://github.com/travis-ci/travis-ci/issues/8549

# Getting current framework version
SDK_VERSION=$(egrep -o "sdkVersion.*=.*" sdk.publish.gradle | egrep -o "'(.*)'" | tr -d "\'")
echo "Running SDK publishing script for $SDK_VERSION version."

### Check if it is snapshot build
if [[ $SDK_VERSION == *"SNAPSHOT"* ]]; then
  echo "[SKIP] $SDK_VERSION is development build and should not be published."
  exit 0
fi

### Checking is it already published

POM_URL="https://dl.bintray.com/dekalo-stanislav/heershingenmosiken/com/heershingenmosiken/multimodule-analytics/$SDK_VERSION/multimodule-analytics-$SDK_VERSION.pom"

if curl --output /dev/null --silent --head --fail "$POM_URL"; then
  echo "[SKIP] Framework version $SDK_VERSION already published."
  exit 0
fi


### Publishing

if ./gradlew bintrayUpload bintrayPublish; then
  git tag $SDK_VERSION -a -m "$SDK_VERSION" HEAD
  git push -q origin $SDK_VERSION
  echo "[SUCCESS] $SDK_VERSION successfully published."
else
  echo "[FAILURE] Failed to publish SDK $SDK_VERSION"
  exit 1
fi
