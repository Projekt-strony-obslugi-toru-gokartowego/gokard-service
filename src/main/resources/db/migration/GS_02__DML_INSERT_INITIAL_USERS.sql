INSERT INTO USERS (ID, EMAIL, ENCRYPTED_PASSWORD, FIRST_NAME, LAST_NAME, PHONE_NUMBER, PHONE_NUMBER_REGION, DATE_OF_BIRTH, ROLE)
VALUES
    ('1', 'Administrator@mail.com', '$2a$10$O.BOYaEyqkfNVXc9FfB0zeaW9ndXSYZ08XA/ksEVvOs64VM1zBTWS', 'Jan', 'Kowalski', '123456789', 'PL', '1999-12-22', 'ADMINISTRATOR'), -- password: qwertyuiop
    ('2', 'Worker1@mail.com', '$2a$10$wf5U.kBvve4DHvS/yhhFnuMsC6NKh3SuPfzVnX6G5fjtTRBQl/pum', 'Stefan', 'Nowak', '987654321', 'PL', '2000-05-15', 'WORKER'), -- decrypted password:  poiuytrewq
    ('3', 'Client1@mail.com', '$2a$10$cRVbkMIRex3ICEgGa3Am2e12EwCcek8yygNdVW/YTJiAyHss/QAbm', 'Kornel', 'Anonymous', '123789456', 'PL', '1998-07-18', 'CLIENT'); -- decrypted password: !QAZxsw2