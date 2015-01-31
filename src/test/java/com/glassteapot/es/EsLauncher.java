package com.glassteapot.es;

import static org.elasticsearch.common.settings.ImmutableSettings.Builder.EMPTY_SETTINGS;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.elasticsearch.Version;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

/**
 * Elastic search launcher for usage in tests. Also has a main method if you want to run ES from the IDE.
 */
public class EsLauncher {

    private Node node;

    /**
     * Initialize and start the elastic search node. Set system properties to configure or call inMemorySettings().
     */
    public void start() {
        try {
            // This step actually interprets the system properties
            NodeBuilder nodeBuilder = NodeBuilder.nodeBuilder().settings(EMPTY_SETTINGS).loadConfigSettings(false);
            node = nodeBuilder.build();
            // register a shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    node.close();
                }
            });
            node.start();
        } catch (Exception e) {
            throw new IllegalStateException("elastic search failed to launch " + e.getMessage(), e);
        }
    }

    /**
     * Sets a few system properties that Elastic Search uses to configure itself. You can override any property in the
     * yml file in a elastic search distribution by prepending es. and making it a system property
     */
    public void inMemorySettings() {
        System.setProperty("es.name", "devnode");
        System.setProperty("es.cluster.name", "localstream-dev");
        System.setProperty("es.index.store.type", "memory");
        System.setProperty("es.index.store.fs.memory.enabled", "memory");
        System.setProperty("es.index.gateway.type", "none");
        System.setProperty("es.gateway.type", "none");
        System.setProperty("es.discovery.zen.ping.multicast.enabled", "false");
        System.setProperty("es.path.data", "target/data");
        System.setProperty("es.path.logs", "target/logs");
        System.setProperty("es.foreground", "true");
    }

    /**
     * Shut down the elastic search node.
     * @throws IOException
     */
    public void close() throws IOException {
        node.close();
    }

    public static void main(String[] args) throws Exception {
        final CountDownLatch keepAliveLatch = new CountDownLatch(1);
        // keep this thread alive (non daemon thread) until we shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                keepAliveLatch.countDown();
            }
        });

        Thread keepAliveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EsLauncher l = new EsLauncher();
                    l.inMemorySettings();
                    l.start();
                    keepAliveLatch.await();
                    l.close();
                } catch (InterruptedException e) {
                    // bail out
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "elasticsearch[keepAlive/" + Version.CURRENT + "]");
        keepAliveThread.setDaemon(false);
        keepAliveThread.start();
    }
}