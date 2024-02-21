package com.ft.routing.client;

import lombok.Getter;

@Getter
public class SendResult {
    
    private final boolean success;
    private final String errorMessage;

    protected SendResult(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
        if(!success) {
            Client.LOGGER.warn(errorMessage);
        }
    }

}
