package io.fabric8;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class DeploymentRollingUpdate {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Deployment deployment = client.apps()
                    .deployments()
                    .inNamespace("default")
                    .withName("nginx-deployment")
                    .get();
            deployment.getSpec().getTemplate().getSpec().getContainers().get(0).setImage("nginx:mainline-alpine");
            client.apps().deployments().inNamespace("default").withName("nginx-deployment").replace(deployment);
        }
    }
}