CREATE TABLE seats (
    id INT NOT NULL AUTO_INCREMENT,
    theater_id INT NOT NULL,
    seat_code VARCHAR(10) NOT NULL,
    seat_row VARCHAR(5) DEFAULT NULL,
    seat_number INT DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_seats_theater (theater_id),
    CONSTRAINT fk_seats_theater FOREIGN KEY (theater_id) REFERENCES theaters (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
