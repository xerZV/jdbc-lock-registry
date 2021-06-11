package com.simitchiyski.lockregistry.config.queue;

import java.util.UUID;

public class ClientId {
    public static ThreadLocal<String> clientId = new ThreadLocal<>();

    static {
        clientId.set(UUID.randomUUID().toString());
    }
}
