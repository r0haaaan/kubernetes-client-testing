package io.fabric8;

import io.fabric8.kubernetes.api.model.DeletionPropagation;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class DeploymentDelete {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            client.apps()
                    .deployments()
                    .inNamespace("default")
                    .withName("nginx-deployment")
                    .withPropagationPolicy(DeletionPropagation.FOREGROUND)
                    .withGracePeriod(0L)
                    .delete();
        }
    }
}
