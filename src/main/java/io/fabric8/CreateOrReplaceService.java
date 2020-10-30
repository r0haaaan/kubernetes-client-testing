package io.fabric8;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class CreateOrReplaceService {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Service svc = client.services().load(CreateOrReplaceResource.class.getResourceAsStream("/test-svc.yaml")).get();

            client.resource(svc).inNamespace("default").createOrReplace();
        }
    }
}