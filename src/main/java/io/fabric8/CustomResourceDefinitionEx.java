package io.fabric8;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.apiextensions.v1beta1.CustomResourceDefinition;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class CustomResourceDefinitionEx {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            for (Namespace item : client.namespaces().list().getItems()) {
                System.out.println(item.getMetadata().getName());
            }
        }
    }
}
