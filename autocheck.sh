#!/bin/bash
output=$( git pull origin master; );
if [ "$output" != 'Already up to date.' ]; then
    cd ./src
    ./tema-checker.sh
fi