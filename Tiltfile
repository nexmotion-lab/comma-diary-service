docker_build('sunwoo3856/diary-service', '.')

k8s_yaml(kustomize('k8s/overlays/development'))

k8s_resource('diary-service', port_forwards=['9003'])