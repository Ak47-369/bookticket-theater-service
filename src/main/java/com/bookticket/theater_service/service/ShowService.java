package com.bookticket.theater_service.service;


import com.bookticket.theater_service.Entity.Screen;
import com.bookticket.theater_service.Entity.Seat;
import com.bookticket.theater_service.Entity.Show;
import com.bookticket.theater_service.Entity.ShowSeat;
import com.bookticket.theater_service.dto.CreateShowRequest;
import com.bookticket.theater_service.dto.MovieResponse;
import com.bookticket.theater_service.dto.ShowResponse;
import com.bookticket.theater_service.dto.ShowSeatResponse;
import com.bookticket.theater_service.enums.ShowSeatStatus;
import com.bookticket.theater_service.repository.ScreenRepository;
import com.bookticket.theater_service.repository.SeatRepository;
import com.bookticket.theater_service.repository.ShowRepository;
import com.bookticket.theater_service.repository.ShowSeatRepository;
import jakarta.transaction.Transactional;
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
    private final ShowSeatRepository showSeatRepository;
    private final SeatRepository seatRepository;

    public ShowService(ShowRepository showRepository, ScreenRepository screenRepository, RestClient movieRestClient, ShowSeatRepository showSeatRepository, SeatRepository seatRepository) {
        this.showRepository = showRepository;
        this.screenRepository = screenRepository;
        this.movieRestClient = movieRestClient;
        this.showSeatRepository = showSeatRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public ShowResponse createShow(CreateShowRequest createShowRequest) {
        Screen screen = screenRepository.findById(createShowRequest.screenId())
                .orElseThrow(() -> new RuntimeException("Screen not found"));
        log.info("Screen found: {}", screen);
        // TODO : Make it Async, This is a sync service call
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
        Show savedShow = null;
        try {
            savedShow = showRepository.save(newShow);
            log.info("Show created successfully");
        } catch (Exception e) {
            log.error("Error creating show", e);
            throw new RuntimeException(e);
        }

        // Populate ShowSeat Inventory
        List<Seat> templateSeats = seatRepository.findByScreenId(createShowRequest.screenId());
        Show finalSavedShow = savedShow;
        List<ShowSeat> showSeats = templateSeats.stream()
                .map(seat -> {
                    ShowSeat showSeat = new ShowSeat();
                    showSeat.setShow(finalSavedShow);
                    showSeat.setSeat(seat);
                    showSeat.setStatus(ShowSeatStatus.AVAILABLE);
                    showSeat.setPrice(seat.getPrice());
                    return showSeat;
                })
                .toList();

        try {
            showSeatRepository.saveAll(showSeats);
            log.info("ShowSeat created successfully");
        } catch (Exception e) {
            log.error("Error creating showSeat", e);
            throw new RuntimeException(e);
        }

        log.info("Show created successfully");
        return new ShowResponse(
                newShow.getId(),
                newShow.getStartTime(),
                newShow.getEndTime(),
                newShow.getMovieId(),
                newShow.getMovieTitle(),
                newShow.getMovieBannerUrl(),
                newShow.getScreen().getTheater().getName(),
                newShow.getScreen().getTheater().getAddress(),
                newShow.getScreen().getName()
        );

    }

    public List<String> getMovieIdsByCity(String city) {
        return showRepository.findDistinctMovieIdsByCity(city);
    }

    public List<ShowResponse> getShowsByMovieAndCityAndDate(String movieId, String city, java.time.LocalDate date) {
        return showRepository.findShowsByMovieAndCityAndDate(movieId, city, date);
    }

    public List<ShowSeatResponse> getShowSeatsByShowId(Long showId) {
        return showSeatRepository.findShowSeatsByShowId(showId);
    }
}
