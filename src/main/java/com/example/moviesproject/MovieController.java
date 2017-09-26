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

@Controller
public class MovieController {

    static final String API_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=be2a38521a7859c95e2d73c48786e4bb";

    @RequestMapping("/")
    public String index(Model model) {

        return "home";
    }

    @RequestMapping("/now-playing")
    public String nowPlaying(Model model) {
        IMDBResponse movies = getMovies();
        System.out.println(movies);
        return "now-playing";
    }

    @RequestMapping("/medium-popular-long-name")
    public String mediumPopularLongName(Model model) {

        return "medium-popular-long-name";
    }

    @RequestMapping("/overview-mashup")
    public String overviewMashup(Model model) {

        return "overview-mashup";
    }

    public static IMDBResponse getMovies() {

        IMDBResponse response = new IMDBResponse();

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
        System.out.println(response.getResults());

        return response;
    }
}
