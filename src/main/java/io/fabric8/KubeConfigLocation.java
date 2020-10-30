package io.fabric8;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class KubeConfigLocation {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            System.out.println(client.getConfiguration().getFile().getAbsolutePath());
        }
    }
}