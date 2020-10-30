package io.fabric8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class ConfigBuilderTest {
    public static void main(String[] args) throws JsonProcessingException {
        Config config = Config.empty();

        Config configFromBuilder = new ConfigBuilder(Config.empty())
                .withMasterUrl("https://openshift.com")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(config));
        System.out.println(objectMapper.writeValueAsString(configFromBuilder));

    }
}