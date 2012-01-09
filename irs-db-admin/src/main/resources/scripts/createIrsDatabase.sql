DROP TABLE IF EXISTS linkset;

CREATE TABLE linkset (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    subjectsTarget VARCHAR(2083) NOT NULL,
    objectsTarget VARCHAR(2083) NOT NULL,
    linkPredicate VARCHAR(2083) NOT NULL,
    dateCreated DATE,
    creator VARCHAR(2083),
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS mapping;

CREATE TABLE mapping (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    linkset_id MEDIUMINT NOT NULL,
    source VARCHAR(2083),
    predicate VARCHAR(2083),
    target VARCHAR(2083),
    PRIMARY KEY (id),
    CONSTRAINT fk_mapping_linkset FOREIGN KEY (linkset_id) REFERENCES linkset (id) ON DELETE CASCADE,
    INDEX (source),
    INDEX (target)
);