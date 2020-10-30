package io.fabric8;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.utils.KubernetesResourceUtil;

public class DeploymentCreateOrReplace {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Deployment deployment = client.apps().deployments().inNamespace("default").withName("nginx-deployment").get();

            System.out.println("ResourceVersion : " + KubernetesResourceUtil.getResourceVersion(deployment));
            deployment = client.apps().deployments().inNamespace("default").withName("nginx-deployment").replace(deployment);
            System.out.println("ResourceVersion : " + KubernetesResourceUtil.getResourceVersion(deployment));
        }
    }
}