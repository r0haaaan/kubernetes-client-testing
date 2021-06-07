package io.fabric8;

import io.fabric8.kubernetes.api.model.networking.v1beta1.Ingress;
import io.fabric8.kubernetes.client.*;

public class IngressWatcher {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            client.network().ingresses().inNamespace("default").watch(new Watcher<Ingress>() {
                @Override
                public void eventReceived(Action action, Ingress ingress) {
                    System.out.println(action.name() + " " + ingress.getMetadata().getName());
                }

                @Override
                public void onClose(WatcherException e) {

                }

            });

            Thread.sleep(10 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
