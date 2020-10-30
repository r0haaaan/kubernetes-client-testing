package io.fabric8.openshift;

import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

import java.util.Map;

public class OpenShiftConfigIngressHack {
    public static void main(String[] args) {
    try (OpenShiftClient client = new DefaultOpenShiftClient()) {
Map<String, Object> openshiftIngressAsMap = client.customResource(new CustomResourceDefinitionContext.Builder()
        .withScope("Cluster")
        .withGroup("config.openshift.io")
        .withVersion("v1")
        .withPlural("ingresses")
        .build())
    .get("cluster");
    }
    }
}
