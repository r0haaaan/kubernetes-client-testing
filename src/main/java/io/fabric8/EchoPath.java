package io.fabric8;

import java.io.IOException;

public class EchoPath {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder();
            pb.command("bash", "-c", "echo $PATH");
            pb.inheritIO();
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
