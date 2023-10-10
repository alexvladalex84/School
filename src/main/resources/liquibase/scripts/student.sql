-- liquibase formatted sql


-- changeset alexandr:1
CREATE INDEX student_name ON student (name);

-- changeset alexandr:2
create index faculty_name on faculty (name,color);


