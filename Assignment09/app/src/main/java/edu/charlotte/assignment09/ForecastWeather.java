/*
 * Assignment 9
 * File Name: ForecastWeather.java
 * Full name of the student: Student Name
 */
package edu.charlotte.assignment09;

import java.io.Serializable;

public class ForecastWeather implements Serializable {
    private String dateTime;
    private double temp;
    private double tempMax;
    private double tempMin;
    private String description;
    private int humidity;
    private double windSpeed;
    private double windDegree;
    private int cloudiness;
    private String icon;

    public ForecastWeather(String dateTime, double temp, double tempMax, double tempMin, String description, int humidity, double windSpeed, double windDegree, int cloudiness, String icon) {
        this.dateTime = dateTime;
        this.temp = temp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.description = description;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.cloudiness = cloudiness;
        this.icon = icon;
    }

    public String getDateTime() {
        return dateTime;
    }

    public double getTemp() {
        return temp;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public String getDescription() {
        return description;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public String getIcon() {
        return icon;
    }
}
