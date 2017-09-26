package com.example.moviesproject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
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

        return "overview-mashup";
    }

    public static List<Movie> getMovies() {

        IMDBResponse response = new IMDBResponse();
        List<Movie> movies;

        RestTemplate restTemplate = new RestTemplate();

        System.out.println("Before RestTemplate call");

        //ResponseEntity<Movie> responseEntity = restTemplate.getForEntity(API_URL, Movie.class);

        //HttpStatus statusCode = responseEntity.getStatusCode();

        // System.out.println("Status code: " + statusCode );

        try {
            response =
                    restTemplate.getForObject(API_URL, IMDBResponse.class);

        } catch( RestClientException ex) {
            System.out.println(ex);
        }

        System.out.println(response.getTotal_pages());
        System.out.println(response.getResults().get(0).getTitle());
        System.out.println(response.getResults().get(0).getOverview());
        System.out.println(response.getResults().get(0).getPopularity());
        System.out.println(response.getResults().get(0).getPoster_path());

        movies = response.getResults();

        return movies;
    }
}
