package io.fabric8;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.*;

public class DeploymentWatch {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            client.apps().deployments().watch(new Watcher<>() {
                @Override
                public void eventReceived(Action action, Deployment deployment) {
                    System.out.println(action.name() + " " + deployment.getMetadata().getName());
                }

                @Override
                public void onClose(WatcherException e) {

                }
            });

            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
