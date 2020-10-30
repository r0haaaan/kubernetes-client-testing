package io.fabric8;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class CertificatePod {
    public static void main(String[] args) {

        Config config = new ConfigBuilder()
                .withNamespace("ns1")
                .withCaCertFile("/path/to/file")
                .build();
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            client.pods().inNamespace("ns").list();
        }
    }
}
