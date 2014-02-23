/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jumlabs.jcr.livereload.server;

import com.jumlabs.jcr.livereload.services.LiveReloadEvent;
import com.jumlabs.jcr.livereload.services.LiveReloadEventHandler;
import com.jumlabs.jcr.livereload.services.LiveReloadEventType;
import com.jumlabs.jcr.livereload.services.LivereloadEventService;
import java.util.Set;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.glassfish.grizzly.websockets.WebSocketEngine;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true)
public class LiveReloadServer extends WebSocketApplication implements LiveReloadEventHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference
    private LivereloadEventService liveReloadEventService;

    @Activate
    protected void activate(ComponentContext ctx) {
        logger.info("Registering testtt web socket server");
        WebSocketEngine.getEngine().register(this);
        logger.info("Web test socket server registered");
        liveReloadEventService.registerHandler(LiveReloadEventType.CHANGED, this);

    }

    @Override
    public boolean isApplicationRequest(HttpRequestPacket request) {
        return request.getRequestURI().toString().endsWith("/liveReload");
    }

    @Override
    public void onMessage(WebSocket socket, String text) {
        // TODO Auto-generated method stub
        super.onMessage(socket, text);
        socket.send(text);
    }

    @Override
    public void onEvent(LiveReloadEvent event) {
        logger.info("An event has been  notified!!!!");
        Set<WebSocket> sockets = getWebSockets();
        for(WebSocket socket:sockets){
            socket.send("Hubo 8una modificacion :)");
        }

    }
    
    

    @Deactivate
    protected void deactivate(ComponentContext ctx) {
        logger.info("Unregistering web socket server");
        WebSocketEngine.getEngine().unregister(this);
        liveReloadEventService.unregisterHandler(this);
        logger.info("Web socket server unregistered");
    }
}
