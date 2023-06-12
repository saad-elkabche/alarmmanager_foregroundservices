package com.saadev.adhan;

public class ClockModel {
    private String time,state,isrepeting;

    public ClockModel(String time, String state, String isrepeting) {
        this.time = time;
        this.state = state;
        this.isrepeting = isrepeting;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIsrepeting() {
        return isrepeting;
    }

    public void setIsrepeting(String isrepeting) {
        this.isrepeting = isrepeting;
    }
}
