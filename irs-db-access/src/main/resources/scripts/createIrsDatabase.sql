DROP TABLE mapping;

CREATE TABLE mapping (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    source VARCHAR(2083),
    predicate VARCHAR(2083),
    target VARCHAR(2083),
    PRIMARY KEY (id)
);