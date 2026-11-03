package edu.charlotte.assignment13.models;

import java.io.Serializable;
import java.util.ArrayList;

public class DataService {
    static public final ArrayList<City> cities = new ArrayList<City>() {{
        add(new City("US", "Charlotte", 35.2271, -80.8431));
        add(new City("US", "Chicago", 41.8781, -87.6298));
        add(new City("US", "New York", 40.7128, -74.0060));
        add(new City("US", "Miami", 25.7617, -80.1918));
        add(new City("US", "San Francisco", 37.7749, -122.4194));
        add(new City("US", "Baltimore", 39.2904, -76.6122));
        add(new City("US", "Houston", 29.7604, -95.3698));
        add(new City("UK", "London", 51.5074, -0.1278));
        add(new City("UK", "Bristol", 51.4545, -2.5879));
        add(new City("UK", "Cambridge", 52.2053, 0.1218));
        add(new City("UK", "Liverpool", 53.4084, -2.9916));
        add(new City("AE", "Abu Dhabi", 24.4539, 54.3773));
        add(new City("AE", "Dubai", 25.276987, 55.296249));
        add(new City("AE", "Sharjah", 25.3463, 55.4209));
        add(new City("JP", "Tokyo", 35.6762, 139.6503));
        add(new City("JP", "Kyoto", 35.0116, 135.7681));
        add(new City("JP", "Hashimoto", 34.3173, 135.6124));
        add(new City("JP", "Osaka", 34.6937, 135.5023));
    }};

    static public class City implements Serializable {
        private String country;
        private String city;
        private double latitude;
        private double longitude;

        public City(String country, String city, double latitude, double longitude) {
            this.country = country;
            this.city = city;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            return city + ", " + country + " (" + latitude + ", " + longitude + ")";
        }
    }
}
