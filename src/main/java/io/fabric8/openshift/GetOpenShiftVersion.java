package io.fabric8.openshift;


import io.fabric8.kubernetes.client.VersionInfo;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class GetOpenShiftVersion {
    public static void main(String[] args) {
        try (OpenShiftClient client = new DefaultOpenShiftClient()) {
            VersionInfo versionInfo = client.getVersion();
            System.out.println("Major version: " + versionInfo.getMajor());
            System.out.println("Minor version: " + versionInfo.getMinor());
        }
    }
}