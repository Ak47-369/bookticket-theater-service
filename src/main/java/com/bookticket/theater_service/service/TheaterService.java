package com.bookticket.theater_service.service;

import com.bookticket.theater_service.Entity.Theater;
import com.bookticket.theater_service.dto.CreateTheaterRequest;
import com.bookticket.theater_service.dto.TheaterResponse;
import com.bookticket.theater_service.repository.TheaterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TheaterService {
    private final TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    public TheaterResponse createTheater(CreateTheaterRequest createTheaterRequest) {
        try{
            Theater theater = new Theater();
            theater.setName(createTheaterRequest.name());
            theater.setAddress(createTheaterRequest.address());
            theater.setCity(createTheaterRequest.city());
            theater.setState(createTheaterRequest.state());
            theater.setZip(createTheaterRequest.zip());
            theater.setLandmark(createTheaterRequest.landmark());
            Theater createdTheater = theaterRepository.save(theater);
            log.info("Theater created successfully");
            return new TheaterResponse(createdTheater.getId(), createdTheater.getName(),
                    createdTheater.getAddress());
        } catch (Exception e) {
            log.error("Error creating theater", e);
            throw new RuntimeException(e);
        }
    }

}
