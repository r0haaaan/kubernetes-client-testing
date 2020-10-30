package io.fabric8;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class IBMCloudTesting {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            String ibmSystemNamespace = "ibm-system";

            client.pods().inNamespace(ibmSystemNamespace)
                    .list()
                    .getItems()
                    .forEach(p -> System.out.println(p.getMetadata().getName()));
        }
    }
}