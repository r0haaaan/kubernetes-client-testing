package io.fabric8;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.ExecListener;
import okhttp3.Response;

import java.net.URLEncoder;

public class ExecExample {
    public static void main(String[] args) {
        String namespace = "rokumar";
        String name = "random-generator-1-g94ll";
        String containerName = "spring-boot";
        String[] commands= new String[3];
        commands[0]= "sh";
        commands[1]= "-c";
        commands[2]= "sleep 1 && echo hello ; echo world";

        try (KubernetesClient client = new DefaultKubernetesClient()) {
            client.pods().inNamespace(namespace)
                    .withName(name)
                    .inContainer(containerName)
                    .readingInput(System.in)
                    .writingOutput(System.out)
                    .writingError(System.err)
                    .withTTY()
                    .usingListener(new SimpleListener()).exec(commands);

            Thread.sleep(10 * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private static class SimpleListener implements ExecListener {

        @Override
        public void onOpen(Response response) {
            System.out.println("The shell will remain open for 10 seconds.");
        }

        @Override
        public void onFailure(Throwable t, Response response) {
            System.err.println("shell barfed");
        }

        @Override
        public void onClose(int code, String reason) {
            System.out.println("The shell will now close.");
        }
    }

}
