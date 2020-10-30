package io.fabric8;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;

import java.util.Map;

public class CountourHttpProxyExample {
    public static void main(String[] args) {
try (KubernetesClient client = new DefaultKubernetesClient()) {
    CustomResourceDefinitionContext httpProxyContext = new CustomResourceDefinitionContext.Builder()
            .withGroup("projectcontour.io") // <-  Group of Custom Resource
            .withVersion("v1")              // <-  Version of Custom Resource
            .withPlural("httpproxies")      // <-  Plural form as specified in CRD
            .withScope("Namespaced")        // <-  Whether Custom Resource is Cluster Scoped or Namespaced
            .build();

    // List all HTTPProxies
    Map<String, Object> httpProxyList = client.customResource(httpProxyContext).list("ns1");
    // Get a specific HTTPProxy
    Map<String, Object> myHttpProxy = client.customResource(httpProxyContext).get("ns1", "tls-example");
}
    }
}
