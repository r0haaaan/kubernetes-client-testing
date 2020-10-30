package io.fabric8;

import io.fabric8.kubernetes.api.model.Event;
import io.fabric8.kubernetes.api.model.EventList;
import io.fabric8.kubernetes.api.model.ObjectReferenceBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class EventListTest {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {

            EventList eventList = client.v1().events().inNamespace("rokumar").withInvolvedObject(new ObjectReferenceBuilder()
                    .withName("wildfly-jar")
                    .withNamespace("rokumar")
                    .withKind("DeploymentConfig")
                    .withUid("6d71451a-f8df-11ea-a8ac-0e13a02d8ebd")
                    .build()).list();

            for (Event event : eventList.getItems()) {
                System.out.println(event.getMessage());
            }

            System.out.println(eventList.getItems().size() + " events found.");

        }
    }
}