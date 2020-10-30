package io.fabric8;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class JKubeIntegrationTest {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Deployment deployment = client.apps().deployments().inNamespace("default").withName("spring-boot-zero-config-dockerfile").get();

            System.out.println(deployment.getMetadata().getName());

            Pod pod = client.pods().inNamespace("default").withLabel("app", "spring-boot-zero-config-dockerfile").list().getItems().get(0);

            System.out.println(client.apps().deployments().inNamespace("default").withName(deployment.getMetadata().getName()).getLog());
            String podLog = client.pods().inNamespace("default").withName(pod.getMetadata().getName()).getLog();
            System.out.println(podLog);
        }
    }
}
