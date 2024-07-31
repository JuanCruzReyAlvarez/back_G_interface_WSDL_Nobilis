#!bin/bash

function printmessage {
    echo " " &&
    echo " " &&
    echo "-------------------------------------------" &&
    echo "[BUILD] $1" &&
    echo "-------------------------------------------" &&
    echo " " &&
    echo " "
}

function printHelp {
    echo "This is the Back G.Interface WSDL Of Nobilis Build, Deploy and Run In development mode Helper!" &&
    echo " " &&
    echo "-a  ----- Build and Deploy back G.Interface WSDL Of Nobili  " &&
    echo "-t  ----- Run unit testing to complete build  " &&
    echo "-m  ----- clean install maven  " &&
    echo "-h  ----- This help guide  " &&
    echo "-------------------------------------------" &&
    echo "if no parameters are passed the helper will run it all" &&
    echo "-------------------------------------------" 
}

function runMavenBuild {
    if [[ $1 == *"m"* ]]; then
        mvn clean install 
    else
        mvn clean install -DskipTests
    fi
}



function buildAndDeployBackGInterfaceWSDLNobilis {
    cd $NOBILIS_PATH/backGInterfaceWSDLNobilis &&
    runMavenBuild $1 &&
    printmessage "Back_G_Interface_WSDL_Nobilis Built and Deployed"
}


if [[ $1 == *"a"* ]]; then
    buildAndDeployBackGInterfaceWSDLNobilis $1
fi

if [[ $1 == *"h"* ]]; then
    printHelp
fi





