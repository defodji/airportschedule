CREATE TABLE IF NOT EXISTS arrival (
    flight_number varchar(20) PRIMARY KEY,
    scheduled_time time NOT NULL,
    actual_time time NOT NULL,
    city_from varchar(40) NOT NULL,
    airline varchar(20) NOT NULL,
    aircraft varchar(40)
);
CREATE INDEX idx_arrival_scheduled ON arrival(scheduled_time);

CREATE TABLE IF NOT EXISTS departure (
    flight_number varchar(20) PRIMARY KEY,
    scheduled_time time NOT NULL,
    actual_time time NOT NULL,
    city_to varchar(40) NOT NULL,
    airline varchar(20) NOT NULL,
    aircraft varchar(40)
);
CREATE INDEX idx_departure_scheduled ON departure(scheduled_time);