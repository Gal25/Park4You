package com.example.park4you;

public enum ServerStrings {
    AUTH("http://10.0.2.2:4242/api/user/auth/:");


    private String s;

    ServerStrings(String s) {
        this.s = s;
    }
    @Override
    public String toString() {
        return s;
    }

}
