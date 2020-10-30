package io.fabric8;

import io.fabric8.kubernetes.api.model.ListOptionsBuilder;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class MultipleKubeConfig {
    public static Logger logger = LoggerFactory.getLogger(MultipleKubeConfig.class);
    public static void main(String args[]) {
        //System.setProperty(Config.KUBERNETES_KUBECONFIG_FILE, "/home/rohaan/.kube/config" + File.pathSeparator + "/home/rohaan/work/config");
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            //PodList podList = client.pods().inNamespace("kube-system").list(3, null);

//            client.pods().inNamespace("rokumar")
//                    .list(new ListOptionsBuilder().withLimit(2L).build())
//                    .getItems().forEach( ns -> logger.info(ns.getMetadata().getName()));

        }
    }
}
