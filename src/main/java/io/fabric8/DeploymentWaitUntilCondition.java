package io.fabric8;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.concurrent.TimeUnit;

public class DeploymentWaitUntilCondition {
    public static void main(String[] args) {
        String namespace = "default";
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Deployment d1 = new DeploymentBuilder(getSampleDeployment()).editMetadata().withName("d1").endMetadata().build();
            Deployment d2 = new DeploymentBuilder(d1).editMetadata().withName("d2").endMetadata().build();
            Deployment d3 = new DeploymentBuilder(d1).editMetadata().withName("d3").endMetadata().build();

// Create 1st Deployment
            client.apps().deployments().inNamespace(namespace).createOrReplace(d1);
            client.apps().deployments().inNamespace(namespace).withName("d1").waitUntilReady(2, TimeUnit.SECONDS);
// Create 2nd Deployment
            client.apps().deployments().inNamespace(namespace).createOrReplace(d2);
            client.apps().deployments().inNamespace(namespace).withName("d2").waitUntilReady(2, TimeUnit.SECONDS);
// Create 3rd Deployment
            client.apps().deployments().inNamespace(namespace).createOrReplace(d3);
            client.apps().deployments().inNamespace(namespace).withName("d3").waitUntilReady(2, TimeUnit.SECONDS);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private static Deployment getSampleDeployment() {
        return new DeploymentBuilder()
                .withNewMetadata()
                .withName("nginx-deployment")
                .addToLabels("app", "nginx")
                .endMetadata()
                .withNewSpec()
                .withReplicas(1)
                .withNewSelector()
                .addToMatchLabels("app", "nginx")
                .endSelector()
                .withNewTemplate()
                .withNewMetadata()
                .addToLabels("app", "nginx")
                .endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName("nginx")
                .withImage("nginx:1.7.9")
                .addNewPort().withContainerPort(80).endPort()
                .endContainer()
                .endSpec()
                .endTemplate()
                .endSpec()
                .build();
    }
}