package io.fabric8;

import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.openshift.api.model.Template;
import io.fabric8.openshift.api.model.TemplateBuilder;

public class TemplateBuilderExample {
    public static void main(String[] args) {
        Template template = new TemplateBuilder()
                .withNewMetadata().withName("redis-template").endMetadata()
                .withObjects(new PodBuilder()
                        .withNewMetadata().withName("redis-master").endMetadata()
                        .withNewSpec()
                        .addNewContainer()
                        .addNewEnv()
                        .withName("REDIS_PASSWORD")
                        .withValue("${REDIS_PASSWORD}")
                        .endEnv()
                        .withImage("dockerfile/redis")
                        .withName("master")
                        .addNewPort()
                        .withProtocol("TCP")
                        .withContainerPort(6379)
                        .endPort()
                        .endContainer()
                        .endSpec()
                .build())
                .addNewParameter()
                .withDescription("Password used for Redis authentication")
                .withFrom("[A-Z0-9]{8}")
                .withGenerate("expression")
                .withName("REDIS_PASSWORD")
                .endParameter()
                .build();

    }
}
