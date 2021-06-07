package io.fabric8;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;

import java.util.logging.Logger;

public class DeploymentInformer {
    private static final Logger logger = Logger.getLogger(DeploymentInformer.class.getSimpleName());

    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            // Get Informer Factory
            SharedInformerFactory sharedInformerFactory = client.informers();

            // Create instance for Deployment Informer
            SharedIndexInformer<Deployment> DeploymentInformer = sharedInformerFactory.sharedIndexInformerFor(Deployment.class,
                    30 * 1000L);
            logger.info("Informer factory initialized.");

            // Add Event Handler for actions on all Deployment events received
            DeploymentInformer.addEventHandler(
                    new ResourceEventHandler<Deployment>() {
                        @Override
                        public void onAdd(Deployment namespace) {
                            logger.info("Deployment " + namespace.getMetadata().getName() + " got added");
                        }

                        @Override
                        public void onUpdate(Deployment oldDeployment, Deployment newDeployment) {
                            logger.info("Deployment " + oldDeployment.getMetadata().getName() + " got updated");
                        }

                        @Override
                        public void onDelete(Deployment namespace, boolean deletedFinalStateUnknown) {
                            logger.info("Deployment " + namespace.getMetadata().getName() + " got deleted");
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
