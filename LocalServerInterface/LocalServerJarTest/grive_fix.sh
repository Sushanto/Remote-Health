#!/bin/bash

GRIVEDIR=RHDrive/
SLEEPVAL=5

cd $GRIVEDIR
while true; do
        echo "Running Grive to synchronize with cloud......"
        grive
        sleep $SLEEPVAL
done
