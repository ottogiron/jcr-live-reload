package com.jumlabs.jcr.livereload.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
	Logger logger = LoggerFactory.getLogger(Activator.class);
	private HttpServer server;
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Starting grizly server");
		server = HttpServer.createSimpleServer("/var/www", 8080);
		try {
		    server.start();
		    System.out.println("Press any key to stop the server...");
		    System.in.read();
		} catch (Exception e) {
		    logger.error(e.toString());
		}
		

	}

	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
	   logger.info("Stoping the web server");
	   server.shutdown();
	}

}
