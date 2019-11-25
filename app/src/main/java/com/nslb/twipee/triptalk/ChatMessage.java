package com.nslb.twipee.triptalk;

class ChatMessage {
    public String message;
    public boolean bchecked;
    public ChatMessage(String message, boolean bchecked) {
        super();
        this.message = message;
        this.bchecked = bchecked;
    }

    public String getMessage() {
        return message;
    }
    public boolean getBChecked() {
        return bchecked;
    }
}
