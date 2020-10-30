package io.fabric8;

import io.fabric8.kubernetes.api.model.apiextensions.v1beta1.CustomResourceDefinition;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class PrometheusAlertManagerCRDLoad {
    public static void main(String[] args) {
try (KubernetesClient client = new DefaultKubernetesClient()) {
    client.load(PrometheusAlertManagerCRDLoad.class.getResourceAsStream("/alertmanager.yml")).createOrReplace();
}
    }
}