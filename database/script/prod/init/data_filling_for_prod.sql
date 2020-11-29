USE hrinkov_social_network;

ALTER TABLE locations AUTO_INCREMENT = 1;

INSERT INTO locations VALUES
(NULL, 'Belarus', 'Minsk'),
(NULL, 'Belarus', 'Gomel'),
(NULL, 'Belarus', 'Vitebsk'),
(NULL, 'Belarus', 'Grodno'),
(NULL, 'Belarus', 'Brest'),
(NULL, 'Belarus', 'Bobruisk'),
(NULL, 'Belarus', 'Baranovichi');

ALTER TABLE schools AUTO_INCREMENT = 1;

INSERT INTO schools VALUES
(NULL, "Secondary school number 104", 1),
(NULL, "Secondary school number 95", 1),
(NULL, "Secondary school number 21", 2),
(NULL, "Secondary school number 69", 2),
(NULL, "Secondary school number 2", 3),
(NULL, "Secondary school number 4", 3),
(NULL, "Secondary school number 38", 4),
(NULL, "Secondary school number 31", 4),
(NULL, "Secondary school number 32", 4),
(NULL, "Secondary school number 8", 5),
(NULL, "Secondary school number 9", 5),
(NULL, "Secondary school number 1", 6),
(NULL, "Secondary school number 3", 6),
(NULL, "Secondary school number 12", 7),
(NULL, "Secondary school number 22", 7);

ALTER TABLE universities AUTO_INCREMENT = 1;

INSERT INTO universities VALUES
(NULL, "Academy of Management under the President of the Republic of Belarus", 1),
(NULL, "Belarusian State University", 1),
(NULL, "Belarusian State University of Transport", 2),
(NULL, "Gomel State Medical University", 2),
(NULL, "Vitebsk State University named after P.M. Masherova", 3),
(NULL, "Vitebsk State Technological University", 3),
(NULL, "Grodno State Agrarian University", 4),
(NULL, "Grodno State Medical University", 4),
(NULL, "Baranovichi State University", 5),
(NULL, "Brest State Technical University", 5);

ALTER TABLE users AUTO_INCREMENT = 1;

INSERT INTO users VALUES
(NULL, 'admin@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_ADMIN');

COMMIT;