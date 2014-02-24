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
import java.io.IOException;
import java.util.Dictionary;
import java.util.Set;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketApplication;
import org.glassfish.grizzly.websockets.WebSocketEngine;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true,metatype = true)
public class LiveReloadServer extends WebSocketApplication implements LiveReloadEventHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private HttpServer server;
    
    
    
    
    
    @Property(intValue = 9001,description = "Live reload server run port")
    static final String SERVER_PORT="server.port";
    
    @Reference
    private LivereloadEventService liveReloadEventService;


    
    @Activate
    protected void activate(ComponentContext ctx) {
        Dictionary properties = ctx.getProperties();
        int port = (Integer)properties.get(SERVER_PORT);
        
        configureHttpServer(port);
        
        logger.info("Registering  web socket server");
        WebSocketEngine.getEngine().register(this);
        logger.info("Web test socket server registered");
        liveReloadEventService.registerHandler(LiveReloadEventType.CHANGED, this);

    }
    
    
    private void configureHttpServer(int port){
             // TODO Auto-generated method stub
        logger.info("Starting live reload server on port "+ port);
        server = new HttpServer();
        NetworkListener nlistener = new NetworkListener("grizzly", "0.0.0.0", port);
        server.addListener(nlistener);
        CLStaticHttpHandler  clStaticHandler = new CLStaticHttpHandler(LiveReloadServer.class.getClassLoader(),"livereload/dist/");
        server.getServerConfiguration().addHttpHandler( clStaticHandler);
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
        int socketsSize = sockets.size();
        for(WebSocket socket:sockets){
            socket.send("Hubo 8una modificacion :), number of sockets:"+socketsSize);
        }

    }
    
    

    @Deactivate
    protected void deactivate(ComponentContext ctx) {
        logger.info("Unregistering web socket server");
        WebSocketEngine.getEngine().unregister(this);
        logger.info("Stoping  Live reload server");
        server.shutdown();
        logger.info("Live reload server");        
        liveReloadEventService.unregisterHandler(this);
        logger.info("Web socket server unregistered");
    }
}
