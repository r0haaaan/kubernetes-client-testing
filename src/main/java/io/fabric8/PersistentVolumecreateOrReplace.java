package io.fabric8;

import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class PersistentVolumecreateOrReplace {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            PersistentVolumeClaim pvc = client.persistentVolumeClaims().load(PersistentVolumecreateOrReplace.class.getResourceAsStream("/test-pvc.yml")).get();

            client.persistentVolumeClaims().inNamespace("default").createOrReplace(pvc);
        }
    }
}
