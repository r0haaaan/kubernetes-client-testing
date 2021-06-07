package io.fabric8;

import io.fabric8.kubernetes.api.model.DeletionPropagation;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class DeploymentCrud {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Deployment deployment = new DeploymentBuilder().build();

    // Create
    client.apps().deployments().inNamespace("default").create(deployment);

    // Get
    Deployment deploy = client.apps().deployments()
            .inNamespace("default")
            .withName("deploy1")
            .get();

    // Update, adding dummy annotation
    Deployment updatedDeploy = client.apps().deployments()
            .inNamespace("default")
            .withName("deploy1")
            .edit(p -> new DeploymentBuilder(p).editMetadata()
                          .addToAnnotations("foo", "bar")
                          .endMetadata()
                          .build());

    // Deletion
    Boolean isDeleted = client.apps().deployments()
            .inNamespace("default")
            .withName("deploy1")
            .delete();

    // Deletion with some propagation policy
    Boolean bDeleted = client.apps().deployments()
            .inNamespace("default")
            .withName("deploy1")
            .withPropagationPolicy(DeletionPropagation.BACKGROUND)
            .delete();

        }
    }
}
