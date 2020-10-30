package io.fabric8;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.Date;
import java.util.Map;

public class PatchExample {
    public static void main(String[] args) {
        String namespace = "default";

        try (KubernetesClient client = new DefaultKubernetesClient()) {
            String podName = "testpod";
            Pod pod = client.pods().inNamespace(namespace).withName(podName).get();

            ObjectMeta objectMeta = pod.getMetadata();
            Map<String, String> labels = objectMeta.getLabels();
            labels.put("foo" + new Date().getTime(), "bar");
            pod.setMetadata(objectMeta);
            client.pods().inNamespace(namespace).withName(podName)
                    .patch(pod);

        }
    }
}
