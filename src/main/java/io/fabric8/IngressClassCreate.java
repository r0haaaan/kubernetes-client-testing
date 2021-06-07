package io.fabric8;

import io.fabric8.kubernetes.api.model.networking.v1.IngressClass;
import io.fabric8.kubernetes.api.model.networking.v1.IngressClassBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class IngressClassCreate {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            IngressClass ingressClass = new IngressClassBuilder()
                    .withNewMetadata().withName("external-lb").endMetadata()
                    .withNewSpec()
                    .withController("example.com/ingress-controller")
                    .withNewParameters("k8s.example.com", "IngressParameters", "external-lb", "", "")
                    .endSpec()
                    .build();

            client.network().v1().ingressClasses().create(ingressClass);
        }
    }
}