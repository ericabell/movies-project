package com.example.moviesproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IMDBResponse implements Serializable {
    private List results;
    private Integer total_pages;

    public IMDBResponse() {
    }

//    public List getResults() {
//        return results;
//    }
//
//    public void setResults(List results) {
//        this.results = results;
//    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }
}
