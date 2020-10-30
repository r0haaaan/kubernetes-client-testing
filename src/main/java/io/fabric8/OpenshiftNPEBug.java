package io.fabric8;

import io.fabric8.kubernetes.api.model.ListOptions;
import io.fabric8.kubernetes.api.model.ListOptionsBuilder;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.StatusBuilder;
import io.fabric8.kubernetes.api.model.authentication.UserInfo;
import io.fabric8.kubernetes.api.model.authentication.UserInfoBuilder;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class OpenshiftNPEBug {
    public static void main(String[] args) {
        try (DefaultOpenShiftClient client = new DefaultOpenShiftClient()) {
            client.apps().deployments().inNamespace("rokumar").withName("nginx-deployment")
                    .scale(1);

            ListOptions listOptions = new ListOptionsBuilder()
                    .withAllowWatchBookmarks(true)
                    .withContinue("erfrgtrgrtgrw")
                    .withLimit(50l)
                    .build();

            UserInfo userInfo = new UserInfoBuilder()
                    .withNewUid("sferfreferfe")
                    .withUsername("test-user")
                    .withGroups("group1", "group2")
                    .build();



        }
    }
}
