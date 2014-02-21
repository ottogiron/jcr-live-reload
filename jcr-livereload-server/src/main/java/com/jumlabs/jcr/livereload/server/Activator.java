package com.jumlabs.jcr.livereload.server;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
	Logger log = LoggerFactory.getLogger(Activator.class);

	public void start(BundleContext context) throws Exception {
	  log.info("Initializing jetty server");

	}

	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}
