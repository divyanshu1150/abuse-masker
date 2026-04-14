package com.divyanshuagarwal.abusemasker.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ViewerCountService {

    private final AtomicInteger count = new AtomicInteger(0);

    @EventListener
    public void onConnect(SessionConnectedEvent event) {
        count.incrementAndGet();
    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        count.decrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}
