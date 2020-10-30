package io.fabric8;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.openshift.api.model.Template;
import io.fabric8.openshift.api.model.TemplateBuilder;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class TemplateCreateOrReplace {
    public static void main(String[] args) {
        try (OpenShiftClient client = new DefaultOpenShiftClient()) {
            Pod pod = new PodBuilder()
                    .withNewMetadata().withName("redis-master").endMetadata()
                    .withNewSpec()
                    .addNewContainer()
                    .addNewEnv().withName("REDIS_PASSWORD").withValue("${REDIS_PASSWORD}").endEnv()
                    .withImage("dockerfile/redis")
                    .addNewPort()
                    .withContainerPort(6379)
                    .withProtocol("TCP")
                    .endPort()
                    .endContainer()
                    .endSpec()
                    .build();
            Template template =  new TemplateBuilder()
                    .withNewMetadata()
                    .withName("redis-template")
                    .addToAnnotations("description", "Description")
                    .addToAnnotations("iconClass", "icon-redis")
                    .addToAnnotations("tags", "database,nosql")
                    .endMetadata()
                    .addToObjects(pod)
                    .addNewParameter()
                    .withDescription("Password used for Redis authentication")
                    .withFrom("[A-Z0-9]{8}")
                    .withGenerate("expression")
                    .withName("REDIS_PASSWORD")
                    .endParameter()
                    .addToLabels("redis", "master")
                    .build();
            template.setApiVersion("v1");
            client.templates().inNamespace("myproject").createOrReplace(template);
        }
    }
}