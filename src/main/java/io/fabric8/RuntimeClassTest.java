package io.fabric8;

import io.fabric8.kubernetes.api.model.node.v1beta1.RuntimeClass;
import io.fabric8.kubernetes.api.model.node.v1beta1.RuntimeClassBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class RuntimeClassTest {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            RuntimeClass runtimeClass = new RuntimeClassBuilder()
                    .withNewMetadata().withName("myclass").endMetadata()
                    .withNewHandler("myconfiguration")
                    .build();

            client.runtimeClasses().create(runtimeClass);
        }
    }
}