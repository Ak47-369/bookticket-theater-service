package com.bookticket.theater_service.repository;

import com.bookticket.theater_service.Entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen,Long> {
}
