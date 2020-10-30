package io.fabric8;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.io.File;

public class UploadTestSimple {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            File fileToUpload = new File("/home/rohaan/work/k8-resource-yamls/jobExample.yml");
            client.pods().inNamespace("default")
                    .withName("vertx-7dcb666759-l46qj")
                    .file("/tmp/jobExample.yml")
                    .upload(fileToUpload.toPath());
            System.out.println("Done");
        }
    }
}