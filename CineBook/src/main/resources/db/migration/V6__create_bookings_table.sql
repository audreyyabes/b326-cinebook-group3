CREATE TABLE bookings (
    id INT NOT NULL AUTO_INCREMENT,
    booking_code VARCHAR(30) NOT NULL,
    user_id INT NOT NULL,
    showtime_id INT NOT NULL,
    booking_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    status ENUM('pending','confirmed','cancelled','completed') NOT NULL DEFAULT 'pending',
    PRIMARY KEY (id),
    KEY fk_bookings_user (user_id),
    KEY fk_bookings_showtime (showtime_id),
    CONSTRAINT fk_bookings_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_bookings_showtime FOREIGN KEY (showtime_id) REFERENCES showtimes (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
