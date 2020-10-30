package io.fabric8;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.List;

public class DeploymentLog {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            String namespace = "default";
            List<HasMetadata> items = client.load(DeploymentLog.class.getResourceAsStream("/hello-k8s.yml")).get();
            client.resourceList(items).inNamespace(namespace).createOrReplace();

            client.apps().deployments()
                    .inNamespace(namespace)
                    .withName("hello-kubernetes")
                    .watchLog(System.out);

            Thread.sleep(5 * 1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}