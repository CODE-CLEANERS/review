CREATE TABLE IF NOT EXISTS MEMBER (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(50) NOT NULL,
                        join_date TIMESTAMP NOT NULL,
                        member_level VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS GAME (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS GAME_CARD (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          title VARCHAR(100) NOT NULL,
                          price DECIMAL(20,2) NOT NULL,
                          serial_number BIGINT NOT NULL,
                          game_id BIGINT,
                          member_id BIGINT,
                          CONSTRAINT FK_GAME FOREIGN KEY (game_id) REFERENCES GAME(id),
                          CONSTRAINT FK_MEMBER FOREIGN KEY (member_id) REFERENCES MEMBER(id),
                          UNIQUE(game_id, serial_number)
);