package io.fabric8;

import io.fabric8.kubernetes.api.model.apps.ReplicaSet;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.io.ByteArrayOutputStream;

public class ReplicaSetLog {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            ReplicaSet rs = client.apps().replicaSets()
                    .load(ReplicaSetLog.class.getResourceAsStream("/test-rs.yml"))
                    .get();

            client.apps().replicaSets().inNamespace("default").createOrReplace(rs);

            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            client.apps().replicaSets()
                    .inNamespace("default")
                    .withName(rs.getMetadata().getName())
                    .withLogWaitTimeout(20)
                    .watchLog(baos);
            while (baos.toString().length() == 0) {
                Thread.sleep(10);
            }
            System.out.println(baos.toString());
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}