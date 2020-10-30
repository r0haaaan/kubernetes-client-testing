package io.fabric8;

import io.fabric8.kubernetes.api.model.DeletionPropagation;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class GracePeriodConfig {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            client.apps().statefulSets().inNamespace("rokumar").withName("nginx").withPropagationPolicy(DeletionPropagation.FOREGROUND).withGracePeriod(0).delete();
        }
    }
}
