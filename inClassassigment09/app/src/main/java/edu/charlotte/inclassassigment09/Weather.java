package edu.charlotte.inclassassigment09;


import java.io.Serializable;

public class Weather implements Serializable {
    private double temp;
    private double tempMax;
    private double tempMin;
    private String description;
    private int humidity;
    private double windSpeed;
    private double windDegree;
    private int cloudiness;
    private String icon;
    private String cityName;
    private String country;

    public Weather(double temp, double tempMax, double tempMin, String description, int humidity, double windSpeed, double windDegree, int cloudiness, String icon, String cityName, String country) {
        this.temp = temp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.description = description;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.cloudiness = cloudiness;
        this.icon = icon;
        this.cityName = cityName;
        this.country = country;
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

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }
}
