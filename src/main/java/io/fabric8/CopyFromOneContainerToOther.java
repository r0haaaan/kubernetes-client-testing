package io.fabric8;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.io.File;
import java.nio.file.Path;

public class CopyFromOneContainerToOther {
    public static void main(String[] args) {
try (KubernetesClient client = new DefaultKubernetesClient()) {
    // File which was downloaded in Step 1
    File fileToUpload = new File("/home/rohaan/Downloads/docker-entrypoint.sh");
    client.pods().inNamespace("default")
            .withName("multi-container-pod")
            .inContainer("c2")
            .file("/tmp/docker-entrypoint.sh")  // <- Target location in other container
            .upload(fileToUpload.toPath());       // <- Local path where to copy downloaded file
}
    }
}