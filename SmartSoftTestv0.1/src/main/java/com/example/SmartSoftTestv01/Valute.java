package com.example.SmartSoftTestv01;

public class Valute {  // класс для хранения информации о валютах
    private String valuteID;
    private String charCode;
    private String name;
    private double value;
    public double getValue(){

        return value;
    }
    public void setValue(double value){

        this.value = value;
    }
    public String getValuteID(){

        return valuteID;
    }
    public void setValuteID(String valuteID) {

        this.valuteID = valuteID;
    }
    public String getCharCode(){

        return charCode;
    }
    public void setCharCode(String charCode){

        this.charCode = charCode;
    }
    public String getName(){

        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Valute [id=" + valuteID+ ", CharCode=" + charCode + ", Name=" + name + ", Value=" + value +  "] \n";
    }
}
