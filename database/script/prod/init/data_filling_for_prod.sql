USE hrinkov_social_network;

ALTER TABLE users AUTO_INCREMENT = 1;

INSERT INTO users VALUES
(NULL, 'admin@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_ADMIN');

COMMIT;