package io.fabric8;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class LoadServiceYaml {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Service service = client.services()
                    .load(LoadServiceYaml.class.getResourceAsStream("/test-svc.yaml"))
                    .get();

            client.services().inNamespace("rokumar").createOrReplace(service);
        }
    }
}
