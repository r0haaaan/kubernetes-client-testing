package io.fabric8.openshift;

import io.fabric8.openshift.api.model.Route;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class RouteCreate {
    public static void main(String[] args) {
        try (OpenShiftClient client = new DefaultOpenShiftClient()) {
            Route route = client.routes().load(RouteCreate.class.getResourceAsStream("/test-route.yml")).get();
            client.routes().inNamespace("rokumar").create(route);
        }
    }
}