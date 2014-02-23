package com.jumlabs.jcr.livereload.server.websockets.test;


import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
//import org.glassfish.grizzly.http.HttpRequestPacket;
//import org.glassfish.grizzly.websockets.WebSocket;
//import org.glassfish.grizzly.websockets.WebSocketApplication;

@Component
@Service(EchoTestService.class)
public class EchoApplication implements EchoTestService{

//	@Override
//	public boolean isApplicationRequest(HttpRequestPacket request) {
//		if(request.getRequestURI().toString().endsWith("/liveReload")){
//			return true;
//		}
//		else{
//			return false;
//		}
//	}
//	
//	
//	@Override
//	public void onMessage(WebSocket socket, String text) {
//		// TODO Auto-generated method stub
//		super.onMessage(socket, text);
//		socket.send(text);
//	}

    @Override
    public void doEcho() {
        System.out.println("Que onda sr burns");
    }

}
