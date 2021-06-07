package io.fabric8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fabric8.kubernetes.api.model.apiextensions.v1beta1.CustomResourceDefinition;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.utils.Serialization;

public class CustomResourceDefinitionLoad {
	public static void main(String[] args) {
		try (KubernetesClient client = new DefaultKubernetesClient()) {
            CustomResourceDefinition crd = client.apiextensions().v1beta1().customResourceDefinitions().load(CustomResourceDefinitionEx.class.getResourceAsStream("/kafka.yml")).get();

			//CustomResourceDefinition sparkCRD = client.customResourceDefinitions().load(CustomResourceDefinition.class.getResourceAsStream("/another-crd.yml")).get();
			//System.out.println(Serialization.asYaml(sparkCRD));

			//client.apiextensions().v1beta1().customResourceDefinitions().createOrReplace(sparkCRD);
			client.apiextensions().v1beta1().customResourceDefinitions().createOrReplace(crd);
		}
	}
}
