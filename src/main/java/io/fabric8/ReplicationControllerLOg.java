package io.fabric8;

import io.fabric8.kubernetes.api.model.ReplicationController;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.io.ByteArrayOutputStream;

public class ReplicationControllerLOg {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            ReplicationController rc = client.replicationControllers()
                    .load(ReplicationControllerLOg.class.getResourceAsStream("/test-rc.yml"))
                    .get();
            client.replicationControllers().inNamespace("default").createOrReplace(rc);

            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            client.replicationControllers().inNamespace("default")
                    .withName(rc.getMetadata().getName())
                    .withLogWaitTimeout(30)
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
