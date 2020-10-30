package io.fabric8;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.io.ByteArrayOutputStream;

public class ExecUnameCommand {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            client.pods().inNamespace("rokumar")
                    .withName("weather-web-application-1-ll8bd")
                    .writingOutput(byteArrayOutputStream)
                    .exec("uname", "-a");

            Thread.sleep(5 * 1000);
            System.out.println(byteArrayOutputStream.toString());
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}