CREATE TABLE payments (
    id INT NOT NULL AUTO_INCREMENT,
    booking_id INT NOT NULL,
    payment_reference VARCHAR(100) DEFAULT NULL,
    payment_method VARCHAR(50) DEFAULT NULL,
    amount DECIMAL(10,2) NOT NULL,
    status ENUM('pending','paid','failed','refunded') NOT NULL DEFAULT 'pending',
    paid_at DATETIME DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_payments_booking (booking_id),
    CONSTRAINT fk_payments_booking FOREIGN KEY (booking_id) REFERENCES bookings (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
