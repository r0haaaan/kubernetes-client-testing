package io.fabric8;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.io.ByteArrayInputStream;
import java.util.List;

public class DeploymentLoad {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            final String deploymentStr = "kind: Deployment\n" +
                    "metadata:\n" +
                    "  name: \"test\"\n" +
                    "  labels:\n" +
                    "    app: \"test\"\n" +
                    "spec:\n" +
                    "  selector:\n" +
                    "    matchLabels:\n" +
                    "      app: \"test\"\n" +
                    "  replicas: \"1\"\n" +
                    "  template:\n" +
                    "    metadata:\n" +
                    "      labels:\n" +
                    "        app: \"test\"\n" +
                    "    spec:\n" +
                    "      containers:\n" +
                    "        - name: \"test\"\n" +
                    "          image: \"busybox:latest\"\n" +
                    "          command: [\"/bin/sh\", \"-c\"]\n" +
                    "          args:\n" +
                    "            - |\n" +
                    "             sleep 60";
            List<HasMetadata> resourceList =  client.load(new ByteArrayInputStream(deploymentStr.getBytes())).get();
            Deployment deploy = (Deployment) resourceList.get(0);
        }
    }
}