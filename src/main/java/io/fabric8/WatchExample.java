package io.fabric8;

import io.fabric8.kubernetes.api.model.Config;
import io.fabric8.kubernetes.api.model.ConfigBuilder;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class WatchExample {

    private static final Logger logger = LoggerFactory.getLogger(WatchExample.class);

    public static void main(String[] args) throws InterruptedException {
        podWatch();
    }

    static void podWatch() throws InterruptedException {
        final KubernetesClient client = new DefaultKubernetesClient();

        //client.namespaces().create(new NamespaceBuilder().withNewMetadata().withName("ns1").endMetadata().build());
        Pod pod1 = new PodBuilder()
                .withNewMetadata()
                .withName("pod1").addToLabels("test", "watch")
                .endMetadata()
                .withNewSpec()
                .addToContainers(new ContainerBuilder().withName("nginx").withImage("nginx").build())
                .endSpec()
                .build();

        client.pods().inNamespace("ns1").create(pod1);

        Thread.sleep(5000);
        Watch watch = client.pods().inNamespace("ns1")
                .withLabels(new HashMap<String, String>() {{ put("test", "watch");}})
                .watch(new Watcher<Pod>() {
                    @Override
                    public void eventReceived(Action action, Pod resource) {
                        switch (action) {
                            case DELETED:
                                System.out.println("DELETEDdfsdsfsd");
                                break;
                            case ADDED:
                                System.out.println("ADDEDDaafdffw");
                                break;
                            case MODIFIED:
                                System.out.println("EDITEDaafdffw");
                                break;
//                            default:
//                                throw new AssertionFailedError(action.toString());
                        }
                    }

                    @Override
                    public void onClose(WatcherException e) {
                        System.out.println("CLOSSSSSINGGG");
                    }
                });

        Pod pod2 = new PodBuilder()
                .withNewMetadata()
                .withName("pod2")
                .endMetadata()
                .withNewSpec()
                .addToContainers(new ContainerBuilder().withName("nginx").withImage("nginx").build())
                .endSpec()
                .build();

        client.pods().inNamespace("ns1").create(pod2);

        pod2 = client.pods().inNamespace("ns1").withName("pod2").get();
        ObjectMeta objectMeta = pod2.getMetadata();
        objectMeta.setLabels(new HashMap<String, String>() {{ put("test", "watch"); }});
        pod2.setMetadata(objectMeta);

        System.out.println(pod2.getMetadata());
        Thread.sleep(5000);

        client.pods().inNamespace("ns1").withName("pod2")
                .patch(pod2);

        Thread.sleep(5000);


        System.out.println("Closing watch....");
        watch.close();
//    Pod pod3 = new PodBuilder().withNewMetadata().withName("pod3").addToLabels("test", "watch").endMetadata().build();

        System.out.println("Let's end this");
    }

}
