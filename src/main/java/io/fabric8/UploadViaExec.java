package io.fabric8;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.ExecWatch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UploadViaExec {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            File fileToUpload = new File("/home/rohaan/work/repos/jkube/quickstarts/maven/vertx/target/docker/maven/vertx/1.0.0/tmp/changed-files.tar");
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            ByteArrayOutputStream error = new ByteArrayOutputStream(1024);
            ExecWatch execWatch = client.pods().inNamespace("default")
                    .withName("vertx-7dcb666759-l46qj")
                    .readingInput(new FileInputStream(fileToUpload))
                    .writingOutput(baos)
                    .writingError(error)
                    .exec("tar", "-xf", "-", "-C", "/");
            System.out.println("Done");
            Thread.sleep(10 * 1000);
            execWatch.close();
            System.out.println(baos.toString());
            System.out.println(error.toString());
        } catch (FileNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}