package io.fabric8;

import io.fabric8.kubernetes.api.model.DeletionPropagation;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class PodLogOptions {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            String log = client.pods()
              .inNamespace("ibm-system")
              .withName("catalog-operator-67646bfcdb-llxwz")
              .usingTimestamps()
              .getLog();
            System.out.println(log);

client.pods()
        .inNamespace("test")
        .withName("foo")
        .withPropagationPolicy(DeletionPropagation.BACKGROUND)
        .withGracePeriod(0)
        .delete();
        }
    }
}
