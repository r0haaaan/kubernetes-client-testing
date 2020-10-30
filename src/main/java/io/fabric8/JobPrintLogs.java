package io.fabric8;

import io.fabric8.kubernetes.api.model.batch.Job;
import io.fabric8.kubernetes.api.model.batch.JobBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.LogWatch;

import java.io.ByteArrayOutputStream;

public class JobPrintLogs {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Job job = new JobBuilder()
                    .withNewMetadata().withName("pi").endMetadata()
                    .withNewSpec()
                    .withNewTemplate()
                    .withNewSpec()
                    .addNewContainer()
                    .withName("pi")
                    .withImage("perl")
                    .withCommand("perl",  "-Mbignum=bpi", "-wle", "print bpi(2000)")
                    .endContainer()
                    .withRestartPolicy("Never")
                    .endSpec()
                    .endTemplate()
                    .endSpec()
                    .build();

            client.batch().jobs().inNamespace("default").createOrReplace(job);

            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            LogWatch logWatch = client.pods().inNamespace("default")
                    .withName("pi")
                    .watchLog(baos);

            Thread.sleep(5 * 1000);
            System.out.println(baos.toString());
            logWatch.close();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}