package com.app.base.bean;

/**
 * Created by 7du-28 on 2018/1/9.
 */

public class TypeSelect {
    private String type;

    private String title;


    public TypeSelect(String type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
