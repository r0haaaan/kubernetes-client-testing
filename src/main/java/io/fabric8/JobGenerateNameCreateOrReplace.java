package io.fabric8;

import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class JobGenerateNameCreateOrReplace {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
//            Job job = client.batch.v1.).jobs()
//                    .load(JobGenerateNameCreateOrReplace.class.getResourceAsStream("/job-generatename.yml"))
//                    .get();

//            client.batch.v1.).jobs()
//                    .inNamespace("default").gen.createOrReplace(job);
        }
    }
}