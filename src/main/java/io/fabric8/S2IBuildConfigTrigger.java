package io.fabric8;

import io.fabric8.kubernetes.api.model.Event;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

import java.io.File;
import java.time.Instant;
import java.util.List;

public class S2IBuildConfigTrigger {
    public static void main(String[] args) {
        try (OpenShiftClient client = new DefaultOpenShiftClient()) {
            File baseDir = new File(System.getProperty("user.dir"));
            File dockerTar = new File(baseDir, "src/main/resources/docker-build.tar");

            client.buildConfigs().withName("random-generator-s2i")
                    .instantiateBinary()
                    .withTimeoutInMillis(5 * 60 * 1000)
                    .fromFile(dockerTar);

            List<Event> eventList = client.v1().events().list().getItems();
            eventList.sort((o1, o2) -> {
                    Instant i1 = Instant.parse(o1.getLastTimestamp());
                    Instant i2 = Instant.parse(o2.getLastTimestamp());
                    return (int) (i2.getEpochSecond() - i1.getEpochSecond());
            });
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                Event event = eventList.get(i);
                stringBuilder.append(event.getReason() + " " + event.getMetadata().getName() + " " + event.getMessage() + "\n");
            }

            System.out.println(stringBuilder.toString());
        }
    }
}
