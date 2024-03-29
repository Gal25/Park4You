package com.example.park4you.Object;

public class Time {
    /**
     * This class represents the time the parking is available for rent. It has the usual constructor, getters, setters and a toString.
     */
    private String start;
    private String end;

    public Time(String start, String end){
        this.start = start;
        this.end = end;
    }
    public Time(){}

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return start + "-" + end;
//        return "Time{" +
//                "start='" + start + '\'' +
//                ", end='" + end + '\'' +
//                '}';
    }
}
