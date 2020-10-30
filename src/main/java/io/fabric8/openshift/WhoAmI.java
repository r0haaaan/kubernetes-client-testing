package io.fabric8.openshift;

import io.fabric8.openshift.api.model.User;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class WhoAmI {
    public static void main(String[] args) {
        try (OpenShiftClient client = new DefaultOpenShiftClient()) {
User user = client.currentUser();
client.console().consoleLinks().list().getItems().forEach(p -> System.out.println(p.getMetadata().getName()));
System.out.println(user.getMetadata().getName());

        }
    }
}