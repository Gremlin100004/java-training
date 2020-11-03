-- Todo don't forget to delete this line
DROP DATABASE IF EXISTS hrinkov_social_network;

CREATE SCHEMA IF NOT EXISTS hrinkov_social_network DEFAULT CHARACTER SET utf8 ;

USE hrinkov_social_network;

-- -----------------------------------------------------
-- Table `locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS locations (
  id INT NOT NULL AUTO_INCREMENT,
  city VARCHAR(45) NOT NULL UNIQUE,
  PRIMARY KEY pk_locations (id)
);

-- -----------------------------------------------------
-- Table `schools`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS schools (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(80) NOT NULL UNIQUE,
  location_id INT NOT NULL,
  PRIMARY KEY pk_schools (id)
);

-- -----------------------------------------------------
-- Table `universities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS universities (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(80) NOT NULL UNIQUE,
  location_id INT NOT NULL,
  PRIMARY KEY pk_universities (id)
);

-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
  id INT NOT NULL AUTO_INCREMENT,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(20) NOT NULL,
  role VARCHAR(20) NOT NULL,
  registration_date DATETIME NOT NULL,
  date_of_birth DATETIME NULL,
  name VARCHAR(45) NULL,
  surname VARCHAR(45) NULL,
  telephone_number VARCHAR(13) NULL,
  location_id INT NULL,
  school_id INT NULL,
  school_graduation_year DATETIME NULL,
  university_id INT NULL,
  university_graduation_year DATETIME NULL,
  PRIMARY KEY pk_users (id)
);

-- -----------------------------------------------------
-- Table `public_messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public_messages (
  id INT NOT NULL AUTO_INCREMENT,
  creation_time DATETIME NOT NULL,
  author_id INT NOT NULL,
  heading VARCHAR(100) NULL,
  content VARCHAR(1000) NULL,
  is_deleted BOOLEAN DEFAULT false,
  PRIMARY KEY pk_public_messages (id)
);

-- -----------------------------------------------------
-- Table `comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS comments (
  id INT NOT NULL,
  creation_time DATETIME NOT NULL,
  author_id INT NOT NULL,
  public_message_id INT NOT NULL,
  content VARCHAR(1000) NULL,
  is_deleted BOOLEAN DEFAULT false,
  PRIMARY KEY pk_comments (id)
);

-- -----------------------------------------------------
-- Table `messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS messages (
  id INT NOT NULL AUTO_INCREMENT,
  departure_time DATETIME NOT NULL,
  sender_id INT NOT NULL,
  recipient_id INT NOT NULL,
  content VARCHAR(1000) NULL,
  is_read BOOLEAN DEFAULT false,
  is_deleted BOOLEAN DEFAULT false,
  PRIMARY KEY pk_messages (id)
);

-- -----------------------------------------------------
-- Table `friendship_requests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS friendship_requests (
  id INT NOT NULL,
  user_id INT NOT NULL,
  friend_id INT NOT NULL,
  PRIMARY KEY pk_friendship_requests (id)
);

-- -----------------------------------------------------
-- Table `communities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS communities (
  id INT NOT NULL AUTO_INCREMENT,
  creation_time DATETIME NOT NULL,
  author_id INT NOT NULL,
  type VARCHAR(20) NOT NULL,
  heading VARCHAR(100) NOT NULL,
  information VARCHAR(1000) NULL,
  is_deleted BOOLEAN DEFAULT false,
  PRIMARY KEY pk_communities (id)
);

-- -----------------------------------------------------
-- Table `posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS posts (
  id INT NOT NULL AUTO_INCREMENT,
  heading VARCHAR(100) NULL,
  content VARCHAR(1000) NULL,
  author_id INT NOT NULL,
  communities_id INT NOT NULL,
  is_deleted BOOLEAN DEFAULT false,
  creation_time DATETIME NOT NULL,
  PRIMARY KEY pk_posts (id)
);

-- -----------------------------------------------------
-- Table `post_comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS post_comments (
  id INT NOT NULL,
  creation_time DATETIME NOT NULL,
  author_id INT NOT NULL,
  post_id INT NOT NULL,
  content VARCHAR(1000) NULL,
  is_deleted BOOLEAN DEFAULT false,
  PRIMARY KEY pk_post_comments (id)
);

-- -----------------------------------------------------
-- Table `sun_calendars`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sun_calendars (
  id INT NOT NULL AUTO_INCREMENT,
  date DATETIME NOT NULL,
  beginning_of_twilight DATETIME NOT NULL,
  sunrise DATETIME NOT NULL,
  highest_point DATETIME NOT NULL,
  sunset DATETIME NOT NULL,
  end_of_twilight DATETIME NOT NULL,
  PRIMARY KEY pk_sun_calendars (id)
);

-- -----------------------------------------------------
-- Table `friends`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS friends (
  user_id INT NOT NULL,
  friend_id INT NOT NULL
);

-- -----------------------------------------------------
-- Table `communities_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS communities_users (
  communities_id INT NOT NULL,
  users_id INT NOT NULL
);

