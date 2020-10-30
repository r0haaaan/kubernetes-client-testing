package io.fabric8.openshift;


import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

import java.io.ByteArrayOutputStream;

public class DeploymentConfigLog {
    public static void main(String[] args) {
        try (OpenShiftClient client = new DefaultOpenShiftClient()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            client.deploymentConfigs().inNamespace("rokumar")
                    .withName("weather-web-application")
                    .watchLog(byteArrayOutputStream);

            while (byteArrayOutputStream.toString().length() == 0) {
                Thread.sleep(10);
            }
            System.out.println(byteArrayOutputStream.toString());
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}