#!/bin/bash
set -v

# Install logging monitor. The monitor will automatically pick up logs sent to syslog.
curl -s "https://storage.googleapis.com/signals-agents/logging/google-fluentd-install.sh" | bash
service google-fluentd restart &

export GCSFUSE_REPO=gcsfuse-wily
echo "deb http://packages.cloud.google.com/apt $GCSFUSE_REPO main" | sudo tee /etc/apt/sources.list.d/gcsfuse.list
curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -

# Install dependencies
apt-get update
apt-get install -yq openjdk-8-jdk nginx gcsfuse

mkdir /opt/app
git clone https://github.com/qualfacul/hades.git /opt/app

# Get bucket files
mkdir /mnt/bucket
mkdir /etc/nginx/ssl
gcsfuse qual-facul.appspot.com /mnt/bucket
cp /mnt/bucket/ssl/qualfacul.com.key /etc/nginx/ssl
cp /mnt/bucket/ssl/qualfacul.com.crt /etc/nginx/ssl
cp /mnt/bucket/hades/hades.key /opt/app/
cp /mnt/bucket/hades/production.properties /opt/app/src/main/resources/application.properties

# Nginx conf
ln -sf /opt/app/configuration/nginx.conf /etc/nginx/nginx.conf

# Gradle release
cd /opt/app
./gradlew runWeb &

systemctl restart nginx
