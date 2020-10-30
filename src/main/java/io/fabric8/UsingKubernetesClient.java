package io.fabric8;

import io.fabric8.kubernetes.api.model.autoscaling.v2beta2.HorizontalPodAutoscaler;
import io.fabric8.kubernetes.api.model.autoscaling.v2beta2.HorizontalPodAutoscalerBuilder;
import io.fabric8.kubernetes.api.model.autoscaling.v2beta2.MetricSpecBuilder;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

public class UsingKubernetesClient {
    public static void main(String[] args) {


        try (KubernetesClient client = new DefaultKubernetesClient()) {

            Pod pod = new PodBuilder()
                    .withNewMetadata().withName("foo").endMetadata()
                    .withNewSpec()
                    .addNewContainer()
                    .withName("busybox")
                    .withImage("busybox")
                    .withCommand("sleep","36000")
                    .endContainer()
                    .endSpec()
                    .build();

            client.resource(pod).inNamespace("default").createOrReplace();
            HorizontalPodAutoscaler horizontalPodAutoscaler = new HorizontalPodAutoscalerBuilder()
                    .withNewMetadata().withName("the-hpa").withNamespace("default").endMetadata()
                    .withNewSpec()
                    .withNewScaleTargetRef()
                    .withApiVersion("apps/v1")
                    .withKind("Deployment")
                    .withName("the-deployment")
                    .endScaleTargetRef()
                    .withMinReplicas(1)
                    .withMaxReplicas(10)
                    .addToMetrics(new MetricSpecBuilder()
                            .withType("Resource")
                            .withNewResource()
                            .withName("cpu")
                            .withNewTarget()
                            .withType("Utilization")
                            .withAverageUtilization(50)
                            .endTarget()
                            .endResource()
                            .build())
                    .withNewBehavior()
                    .withNewScaleDown()
                    .addNewPolicy()
                    .withType("Pods")
                    .withValue(4)
                    .withPeriodSeconds(60)
                    .endPolicy()
                    .addNewPolicy()
                    .withType("Percent")
                    .withValue(10)
                    .withPeriodSeconds(60)
                    .endPolicy()
                    .endScaleDown()
                    .endBehavior()
                    .endSpec()
                    .build();


        } catch (KubernetesClientException ex) {
            // Handle exception
            ex.printStackTrace();
        }


    }
}
