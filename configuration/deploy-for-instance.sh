#!/bin/bash
# Deploy script for zeus

set -ve

HADES_TEMP_DIR=/tmp/hades
HADES_PROD_DIR=/opt/app

git clone https://github.com/qualfacul/hades.git $HADES_TEMP_DIR

mkdir $HADES_TEMP_DIR/bucket
gcsfuse qual-facul.appspot.com $HADES_TEMP_DIR/bucket

cp $HADES_TEMP_DIR/bucket/hades/production.properties $HADES_TEMP_DIR/src/main/resources/application.properties
cp $HADES_TEMP_DIR/bucket/hades/hades.key $HADES_TEMP_DIR/
fusermount -u $HADES_TEMP_DIR/bucket
rm -r $HADES_TEMP_DIR/bucket

ps -aux | grep "[j]ava" | awk '{print $2}' | xargs kill -9 
mv $HADES_TEMP_DIR $HADES_PROD_DIR

cd $HADES_PROD_DIR

./gradlew runWeb &

rm -rfv $HADES_TEMP_DIR
