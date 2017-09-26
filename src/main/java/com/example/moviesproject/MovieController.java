package com.example.moviesproject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class MovieController {

    static final String API_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=be2a38521a7859c95e2d73c48786e4bb";

    @RequestMapping("/")
    public String index(Model model) {

        return "home";
    }

    @RequestMapping("/now-playing")
    public String nowPlaying(Model model) {
        List<Movie> movies = getMovies();
        model.addAttribute("movies", movies);

        return "now-playing";
    }

    @RequestMapping("/medium-popular-long-name")
    public String mediumPopularLongName(Model model) {
        // filter the list to include movies whose title is at least 10 characters long
        // and popularity is between 30 and 80
        List<Movie> movies = getMovies();
        List<Movie> filteredMovies;
        filteredMovies = movies.stream()
                .filter( movie -> movie.getTitle().length() > 10 )
                .filter( movie -> movie.getPopularity() > 100 && movie.getPopularity() < 200)
                .collect( Collectors.toList());
        model.addAttribute("movies", filteredMovies);

        return "now-playing";
    }

    @RequestMapping("/overview-mashup")
    public String overviewMashup(Model model) {
        List<Movie> movies = getMovies();
        List<Movie> mashupMovies;

        ArrayList<String> randomSentences = new ArrayList<String>();

        for( Movie movie : movies ) {
            // grab the description, and split into sentences
            for( String sentence : movie.getOverview().split("\\.")) {
                // push the sentence
                randomSentences.add(sentence);
            }
        }
        Random random = new Random();

        Collections.sort(randomSentences, (e1, e2) -> random.nextInt(3) - 1);
        // now we have randomSentences populated
        // go through the movie list and modify the overview
        for( Movie movie : movies ) {
            movie.setOverview(randomSentences.get(random.nextInt(randomSentences.size())) + ". " +
                    randomSentences.get(random.nextInt(randomSentences.size())) + ". " +
                    randomSentences.get(random.nextInt(randomSentences.size()))
            );
        }

        System.out.println(movies);

        model.addAttribute("movies", movies);

        return "now-playing";
    }

    public static List<Movie> getMovies() {

        IMDBResponse response = new IMDBResponse();
        List<Movie> movies;
        RestTemplate restTemplate = new RestTemplate();

        try {
            response =
                    restTemplate.getForObject(API_URL, IMDBResponse.class);

        } catch( RestClientException ex) {
            System.out.println(ex);
        }

        movies = response.getResults();

        return movies;
    }
}
