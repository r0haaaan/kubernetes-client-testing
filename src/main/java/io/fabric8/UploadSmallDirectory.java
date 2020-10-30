package io.fabric8;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.io.File;
import java.nio.file.Path;

public class UploadSmallDirectory {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            File fileToUpload = new File("/home/rohaan/work/repos/test-dir");
            client.pods().inNamespace("rokumar")
                    .withName("weather-web-application-1-ll8bd")
                    .dir("/tmp/test-dir")
                    .upload(fileToUpload.toPath());
            System.out.println("Done");
        }
    }
}