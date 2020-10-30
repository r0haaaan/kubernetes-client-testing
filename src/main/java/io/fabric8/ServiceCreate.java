package io.fabric8;

import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.Collections;

public class ServiceCreate {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Service svc = new ServiceBuilder()
                    .withNewMetadata().withName("my-service").endMetadata()
                    .withNewSpec()
                    .addToSelector(Collections.singletonMap("app", "MyApp"))
                    .addNewPort()
                    .withProtocol("TCP")
                    .withPort(80)
                    .withTargetPort(new IntOrString(9376))
                    .endPort()
                    .endSpec()
                    .build();
            client.services().inNamespace("default").createOrReplace(svc);
        }
    }
}