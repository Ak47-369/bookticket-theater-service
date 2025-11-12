package com.bookticket.theater_service.service;


import com.bookticket.theater_service.Entity.Screen;
import com.bookticket.theater_service.Entity.Show;
import com.bookticket.theater_service.dto.CreateShowRequest;
import com.bookticket.theater_service.dto.MovieResponse;
import com.bookticket.theater_service.dto.ShowResponse;
import com.bookticket.theater_service.repository.ScreenRepository;
import com.bookticket.theater_service.repository.ShowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Slf4j
public class ShowService {
    private final ShowRepository showRepository;
    private final ScreenRepository screenRepository;
    private final RestClient movieRestClient;

    public ShowService(ShowRepository showRepository, ScreenRepository screenRepository,  RestClient movieRestClient) {
        this.showRepository = showRepository;
        this.screenRepository = screenRepository;
        this.movieRestClient = movieRestClient;
    }

    public ShowResponse createShow(CreateShowRequest createShowRequest) {
        Screen screen = screenRepository.findById(createShowRequest.screenId())
                .orElseThrow(() -> new RuntimeException("Screen not found"));
        log.info("Screen found: {}", screen);
        // TO DO : Make it Async, This is a sync service call
        MovieResponse movieResponse = movieRestClient.get()
                .uri("/api/v1/movies/{movieId}", createShowRequest.movieId())
                .retrieve()
                .body(MovieResponse.class);
        if(movieResponse == null) {
            throw new RuntimeException("Movie not found with id : "+ createShowRequest.movieId());
        }

        Show newShow = new Show();
        newShow.setStartTime(createShowRequest.startTime());
        newShow.setEndTime(createShowRequest.endTime());
        newShow.setMovieId(createShowRequest.movieId());
        newShow.setMovieTitle(movieResponse.title());
        newShow.setMovieBannerUrl(movieResponse.bannerUrl());
        newShow.setScreen(screen);
        try {
            showRepository.save(newShow);
            log.info("Show created successfully");
        } catch (Exception e) {
            log.error("Error creating show", e);
            throw new RuntimeException(e);
        }
        return new ShowResponse(
                newShow.getStartTime(),
                newShow.getEndTime(),
                newShow.getMovieId(),
                newShow.getMovieTitle(),
                newShow.getMovieBannerUrl()
        );

    }

    public List<String> getMovieIdsByCity(String city) {
        return showRepository.findDistinctMovieIdsByCity(city);
    }
}
