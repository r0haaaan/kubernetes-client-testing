package io.fabric8.openshift;

import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

import java.io.ByteArrayOutputStream;

public class BuildLog {
    public static void main(String[] args) {
        try (OpenShiftClient client = new DefaultOpenShiftClient()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            client.builds().inNamespace("rokumar")
                    .withName("weather-web-application-s2i-4")
                    .withLogWaitTimeout(1)
                    .watchLog(baos);

            while(baos.toString().length() < 100) {
                Thread.sleep(10 * 1000);
            }
            System.out.println(baos.toString());
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}