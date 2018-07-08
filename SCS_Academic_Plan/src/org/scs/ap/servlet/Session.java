package org.scs.ap.servlet;

public class Session {
    private static int acces = 3;

    public void setAcces(int acces){
        this.acces = acces;
    }

    public int getAcces(){
        return acces;
    }
}
