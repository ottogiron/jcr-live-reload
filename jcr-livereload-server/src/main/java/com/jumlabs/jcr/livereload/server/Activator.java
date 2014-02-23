package com.jumlabs.jcr.livereload.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class Activator implements BundleActivator {

    Logger logger = LoggerFactory.getLogger(Activator.class);
    private HttpServer server;

    @Override
    public void start(BundleContext context) throws Exception {
        // TODO Auto-generated method stub
        logger.info("Starting grizly server");
        server = HttpServer.createSimpleServer("/home/ogiron/www", 8080);
        StaticHttpHandler handler = new StaticHttpHandler();
        server.getServerConfiguration().addHttpHandler(handler);
        final WebSocketAddOn addon = new WebSocketAddOn();
        for (NetworkListener listener : server.getListeners()) {
            listener.registerAddOn(addon);
        }

        try {

            server.start();
            logger.info("Web server started");
        } catch (IOException e) {
            logger.error("An IO exception" + e);
        }

    }

    @Override
    public void stop(BundleContext context) throws Exception {
        logger.info("Stoping the web server");
        server.shutdown();
    }

}
