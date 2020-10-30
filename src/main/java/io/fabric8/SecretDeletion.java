package io.fabric8;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.api.model.SecretBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.Collections;

public class SecretDeletion {
    public static void main(String[] args) {
        String namespace = "rokumar";

        try (KubernetesClient client = new DefaultKubernetesClient()) {
            for (int i = 0; i < 20; i++) {
                Secret secret = getSecret();
                System.out.println("Creating secret");
                //client.secrets().inNamespace(namespace).create(secret);

                System.out.println("Deleting secret");
                /*
                 * I0429 12:09:18.237610    9996 request.go:897] Request Body: {"propagationPolicy":"Background"}
                 * I0429 12:09:18.237636    9996 round_trippers.go:383]
                 * DELETE https://api.rh-idev.openshift.com:443/api/v1/namespaces/rokumar/secrets/mysecret
                 *
                 */
                //client.secrets().inNamespace(namespace).withLabel("foo", "bar").delete();
                //client.apps().deployments().inNamespace(namespace).withName("nginx-deployment").delete();
                Pod pod = client.pods().inNamespace(namespace).withName("random-generator-1-g94ll").get();

                ObjectMeta objectMeta = pod.getMetadata();
                objectMeta.setLabels(Collections.singletonMap("foo", "bar"));
                pod.setMetadata(objectMeta);
                client.pods().inNamespace(namespace).withName("random-generator-1-g94ll")
                        .patch(pod);
            }
        }
    }

    private static Secret getSecret() {
        return new SecretBuilder()
                .withNewMetadata()
                .withName("mysecret")
                .addToLabels("foo", "bar")
                .endMetadata()
                .withType("Opaque")
                .addToData("username", "YWRtaW4=")
                .addToData("password", "MWYyZDFlMmU2N2Rm")
                .build();
    }
}