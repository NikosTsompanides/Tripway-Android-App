package com.nikostsompanidis.tripwayapp2;

/**
 * Created by Nikos Τsompanidis on 25/2/2018.
 */

public class DailyTrip {
    private String img,title,date,price,description;

    public DailyTrip(String img, String title) {
        this.img = img;
        this.title = title;
    }

    public DailyTrip(String img, String title, String date, String price,String description) {
        this.img = img;
        this.title = title;
        this.date = date;
        this.price = price;
        this.description= description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
