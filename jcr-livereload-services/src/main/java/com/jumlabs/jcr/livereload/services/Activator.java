package com.jumlabs.jcr.livereload.services;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
        protected  final Logger logger = LoggerFactory.getLogger(this.getClass());
	public void start(BundleContext context) throws Exception {
		logger.info("Starting services bundle..................");

	}

	public void stop(BundleContext context) throws Exception {
		logger.info("Services bundle finished.................");

	}

}
