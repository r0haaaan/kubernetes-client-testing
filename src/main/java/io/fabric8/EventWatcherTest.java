package io.fabric8;

import io.fabric8.kubernetes.api.model.Event;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.*;

public class EventWatcherTest {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            client.v1().events().inNamespace("default").watch(new Watcher<>() {
                @Override
                public void eventReceived(Action action, Event resource) {
                    System.out.println(action.name() + " " + resource.getMessage());
                }

                @Override
                public void onClose(WatcherException e) {

                }

            });


            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
