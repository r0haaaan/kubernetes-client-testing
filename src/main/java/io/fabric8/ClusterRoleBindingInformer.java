package io.fabric8;

import io.fabric8.kubernetes.api.model.rbac.ClusterRoleBinding;
import io.fabric8.kubernetes.api.model.rbac.ClusterRoleBindingList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;

import java.util.logging.Logger;

public class ClusterRoleBindingInformer {
    private static final Logger logger = Logger.getLogger(ClusterRoleBindingInformer.class.getSimpleName());

    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            // Get Informer Factory
            SharedInformerFactory sharedInformerFactory = client.informers();

            // Create instance for ClusterRoleBinding Informer
            SharedIndexInformer<ClusterRoleBinding> ClusterRoleBindingInformer = sharedInformerFactory.sharedIndexInformerFor(ClusterRoleBinding.class,
                    30 * 1000L);
            logger.info("Informer factory initialized.");

            // Add Event Handler for actions on all ClusterRoleBinding events received
            ClusterRoleBindingInformer.addEventHandler(
                    new ResourceEventHandler<ClusterRoleBinding>() {
                        @Override
                        public void onAdd(ClusterRoleBinding namespace) {
                            logger.info("ClusterRoleBinding " + namespace.getMetadata().getName() + " got added");
                        }

                        @Override
                        public void onUpdate(ClusterRoleBinding oldClusterRoleBinding, ClusterRoleBinding newClusterRoleBinding) {
                            logger.info("ClusterRoleBinding " + oldClusterRoleBinding.getMetadata().getName() + " got updated");
                        }

                        @Override
                        public void onDelete(ClusterRoleBinding namespace, boolean deletedFinalStateUnknown) {
                            logger.info("ClusterRoleBinding " + namespace.getMetadata().getName() + " got deleted");
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
