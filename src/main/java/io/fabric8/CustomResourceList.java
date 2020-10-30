package io.fabric8;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class CustomResourceList {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            CustomResourceDefinitionContext crdContext = new CustomResourceDefinitionContext.Builder()
                    .withGroup("demo.fabric8.io")
                    .withPlural("dummies")
                    .withScope("Namespaced")
                    .withVersion("v1")
                    .withName("dummies.demo.fabric8.io")
                    .build();

            Map<String, Object> list = client.customResource(crdContext).list("default");
            JSONObject jsonObject = new JSONObject(list);
            JSONArray itemList = jsonObject.getJSONArray("items");
            System.out.println("list : " + itemList.length());

            list = client.customResource(crdContext).list("default", Collections.singletonMap("sabre.cicd/parent-project", "ngp-devx-bangalore"));

            jsonObject = new JSONObject(list);
            itemList = jsonObject.getJSONArray("items");
            System.out.println("list with labels: " + itemList.length());

        }
    }
}
