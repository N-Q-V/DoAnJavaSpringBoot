package com.example.fashion_spring_boot.entity;

public class ErrorMsg {
    private String msg;
    private int status;
    private long timestamp;

    public ErrorMsg() {
    }

    public ErrorMsg(String msg, int status) {
        this.msg = msg;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
