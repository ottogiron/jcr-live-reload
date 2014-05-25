/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jumlabs.jcr.livereload.services;

import org.osgi.service.event.Event;

/**
 *
 * @author otto
 */
public class LiveReloadEvent {
    private final Event ogsEvent;
    private final LiveReloadEventType liveReloadEventType;
    public LiveReloadEvent(Event osgiEvent,LiveReloadEventType eventType){
        this.liveReloadEventType = eventType;
        this.ogsEvent = osgiEvent;
    }
    
    
    public LiveReloadEventType  getEventType(){
        return this.liveReloadEventType;
    }
    
    public Event getOSGIEvent( ){
        return this.ogsEvent;
    }
}
