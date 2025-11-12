package com.bookticket.theater_service.service;

import com.bookticket.theater_service.Entity.Theater;
import com.bookticket.theater_service.dto.CreateTheaterRequest;
import com.bookticket.theater_service.dto.TheaterResponse;
import com.bookticket.theater_service.dto.UpdateTheaterRequest;
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
    
    public TheaterResponse getTheater(Long theaterId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theater not found"));
        return new TheaterResponse(theater.getId(), theater.getName(), theater.getAddress());
    }

    public void deleteTheater(Long theaterId) {
        // TO DO : Check if not exists - throws ResouceNotFound Exception, use GlobalExaception Handler
        theaterRepository.deleteById(theaterId);
    }

    public TheaterResponse updateTheater(Long theaterId, UpdateTheaterRequest updateTheaterrequest) {
        try{
            Theater theater = theaterRepository.findById(theaterId)
                    .orElseThrow(() -> new RuntimeException("Theater not found"));

            updateIfNotEmpty(updateTheaterrequest.name(), theater::setName);
            updateIfNotEmpty(updateTheaterrequest.address(), theater::setAddress);
            updateIfNotEmpty(updateTheaterrequest.city(), theater::setCity);
            updateIfNotEmpty(updateTheaterrequest.state(), theater::setState);
            updateIfNotEmpty(updateTheaterrequest.zip(), theater::setZip);
            updateIfNotEmpty(updateTheaterrequest.landmark(), theater::setLandmark);

            Theater updatedTheater = theaterRepository.save(theater);
            log.info("Theater updated successfully {}", updatedTheater);
            return new TheaterResponse(updatedTheater.getId(), updatedTheater.getName(),
                    updatedTheater.getAddress());
        } catch (Exception e) {
            log.error("Error updating theater", e);
            throw new RuntimeException(e);
        }
    }

    private void updateIfNotEmpty(String value, java.util.function.Consumer<String> setter) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        }
    }

}
