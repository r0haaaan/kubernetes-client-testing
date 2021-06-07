package io.fabric8;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.base.OperationContext;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;

import java.util.logging.Logger;

public class NamespacedInformer {
    private static final Logger logger = Logger.getLogger(NamespacedInformer.class.getSimpleName());

    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            // Get Informer Factory
            SharedInformerFactory sharedInformerFactory = client.informers();

            // Create instance for Namespace Informer
            SharedIndexInformer<Namespace> NamespaceInformer = sharedInformerFactory.sharedIndexInformerFor(Namespace.class,
                    new OperationContext().withNamespace("tekton-pipelines"),
                    30 * 1000L);
            logger.info("Informer factory initialized.");

            // Add Event Handler for actions on all Namespace events received
            NamespaceInformer.addEventHandler(
                    new ResourceEventHandler<Namespace>() {
                        @Override
                        public void onAdd(Namespace namespace) {
                            logger.info("Namespace " + namespace.getMetadata().getName() + " got added");
                        }

                        @Override
                        public void onUpdate(Namespace oldNamespace, Namespace newNamespace) {
                            logger.info("Namespace " + oldNamespace.getMetadata().getName() + " got updated");
                        }

                        @Override
                        public void onDelete(Namespace namespace, boolean deletedFinalStateUnknown) {
                            logger.info("Namespace " + namespace.getMetadata().getName() + " got deleted");
                        }
                    }
            );

            logger.info("Starting all registered informers");
            sharedInformerFactory.startAllRegisteredInformers();

            // Wait for 1 minute
            Thread.sleep(60 * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
