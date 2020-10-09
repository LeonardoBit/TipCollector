package com.example.tipcollector;

import java.sql.Date;

public class DayModel {
    private int id;
    private Date dayDate;
    private int weekOfYear;
    private int cash;
    private int card;
    private int sum;
    private float hours;



    //constructors


    public DayModel(int id, Date dayDate, int weekOfYear, int cash, int card, int sum, float hours) {
        this.id = id;
        this.dayDate = dayDate;
        this.weekOfYear = weekOfYear;
        this.cash = cash;
        this.card = card;
        this.sum = sum;
        this.hours = hours;
    }

    @Override
    public String toString() {
        return dayDate  +"    "+
                " cash=" + cash +"    "+
                " card=" + card +"    "+
                " hours=" + hours ;
    }

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
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
