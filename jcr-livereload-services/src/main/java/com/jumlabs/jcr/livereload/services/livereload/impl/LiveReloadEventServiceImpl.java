package com.jumlabs.jcr.livereload.services.livereload.impl;

import com.jumlabs.jcr.livereload.services.LiveReloadEvent;
import com.jumlabs.jcr.livereload.services.LiveReloadEventHandler;
import com.jumlabs.jcr.livereload.services.LiveReloadEventType;
import com.jumlabs.jcr.livereload.services.LivereloadEventService;
import java.util.ArrayList;
import org.osgi.service.event.EventHandler;
import java.util.Dictionary;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingConstants;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(metatype = true, immediate = true,
        description = "Live reload event listener configuration"
)
@Service(value = {EventHandler.class, LivereloadEventService.class})
@Property(name = EventConstants.EVENT_TOPIC, propertyPrivate = true, value = {
    SlingConstants.TOPIC_RESOURCE_ADDED,
    SlingConstants.TOPIC_RESOURCE_CHANGED,
    SlingConstants.TOPIC_RESOURCE_REMOVED
})

public class LiveReloadEventServiceImpl implements EventHandler, LivereloadEventService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private EnumMap<LiveReloadEventType, List<LiveReloadEventHandler>> eventHandlersMap;
    private Map<LiveReloadEventHandler, List<LiveReloadEventType>> handlersEvents;

    @Property(label = "Listen paths filter",
            description = "This property takes LDAP filter syntax",
            value = "(|(/etc/*)(/app/*))"
    )
    static final String PATH_FILTERS = "path.filters";

    protected void activate(ComponentContext ctx) {
        Dictionary properties = ctx.getProperties();
        properties.put(EventConstants.EVENT_FILTER, properties.get(PATH_FILTERS));
        //Initialize the handler map
        eventHandlersMap = new EnumMap<LiveReloadEventType, List<LiveReloadEventHandler>>(LiveReloadEventType.class);
        handlersEvents = new HashMap<LiveReloadEventHandler, List<LiveReloadEventType>>();

    }

    @Override
    public void handleEvent(Event event) {
        // get the resource event information
        final String propPath = (String) event.getProperty(SlingConstants.PROPERTY_PATH);
        final String propResType = (String) event.getProperty(SlingConstants.PROPERTY_RESOURCE_TYPE);
        final String propTopic = (String) event.getTopic();
        String[] propTypes = {"nt:file","cq:Dialog","cq:EditConfig"};
        if (propResType != null && containsToIgnoreCase(propTypes,propResType)) {
           // logger.info("the event type is "+propResType);
            LiveReloadEventType eventType = null;
            if(propTopic.equals(SlingConstants.TOPIC_RESOURCE_ADDED)){
                eventType = LiveReloadEventType.ADDED;
            }
            else if(propTopic.equals(SlingConstants.TOPIC_RESOURCE_CHANGED)){
                eventType = LiveReloadEventType.CHANGED;
            }
            else if(propTopic.equals(SlingConstants.TOPIC_RESOURCE_REMOVED)){
                eventType = LiveReloadEventType.REMOVED;
            }
            notifyHandlers(eventType, event);

        }
    }

    private boolean  containsToIgnoreCase(String[] arr,String value){
        for(String item:arr){
            if(item.compareToIgnoreCase(value) == 0){
                return true;
            }
        }
        return  false;
    }

    private void notifyHandlers(LiveReloadEventType eventType, Event event) {
        if(eventType != null){
            if(eventHandlersMap.containsKey(eventType)){
                for(LiveReloadEventHandler handler:eventHandlersMap.get(eventType)){
                    handler.onEvent(new LiveReloadEvent(event, eventType));
                }
            }
            
        }
    }

    @Override
    public void registerHandler(LiveReloadEventType eventType, LiveReloadEventHandler handler) {
       List<LiveReloadEventType> handlerRegisteredEventTypes ;
        if (!handlersEvents.containsKey(handler)) {            
            handlerRegisteredEventTypes = new ArrayList<LiveReloadEventType>();
            handlersEvents.put(handler,handlerRegisteredEventTypes );
        } else {
              handlerRegisteredEventTypes = handlersEvents.get(handler);
        }
        

        handlerRegisteredEventTypes.add(eventType);

        List<LiveReloadEventHandler> handlers;
        if (!eventHandlersMap.containsKey(eventType)) {
            handlers = new ArrayList<LiveReloadEventHandler>();
            eventHandlersMap.put(eventType,handlers );
        }
        else{
            handlers = eventHandlersMap.get(eventType);
        }

        handlers.add(handler);

    }

    @Override
    public void unregisterHandler(LiveReloadEventHandler handler) {
        if (handlersEvents.containsKey(handler)) {
            List<LiveReloadEventType> eventTypes = handlersEvents.get(handler);
            for (LiveReloadEventType eventType : eventTypes) {
                List<LiveReloadEventHandler> handlers = eventHandlersMap.get(eventType);
                handlers.remove(handler);
            }
        }
    }

}
