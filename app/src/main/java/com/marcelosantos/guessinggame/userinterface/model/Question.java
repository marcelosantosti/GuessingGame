package com.marcelosantos.guessinggame.userinterface.model;

/**
 * Created by Marcelo on 18/10/2015.
 */
//// TODO: 20/10/2015
//make class typeable. when i did this, initially, i get some problems. no time to do this now :)
public class Question {

    private boolean ask;
    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isAsk() {
        return ask;
    }

    public void setAsk(boolean ask) {
        this.ask = ask;
    }
}
