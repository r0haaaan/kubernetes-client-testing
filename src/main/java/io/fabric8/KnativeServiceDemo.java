package io.fabric8;

import io.fabric8.knative.client.DefaultKnativeClient;
import io.fabric8.knative.client.KnativeClient;
import io.fabric8.knative.serving.v1.Service;
import io.fabric8.knative.serving.v1.ServiceBuilder;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.List;

public class KnativeServiceDemo {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            List<HasMetadata> genericList = client.load(KnativeServiceDemo.class.getResourceAsStream("/knative-svc.yml")).get();
            HasMetadata genericListItem = genericList.get(0);
            if (genericListItem instanceof Service) {
                KnativeClient knativeClient = client.adapt(KnativeClient.class);
                Service knativeSvcFromGeneric = (Service) genericListItem;
                knativeClient.services().inNamespace("default").createOrReplace(knativeSvcFromGeneric);
            }
        }
    }

    private static Service getKnativeServiceObj() {
        return new ServiceBuilder()
                .withNewMetadata().withName("helloworld-go").endMetadata()
                .withNewSpec()
                .withNewTemplate()
                .withNewSpec()
                .addToContainers(new ContainerBuilder()
                        .withImage("gcr.io/knative-samples/helloworld-go")
                        .addNewEnv().withName("TARGET").withValue("Go Sample V1").endEnv()
                        .build())
                .endSpec()
                .endTemplate()
                .endSpec()
                .build();

    }
}
