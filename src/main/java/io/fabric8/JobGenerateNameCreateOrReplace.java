package io.fabric8;

import io.fabric8.kubernetes.api.model.batch.Job;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class JobGenerateNameCreateOrReplace {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
//            Job job = client.batch().jobs()
//                    .load(JobGenerateNameCreateOrReplace.class.getResourceAsStream("/job-generatename.yml"))
//                    .get();

//            client.batch().jobs()
//                    .inNamespace("default").gen.createOrReplace(job);
        }
    }
}