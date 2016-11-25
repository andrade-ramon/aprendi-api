#!/bin/bash
set -ve
gcloud compute ssh hades-web --zone us-central1-b --command /opt/app/configuration/deploy-for-instance.sh
