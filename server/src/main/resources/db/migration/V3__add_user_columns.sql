CREATE TYPE gender AS ENUM ('MALE', 'FEMALE', 'OTHER');
CREATE TYPE theme AS ENUM ('DARK_MODE', 'LIGHT_MODE');

ALTER TABLE users
    ADD COLUMN date_of_birth DATE,
    ADD COLUMN age INTEGER,
    ADD COLUMN phone_number VARCHAR(255),
    ADD COLUMN gender gender,
    ADD COLUMN theme theme;
