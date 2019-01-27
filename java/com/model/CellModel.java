package com.model;

public class CellModel {
    private  String actiondate;
    private String actionName;

    public CellModel(String actiondate, String actionName) {
        this.actiondate = actiondate;
        this.actionName = actionName;
    }

    public String getActiondate() {
        return actiondate;
    }

    public void setActiondate(String actiondate) {
        this.actiondate = actiondate;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
