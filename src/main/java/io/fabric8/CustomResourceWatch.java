package io.fabric8;

import io.fabric8.kubernetes.api.model.ListOptionsBuilder;
import io.fabric8.kubernetes.client.*;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomResourceWatch {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            CustomResourceDefinitionContext crdContext = new CustomResourceDefinitionContext.Builder()
                    .withGroup("stable.example.com")
                    .withPlural("crontabs")
                    .withScope("Namespaced")
                    .withVersion("v1")
                    .build();

            Map<String, String> labels = new HashMap<>();
            labels.put("foo", "bar");
            labels.put("scope", "test");

            Watch watch = client.customResource(crdContext)
                    .watch("myproject", null, labels, new ListOptionsBuilder().build(),
                            new Watcher<String>() {
                                @Override
                                public void eventReceived(Action action, String resource) {
                                    System.out.println("Event received: " + action.name() + " " + resource);
                                }

                                @Override
                                public void onClose(WatcherException e) {
                                }

                            });

            Thread.sleep(60 * 1000);
            watch.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
