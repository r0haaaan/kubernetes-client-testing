package io.fabric8;

import io.fabric8.crd.CronTab;
import io.fabric8.crd.CronTabList;
import io.fabric8.crd.CronTabSpec;
import io.fabric8.crd.DoneableCronTab;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import io.fabric8.kubernetes.client.dsl.internal.CustomResourceOperationContext;

import java.util.Arrays;
import java.util.Collections;

public class CustomResourceCreateOrReplace {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            CustomResourceDefinitionContext context = new CustomResourceDefinitionContext.Builder()
                    .withGroup("stable.example.com")
                    .withVersion("v1")
                    .withPlural("crontabs")
                    .withScope("Namespaced")
                    .withName("crontabs.stable.example.com")
                    .build();

            MixedOperation<CronTab, CronTabList, DoneableCronTab, Resource<CronTab, DoneableCronTab>> cronTabClient =
                    client.customResources(context, CronTab.class, CronTabList.class, DoneableCronTab.class);

            CronTab ct = getCronTab();

            cronTabClient.inNamespace("default").createOrReplace(ct);
        }
    }

    private static CronTab getCronTab() {
        CronTab cronTab = new CronTab();
        cronTab.setMetadata(new ObjectMetaBuilder()
                .withFinalizers(Collections.singletonList("crontabs.stable.example.com"))
                .withName("my-third-crontab")
                .build());
        CronTabSpec cronTabSpec = new CronTabSpec();
        cronTabSpec.setCronSpec("* * * * */6");
        cronTabSpec.setImage("my-third-cron-image");
        cronTabSpec.setReplicas(5);
        cronTab.setSpec(cronTabSpec);

        return cronTab;
    }
}
