#!/bin/bash

# NOTE: this script requires jar2app to be installed!
# get it from: https://github.com/wvdschel/jar2app/tree/master
# run this script AFTER running './gradlew build'

MAIN_CLASS="com.ft.routing.App"
JRE_HOME="$(readlink -f "$HOME/.sdkman/candidates/java/current")/*.jdk"
OUT_DIR="build/osx"
APP_NAME="FutureTechRouting.app"

mkdir -p $OUT_DIR
rm -rf $OUT_DIR/*

echo "JRE Home: $JRE_HOME"

jar2app build/libs/ft-*-all.jar $OUT_DIR/$APP_NAME \
    -n "FutureTech\\ Routing" \
    -v "1.0.1" \
    -b "$MAIN_CLASS" \
    -c "Copyright (c) FutureTech Australia 2024" \
    -m $MAIN_CLASS \
    -r $JRE_HOME \
    -i icons/mac.icns
