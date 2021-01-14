#!/bin/bash

kubectl apply -f minikube-deployment.yaml

kubectl rollout restart deployment/user-service-app -n dmp
