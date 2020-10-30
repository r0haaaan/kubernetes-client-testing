package io.fabric8.openshift;


import io.fabric8.kubernetes.client.utils.KubernetesResourceUtil;
import io.fabric8.openshift.api.model.DeploymentConfig;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class CheckDeploymentConfigReady {
    public static void main(String[] args) {
        try (OpenShiftClient client = new DefaultOpenShiftClient()) {
            DeploymentConfig deploymentConfig = client.deploymentConfigs()
                    .inNamespace("rokumar")
                    .withName("weather-web-application")
                    .get();
            //System.out.println(KubernetesResourceUtil.isResourceReady(deploymentConfig));
        }
    }
}