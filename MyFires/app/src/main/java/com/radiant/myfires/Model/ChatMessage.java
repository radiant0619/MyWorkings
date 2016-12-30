package com.radiant.myfires.Model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sakthivel on 26/12/2016.
 */

public class ChatMessage {
    private String msgUser;
    private String msgDesc;
    private String sender;
    private Date msgTime;

    public ChatMessage(String msgUser, String msgDesc) {
        this.msgUser = msgUser;
        this.msgDesc = msgDesc;
        this.msgTime = Calendar.getInstance().getTime();
    }

    public ChatMessage() {
    }

    public String getMsgUser() {
        return msgUser;
    }

    public void setMsgUser(String msgUser) {
        this.msgUser = msgUser;
    }


    public String getMsgDesc() {
        return msgDesc;
    }

    public void setMsgDesc(String msgDesc) {
        this.msgDesc = msgDesc;
    }

    public Date getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }
}

