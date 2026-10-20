package com.example.moviebrowserpractice2;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {

    static private ArrayList<Movie> allMovies = new ArrayList<Movie>() {{
        add(new Movie("Dune", "Denis Villeneuve", 2021, "Sci-Fi"));
        add(new Movie("The Matrix", "The Wachowskis", 1999, "Sci-Fi"));

        add(new Movie("John Wick", "Chad Stahelski", 2014, "Action"));
        add(new Movie("Die Hard", "John McTiernan", 1988, "Action"));

        add(new Movie("Superbad", "Greg Mottola", 2007, "Comedy"));
        add(new Movie("The Hangover", "Todd Phillips", 2009, "Comedy"));
    }};

    static private HashMap<String, ArrayList<Movie>> map;

    static public ArrayList<String> getAllCategories() {
        setupMap();
        return new ArrayList<>(map.keySet());
    }

    static public ArrayList<Movie> getMoviesByCategory(String category) {
        setupMap();
        return map.get(category);
    }

    static private void setupMap() {
        if (map == null) {
            map = new HashMap<>();

            for (Movie movie : allMovies) {

                if (map.containsKey(movie.getCategory())) {
                    map.get(movie.getCategory()).add(movie);
                } else {
                    ArrayList<Movie> list = new ArrayList<>();
                    list.add(movie);
                    map.put(movie.getCategory(), list);
                }
            }
        }
    }
}