(function(window){
    
    var JCRLiveReload = JCRLiveReload || {};
    window.JCRLiveReload = JCRLiveReload;
    var socket;
    
        
    JCRLiveReload.initialize = function(port){ 
       socket = new WebSocket('ws://'+document.location.hostname+':'+port+'/liveReload'); 
         socket.onopen = onConnect;         
        socket.onclose = onClose;        
        socket.onerror = onConnectError;
        socket.onmessage = onMessage;
    };
    
    function onConnect(){
        console.log("Connected");
    }
    
    function onConnectError(){
        console.log("Connection error");
    }
    
    function onMessage(message){
        location.reload();
    }
    
    function onClose(){
       console.log('Connection closed'); 
    }
    
    
        
})(window);
