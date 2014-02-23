/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jumlabs.jcr.livereload.services;



public interface LivereloadEventService {
      public void registerHandler(LiveReloadEventType eventType,LiveReloadEventHandler handler);
      public void unregisterHandler(LiveReloadEventHandler handler);
}
