package com.example.park4you;

public enum Server {
    AUTH("http://10.0.2.2:4242/api/user/auth/:");


    private String s;

    Server(String s) {
        this.s = s;
    }
    @Override
    public String toString() {
        return s;
    }

}
