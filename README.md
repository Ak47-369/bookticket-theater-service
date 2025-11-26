# BookTicket :: Services :: Theater Service

## Overview

The **Theater Service** is the operational core of the BookTicket platform, responsible for managing the physical locations, schedules, and inventory for movie screenings. It bridges the gap between the movie catalog and the actual booking process.

## Core Responsibilities

-   **Theater Management:** Manages information about theater locations, including names, addresses, and the screens they contain.
-   **Screen Management:** Manages the details of individual screens within a theater, including their seating layouts.
-   **Showtime Scheduling:** Manages the schedule of which movies are playing on which screens at what times.
-   **Seat Inventory:** Tracks the availability of seats for each specific show, which is critical for the booking process.

## Architecture
<img width="1305" height="1052" alt="Theater service" src="https://github.com/user-attachments/assets/cef65330-b339-47d5-b728-5cf960b57897" />



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

The service's endpoints are exposed through the API Gateway. Key operations include:

-   `GET /api/v1/theaters`: Fetches a list of all theaters.
-   `GET /api/v1/theaters/{id}/shows`: Fetches all shows for a specific theater.
-   `GET /api/v1/shows`: Allows searching for shows, for example, by `movieId` and `city`.
