package edu.aku.hassannaqvi.casi_2019.other;

public class AgeModel {
    public int days, years, months;

    public AgeModel(int days, int years, int months) {
        this.days = days;
        this.years = years;
        this.months = months;
    }

    public int getdays() {
        return days;
    }

    public void setdays(int days) {
        this.days = days;
    }

    public int getyears() {
        return years;
    }

    public void setyears(int years) {
        this.years = years;
    }

    public int getmonths() {
        return months;
    }

    public void setmonths(int months) {
        this.months = months;
    }
}
