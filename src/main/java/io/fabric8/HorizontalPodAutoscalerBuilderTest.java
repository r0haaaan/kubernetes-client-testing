package io.fabric8;

import io.fabric8.kubernetes.api.model.autoscaling.v2beta2.HorizontalPodAutoscaler;
import io.fabric8.kubernetes.api.model.autoscaling.v2beta2.HorizontalPodAutoscalerBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class HorizontalPodAutoscalerBuilderTest {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            String[] metricInput = new String[] {"input1", "input2"};

            HorizontalPodAutoscalerBuilder horizontalPodAutoscalerBuilder = new HorizontalPodAutoscalerBuilder()
                    .withNewMetadata().withName("the-hpa").withNamespace("default").endMetadata()
                    .withNewSpec()
                    .withMetrics()
                    .withNewScaleTargetRef()
                    .withApiVersion("apps/v1")
                    .withKind("Deployment")
                    .withName("the-deployment")
                    .endScaleTargetRef()
                    .withMinReplicas(1)
                    .withMaxReplicas(10)
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
                    .endSpec();

            for (int i = 0; i < metricInput.length; i++) {
                horizontalPodAutoscalerBuilder
                        .editOrNewSpec()
                        .addNewMetric()
                        .withType(metricInput[i])
                        // ...
                        .endMetric()
                        .endSpec();
            }
            HorizontalPodAutoscaler hpa =  horizontalPodAutoscalerBuilder.build();
        }
    }
}