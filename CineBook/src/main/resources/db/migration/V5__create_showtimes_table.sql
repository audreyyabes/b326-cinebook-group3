CREATE TABLE showtimes (
    id INT NOT NULL AUTO_INCREMENT,
    movie_id INT NOT NULL,
    theater_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    ticket_price DECIMAL(10,2) NOT NULL,
    status ENUM('scheduled','cancelled','completed') NOT NULL DEFAULT 'scheduled',
    PRIMARY KEY (id),
    KEY fk_showtimes_movie (movie_id),
    KEY fk_showtimes_theater (theater_id),
    CONSTRAINT fk_showtimes_movie FOREIGN KEY (movie_id) REFERENCES movies (id),
    CONSTRAINT fk_showtimes_theater FOREIGN KEY (theater_id) REFERENCES theaters (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
