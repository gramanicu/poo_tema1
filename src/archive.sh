#!/bin/bash
AFLAGS=-FSr
ANAME=322CA_GramaNicolae_Tema1.zip
ACONTENTS="Readme.md com/"
if zip $AFLAGS $ANAME $ACONTENTS ; then
    echo "Archive created"
else
    ./zip $AFLAGS $ANAME $ACONTENTS
    echo "Archive created"
fi