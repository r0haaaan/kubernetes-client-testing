package io.fabric8;

import io.fabric8.kubernetes.api.model.ListOptionsBuilder;
import io.fabric8.kubernetes.client.*;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RawCustomResourceWatch {
    private static final Logger logger = LoggerFactory.getLogger(RawCustomResourceWatch.class.getSimpleName());

    public static void main(String[] args) throws InterruptedException {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            String namespace = "myproject";
            CustomResourceDefinitionContext crdContext = new CustomResourceDefinitionContext.Builder()
                    .withGroup("demo.fabric8.io")
                    .withScope("Namespaced")
                    .withVersion("v1")
                    .withPlural("dummies")
                    .withName("dummies.demo.fabric8.io")
                    .build();

            final CountDownLatch closeLatch = new CountDownLatch(1);
            client.customResource(crdContext).watch(namespace, new ListOptionsBuilder().withTimeoutSeconds(30L).build(), new Watcher<String>() {
                @Override
                public void eventReceived(Action action, String resource) {
                    logger.info(action + " : " +  resource);
                }

                @Override
                public void onClose(WatcherException e) {
                    logger.info( "Watcher onClose");
                    closeLatch.countDown();
                    if (e != null) {
                        logger.info(e.getMessage());
                    }
                }
            });
            closeLatch.await(10, TimeUnit.MINUTES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
