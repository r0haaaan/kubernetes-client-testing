package io.fabric8;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class NamespaceDemo {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Namespace ns = client.namespaces().withName("default").get();
            System.out.println(ns.getMetadata().getName() + " " + ns.getMetadata().getAnnotations());
        }
    }
}
