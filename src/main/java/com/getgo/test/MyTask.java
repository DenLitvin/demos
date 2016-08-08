package com.getgo.test;

import java.io.Serializable;

/**
 * Created by denisr on 7/29/2016.
 */
public class MyTask implements Serializable {



    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    String action;

}
