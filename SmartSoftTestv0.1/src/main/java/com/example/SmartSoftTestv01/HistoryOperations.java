package com.example.SmartSoftTestv01;

public class HistoryOperations {  // класс для хранения информации об произошедших операциях
    private String from;
    private String to;
    private double fromValue;
    private double toValue;
    private String date;
    public void setFrom(String from){
        this.from=from;
    }
    public void setTo(String to){
        this.to=to;
    }
    public void setFromValue(double fromValue){
        this.fromValue=fromValue;
    }
    public void setToValue(double toValue){
        this.toValue=toValue;
    }
    public void setDate(String date){
        this.date=date;
    }
    public String getFrom(){
        return from;
    }
    public String getTo(){
        return to;
    }
    public double getFromValue(){
        return fromValue;
    }
    public double getToValue(){
        return toValue;
    }
    public String getDate(){
        return date;
    }


}
