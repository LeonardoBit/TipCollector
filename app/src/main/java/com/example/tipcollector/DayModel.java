package com.example.tipcollector;

import java.sql.Date;

public class DayModel {
    private int id;
    private Date dayDate;
    private int week;
    private int cash;
    private int card;
    private int sum;
    private float hours;
    private float hoursByRate;



    //constructors


    public DayModel(int id, Date dayDate,int week, int cash, int card, int sum, float hours, float hoursByRate) {
        this.id = id;
        this.dayDate = dayDate;
        this.week = week;
        this.cash = cash;
        this.card = card;
        this.sum = sum;
        this.hours = hours;
        this.hoursByRate = hoursByRate;
    }

    @Override
    public String toString() {
        return dayDate  +"    "+
                " cash=" + cash +"    "+
                " card=" + card +"    "+
                " hours=" + hours ;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public float getHoursByRate() {
        return hoursByRate;
    }

    public void setHoursByRate(float hoursByRate) {
        this.hoursByRate = hoursByRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.sql.Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }
}
