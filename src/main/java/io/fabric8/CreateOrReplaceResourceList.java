package io.fabric8;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class CreateOrReplaceResourceList {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            client.load(CreateOrReplaceResourceList.class.getResourceAsStream("/test-list.yml"))
                    .inNamespace("default")
                    .deletingExisting()
                    .createOrReplace();
        }
    }
}