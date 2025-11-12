package com.bookticket.theater_service.service;

import com.bookticket.theater_service.Entity.Screen;
import com.bookticket.theater_service.Entity.Theater;
import com.bookticket.theater_service.dto.CreateScreenRequest;
import com.bookticket.theater_service.dto.ScreenResponse;
import com.bookticket.theater_service.repository.ScreenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScreenService {
    private final ScreenRepository screenRepository;

    public ScreenService(ScreenRepository screenRepository) {
        this.screenRepository = screenRepository;
    }

    public ScreenResponse createScreen(CreateScreenRequest createScreenRequest) {
        try{
            Screen newScreen = new Screen();
            newScreen.setName(createScreenRequest.name());
            newScreen.setRows(createScreenRequest.rows());
            newScreen.setColumns(createScreenRequest.columns());
            newScreen.setTheater(new Theater());
            newScreen.getTheater().setId(createScreenRequest.theaterId());
            newScreen.setScreenType(createScreenRequest.screenType());
            Screen createdScreen = screenRepository.save(newScreen);
            log.info("Screen created successfully {}", createdScreen);
            return new ScreenResponse(createdScreen.getId(), createdScreen.getName(),
                    createdScreen.getRows(), createdScreen.getColumns(),
                    createdScreen.getScreenType());
        } catch (Exception e) {
            log.error("Error creating screen", e);
            throw new RuntimeException(e);
        }
    }
}
