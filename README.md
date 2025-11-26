# BookTicket :: Services :: Theater Service

## Overview

The **Theater Service** is the operational core of the BookTicket platform, responsible for managing the physical locations, schedules, and inventory for movie screenings. It bridges the gap between the movie catalog and the actual booking process.

## Core Responsibilities

-   **Theater Management:** Manages information about theater locations, including names, addresses, and the screens they contain.
-   **Screen Management:** Manages the details of individual screens within a theater, including their seating layouts.
-   **Showtime Scheduling:** Manages the schedule of which movies are playing on which screens at what times.
-   **Seat Inventory:** Tracks the availability of seats for each specific show, which is critical for the booking process.

## Architecture

<img width="1266" height="1010" alt="Theater Service" src="https://github.com/user-attachments/assets/9b908de1-d5c3-4c25-97c7-a5146fb7c359" />


### How It Works

1.  **Data Storage:** The service uses a **PostgreSQL** database. The highly relational nature of theaters, screens, shows, and seats makes a traditional RDBMS the ideal choice to enforce data integrity and handle transactional operations like seat management.
2.  **Decoupled Domain:** The Theater Service is intentionally decoupled from the `Movie Service`. It does not store any movie metadata (like titles or posters). Instead, it references movies only by their unique `movieId`. This follows the Single Source of Truth principle.
3.  **Client-Side Composition:** A client application looking to display showtimes for a movie will first call the `Movie Service` to get movie details, and then call the `Theater Service` with the `movieId` to get a list of showtimes.
4.  **Service Discovery:** The Theater Service registers itself with the **Eureka Service Registry**, allowing other services like the API Gateway and Booking Service to discover and communicate with it dynamically.

## Key Dependencies

-   **Spring Boot Starter Web:** For building the REST APIs.
-   **Spring Boot Starter Data JPA:** For database interaction using PostgreSQL.
-   **Spring Boot Starter Security:** For handling role-based authorization on its endpoints.
-   **Eureka Discovery Client:** To register with the service registry.
-   **SpringDoc OpenAPI:** For generating API documentation.

## API Endpoints

The service's endpoints are exposed through the API Gateway. They provide comprehensive access to theater, screen, and show information.

### Theater Endpoints
-   `POST /api/v1/theaters`: Adds a new theater to the system. (Requires `ADMIN` role).
-   `GET /api/v1/theaters`: Retrieves a list of all theaters, with optional filtering by `city`.
-   `GET /api/v1/theaters/{id}`: Fetches detailed information for a single theater by its ID.
-   `GET /api/v1/theaters/{id}/shows`: Retrieves all shows currently scheduled for a specific theater.

### Screen Endpoints
-   `POST /api/v1/screens`: Adds a new screen to a specified theater. (Requires `ADMIN` or `THEATER_OWNER` role).
-   `GET /api/v1/screens/{id}`: Fetches detailed information for a single screen, including its seating arrangement.

### Show Endpoints
-   `POST /api/v1/shows`: Schedules a new show for a movie on a specific screen. (Requires `ADMIN` or `THEATER_OWNER` role).
-   `GET /api/v1/shows`: A powerful search endpoint to find shows. Can be filtered by `movieId`, `city`, and `showDate`.
-   `GET /api/v1/shows/{id}`: Fetches detailed information for a single show, including the availability of all its seats.

### Internal Endpoints
*(These are intended for service-to-service communication and are not exposed on the API Gateway)*
-   `POST /api/v1/internal/shows/verify-seats`: Used by the `Booking Service` to verify that a list of seats for a show are valid and available before proceeding with a booking.
-   `POST /api/v1/internal/shows/lock-seats`: Used by the `Booking Service` to mark seats as temporarily locked during the booking process.
-   `POST /api/v1/internal/shows/book-seats`: Used by the `Booking Service` to permanently mark seats as booked after a successful payment.
