package io.fabric8.openshift;


import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

import java.util.concurrent.TimeUnit;

public class DeploymentConfigWaitUntilReady {
    public static void main(String[] args) {
        try (OpenShiftClient client = new DefaultOpenShiftClient()) {
            client.load(DeploymentConfigWaitUntilReady.class.getResourceAsStream("/test-dc-wait.yml")).waitUntilReady(2, TimeUnit.MINUTES);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            interruptedException.printStackTrace();
        }
    }
}