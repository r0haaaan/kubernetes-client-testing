package io.fabric8;

import io.fabric8.kubernetes.api.model.ReplicationController;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class DeletionCheckTimestamp {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            String namespace = "rokumar";
            ReplicationController rc = client.replicationControllers()
                    .inNamespace(namespace)
                    .withName("nginx")
                    .get();

            System.out.println("deletion timestamp: " + rc.getMetadata().getDeletionTimestamp());

            client.replicationControllers().inNamespace(namespace).withName("nginx").delete();
            rc = client.replicationControllers()
                    .inNamespace(namespace)
                    .withName("nginx")
                    .get();

            System.out.println(rc);
            System.out.println("deletion timestamp: " + rc.getMetadata().getDeletionTimestamp());
        }
    }
}
