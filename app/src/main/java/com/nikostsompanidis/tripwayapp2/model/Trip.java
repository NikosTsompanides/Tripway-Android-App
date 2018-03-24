package com.nikostsompanidis.tripwayapp2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nikos Τsompanidis on 25/2/2018.
 */

public class Trip implements  Serializable {
    private String img,price,deposit,discountedPrice;
    private String publishFrom,publishTo;
    private double firstLongitude,firtsLatitude,secondLongitude,secondLatitude;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> descriptions = new ArrayList<>();
    private int id;

    public Trip(String img, String price, String deposit, String discountedPrice, String publishFrom, String publishTo, double firstLongitude, double firtsLatitude, double secondLongitude, double secondLatitude, ArrayList<String> titles, ArrayList<String> descriptions, int id) {
        this.img = img;
        this.price = price;
        this.deposit = deposit;
        this.discountedPrice = discountedPrice;
        this.publishFrom = publishFrom;
        this.publishTo = publishTo;
        this.firstLongitude = firstLongitude;
        this.firtsLatitude = firtsLatitude;
        this.secondLongitude = secondLongitude;
        this.secondLatitude = secondLatitude;
        this.titles = titles;
        this.descriptions = descriptions;
        this.id=id;
    }

    public Trip(String img, String price, String deposit, String discountedPrice, String publishFrom, String publishTo, double firstLongitude, double firtsLatitude, double secondLongitude, double secondLatitude) {
        this.img = img;
        this.price = price;
        this.deposit = deposit;
        this.discountedPrice = discountedPrice;
        this.publishFrom = publishFrom;
        this.publishTo = publishTo;
        this.firstLongitude = firstLongitude;
        this.firtsLatitude = firtsLatitude;
        this.secondLongitude = secondLongitude;
        this.secondLatitude = secondLatitude;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getPublishFrom() {
        return publishFrom;
    }

    public void setPublishFrom(String publishFrom) {
        this.publishFrom = publishFrom;
    }

    public String getPublishTo() {
        return publishTo;
    }

    public void setPublishTo(String publishTo) {
        this.publishTo = publishTo;
    }

    public double getFirstLongitude() {
        return firstLongitude;
    }

    public void setFirstLongitude(double firstLongitude) {
        this.firstLongitude = firstLongitude;
    }

    public double getFirtsLatitude() {
        return firtsLatitude;
    }

    public void setFirtsLatitude(double firtsLatitude) {
        this.firtsLatitude = firtsLatitude;
    }

    public double getSecondLongitude() {
        return secondLongitude;
    }

    public void setSecondLongitude(double secondLongitude) {
        this.secondLongitude = secondLongitude;
    }

    public double getSecondLatitude() {
        return secondLatitude;
    }

    public void setSecondLatitude(double secondLatitude) {
        this.secondLatitude = secondLatitude;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(ArrayList<String> descriptions) {
        this.descriptions = descriptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
