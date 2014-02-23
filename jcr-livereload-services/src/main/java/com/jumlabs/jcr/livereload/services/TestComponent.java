/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jumlabs.jcr.livereload.services;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestComponent implements LiveReloadEventHandler{

    
      protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference
    private LivereloadEventService liveReloadEventService;

    @Activate
    protected void activate(ComponentContext ctx) {
        logger.info("Registering testtt web socket server");
        //WebSocketEngine.getEngine().register(this);
        logger.info("Web test socket server registered");
        liveReloadEventService.registerHandler(LiveReloadEventType.CHANGED, this);
  
    }

//    @Override
//    public boolean isApplicationRequest(HttpRequestPacket request) {
//        return request.getRequestURI().toString().endsWith("/liveReload");
//    }
//
//    @Override
//    public void onMessage(WebSocket socket, String text) {
//        // TODO Auto-generated method stub
//        super.onMessage(socket, text);
//        socket.send(text);
//    }

    @Override
    public void onEvent(LiveReloadEvent event) {
        logger.info("An event has been  notified!!!!");

    }

    @Deactivate
    protected void deactivate(ComponentContext ctx) {
        logger.info("Unregistering web socket server");
       // WebSocketEngine.getEngine().unregister(this);
        liveReloadEventService.unregisterHandler(this);
        logger.info("Web socket server unregistered");
    }
}
