package io.fabric8;

import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinition;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class V1CustomResourceDefinitionLoad {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            CustomResourceDefinition customResourceDefinition = client.apiextensions()
                    .v1()
                    .customResourceDefinitions()
                    .load(V1CustomResourceDefinitionLoad.class.getResourceAsStream("/v1-crontab.yml")).get();

            client.apiextensions().v1().customResourceDefinitions().createOrReplace(customResourceDefinition);
        }
    }
}