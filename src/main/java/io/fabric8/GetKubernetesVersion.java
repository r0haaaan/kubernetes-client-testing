package io.fabric8;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.VersionInfo;

public class GetKubernetesVersion {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            VersionInfo versionInfo = client.getVersion();
            System.out.println("Major version: " + versionInfo.getMajor());
            System.out.println("Minor version: " + versionInfo.getMinor());
        }
    }
}