-- -----------------------------------------------------
-- ADD CONSTRAINTS
-- -----------------------------------------------------
ALTER TABLE schools
ADD CONSTRAINT fk_schools
FOREIGN KEY (location_id)
REFERENCES locations (id) ON DELETE CASCADE;

ALTER TABLE universities
ADD CONSTRAINT fk_universities
FOREIGN KEY (location_id)
REFERENCES locations (id) ON DELETE CASCADE;

ALTER TABLE users
ADD CONSTRAINT fk_users_locations
FOREIGN KEY (location_id)
REFERENCES locations (id) ON DELETE CASCADE;

ALTER TABLE users
ADD CONSTRAINT fk_users_schools
FOREIGN KEY (school_id)
REFERENCES schools (id) ON DELETE CASCADE;

ALTER TABLE users
ADD CONSTRAINT fk_users_universities
FOREIGN KEY (university_id)
REFERENCES universities (id) ON DELETE CASCADE;

ALTER TABLE public_messages
ADD CONSTRAINT fk_public_messages
FOREIGN KEY (author_id)
REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE comments
ADD CONSTRAINT fk_comments_users
FOREIGN KEY (author_id)
REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE comments
ADD CONSTRAINT fk_comments_public_messages
FOREIGN KEY (public_message_id)
REFERENCES public_messages (id) ON DELETE CASCADE;

ALTER TABLE messages
ADD CONSTRAINT fk_messages_users_sender
FOREIGN KEY (sender_id)
REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE messages
ADD CONSTRAINT fk_messages_users_recipient
FOREIGN KEY (recipient_id)
REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE friendship_requests
ADD CONSTRAINT fk_friendship_requests_users_user
FOREIGN KEY (user_id)
REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE friendship_requests
ADD CONSTRAINT fk_friendship_requests_users_friend
FOREIGN KEY (friend_id)
REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE communities
ADD CONSTRAINT fk_communities_users
FOREIGN KEY (author_id)
REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE posts
ADD CONSTRAINT fk_posts_users
FOREIGN KEY (author_id)
REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE posts
ADD CONSTRAINT fk_posts_communities
FOREIGN KEY (communities_id)
REFERENCES communities (id) ON DELETE CASCADE;

ALTER TABLE post_comments
ADD CONSTRAINT fk_post_comments_users
FOREIGN KEY (author_id)
REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE post_comments
ADD CONSTRAINT fk_post_comments_posts
FOREIGN KEY (post_id)
REFERENCES posts (id) ON DELETE CASCADE;

ALTER TABLE friends
ADD CONSTRAINT fk_friends_users_user
FOREIGN KEY (user_id)
REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE friends
ADD CONSTRAINT fk_friends_users_friend
FOREIGN KEY (friend_id)
REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE communities_users
ADD CONSTRAINT fk_communities_communities
FOREIGN KEY (communities_id)
REFERENCES communities (id) ON DELETE CASCADE;

ALTER TABLE communities_users
ADD CONSTRAINT fk_communities_users_users
FOREIGN KEY (users_id)
REFERENCES users (id) ON DELETE CASCADE;

-- -----------------------------------------------------
-- CREATE INDEX
-- -----------------------------------------------------
CREATE UNIQUE INDEX locations_id_idx
ON locations (id);

CREATE UNIQUE INDEX schools_id_idx
ON schools (id);

CREATE UNIQUE INDEX universities_id_idx
ON universities (id);

CREATE UNIQUE INDEX users_id_idx
ON users (id);

CREATE UNIQUE INDEX public_messages_id_idx
ON public_messages (id);

CREATE UNIQUE INDEX comments_id_idx
ON comments (id);

CREATE UNIQUE INDEX messages_id_idx
ON messages (id);

CREATE UNIQUE INDEX friendship_requests_id_idx
ON friendship_requests (id);

CREATE UNIQUE INDEX communities_id_idx
ON communities (id);

CREATE UNIQUE INDEX posts_id_idx
ON posts (id);

CREATE UNIQUE INDEX post_comments_id_idx
ON post_comments (id);

CREATE UNIQUE INDEX sun_calendars_id_idx
ON sun_calendars (id);

COMMIT;