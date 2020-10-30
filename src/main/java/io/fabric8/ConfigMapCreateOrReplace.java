package io.fabric8;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.singletonMap;

public class ConfigMapCreateOrReplace {
    public static final String RESOURCE_NAME = "my-resource";
    public static final String NAMESPACE = "rokumar";

    public static ConfigMap getOriginalCM()  {
        return new ConfigMapBuilder()
                .withNewMetadata()
                .withName(RESOURCE_NAME)
                .withLabels(singletonMap("state", "new"))
                .endMetadata()
                .build();
    }

    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            ConfigMap newResource = getOriginalCM();
            //client.configMaps().inNamespace(NAMESPACE).withName(RESOURCE_NAME).create(newResource);
            newResource.setData(Collections.singletonMap("no", "yes"));
            newResource = client.configMaps().inNamespace(NAMESPACE).createOrReplace(newResource);
            System.out.println(newResource.getData());

            // oc get -n openshift-kube-apiserver configmap/config -o yaml -o jsonpath="{.data['config\.yaml']}" \
            //  | jq -r '.apiServerArguments."cloud-provider"[0]'
            client.configMaps()
                    .inNamespace("openshift-kube-apiserver")
                    .withName("config")
                    .get()
                    .getData()
                    .get("config.yaml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
