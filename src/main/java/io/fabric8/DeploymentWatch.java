package io.fabric8;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;

public class DeploymentWatch {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            client.apps().deployments().watch(new Watcher<>() {
                @Override
                public void eventReceived(Action action, Deployment deployment) {
                    System.out.println(action.name() + " " + deployment.getMetadata().getName());
                }

                @Override
                public void onClose(KubernetesClientException e) {

                }
            });

            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
