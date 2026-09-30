CREATE TABLE movies (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    description TEXT DEFAULT NULL,
    duration_minutes INT NOT NULL,
    genre VARCHAR(100) DEFAULT NULL,
    release_date DATE DEFAULT NULL,
    rating VARCHAR(20) DEFAULT NULL,
    status ENUM('active','inactive') NOT NULL DEFAULT 'active',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
