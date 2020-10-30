package io.fabric8;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class CreateOrReplaceResource {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Pod pod = client.pods().inNamespace("default").withName("ex5h7qc").get();
            client.resource(pod).inNamespace("default").createOrReplace();
        }
    }
}