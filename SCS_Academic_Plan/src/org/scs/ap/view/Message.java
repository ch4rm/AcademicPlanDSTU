package org.scs.ap.view;

public class Message {
    private static boolean isShow;
    private static String message;

    public void setMessage(String message){
        Message.message = message;
        isShow = true;
    }

    public boolean isShow(){
        return isShow;
    }

    public String getMessage(){
        isShow = false;
        return message;
    }
}
