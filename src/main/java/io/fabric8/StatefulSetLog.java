package io.fabric8;

import io.fabric8.kubernetes.api.model.apps.StatefulSet;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.io.ByteArrayOutputStream;

public class StatefulSetLog {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            StatefulSet statefulSet = client.apps().statefulSets()
                    .load(StatefulSetLog.class.getResourceAsStream("/test-ss.yml"))
                    .get();

            client.apps().statefulSets().inNamespace("default").createOrReplace(statefulSet);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            client.apps().statefulSets().inNamespace("default")
                    .withName(statefulSet.getMetadata().getName())
                    .watchLog(byteArrayOutputStream);

            while(byteArrayOutputStream.toString().length() == 0) {
                Thread.sleep(100);
            }
            System.out.println(byteArrayOutputStream.toString());
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
