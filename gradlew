#!/bin/bash
APP_BASE_NAME=`basename "$0"`
APP_HOME=`cd "$(dirname "$0")" ; pwd`
java -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
