package io.fabric8;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.io.File;

public class UploadDirectoryTest {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            File fileToUpload = new File("/home/rohaan/work/k8-resource-yamls");
            client.pods().inNamespace("rokumar")
                    .withName("weather-web-application-1-ll8bd")
                    .dir("/tmp/k8-resource-yamls")
                    .upload(fileToUpload.toPath());
            System.out.println("Done");
        }
    }
}
