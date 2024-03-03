CREATE TABLE USERS
(
    ID                  VARCHAR(255) primary key,
    EMAIL               VARCHAR(255) UNIQUE NOT NULL,
    ENCRYPTED_PASSWORD  VARCHAR(255)        NOT NULL,
    FIRST_NAME          VARCHAR(255)        NOT NULL,
    LAST_NAME           VARCHAR(255)        NOT NULL,
    PHONE_NUMBER        VARCHAR(30),
    PHONE_NUMBER_REGION VARCHAR(10),
    DATE_OF_BIRTH       DATE                NOT NULL,
    ROLE                VARCHAR(40)
);