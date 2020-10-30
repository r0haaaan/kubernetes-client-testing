package io.fabric8;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class TestBasicPod {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            String namespace = "default";
            Pod pod = new PodBuilder()
                    .withNewMetadata().withName("test-pod1").endMetadata()
                    .withNewSpec()
                    .addNewContainer()
                    .withName("nginx")
                    .withImage("nginx:1.7.9")
                    .endContainer()
                    .endSpec()
                    .build();

            client.pods().inNamespace(namespace).createOrReplace(pod);

            client.pods().inNamespace(namespace).withName("test-pod1")
                    .terminated().getLog();
        }
    }
}