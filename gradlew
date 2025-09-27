#!/bin/sh

# Gradle startup script for UNIX systems

APP_HOME=$(cd "$(dirname "$0")" && pwd)

# Use Java from JAVA_HOME or try to find it
if [ -n "$JAVA_HOME" ]; then
    JAVA="$JAVA_HOME/bin/java"
else
    JAVA=$(which java)
fi

if [ ! -x "$JAVA" ]; then
    echo "Error: JAVA_HOME is not set and no 'java' command could be found"
    exit 1
fi

CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

exec "$JAVA" \
    -classpath "$CLASSPATH" \
    org.gradle.wrapper.GradleWrapperMain \
    "$@"
