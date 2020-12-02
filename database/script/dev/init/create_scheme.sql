DROP DATABASE IF EXISTS hrinkov_social_network;

CREATE SCHEMA IF NOT EXISTS hrinkov_social_network DEFAULT CHARACTER SET UTF8MB4 ;

USE hrinkov_social_network;

-- -----------------------------------------------------
-- Table `locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS locations (
  id INT NOT NULL AUTO_INCREMENT,
  country VARCHAR(45) NOT NULL,
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
  email VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(256) NOT NULL,
  role VARCHAR(20) NOT NULL,
  PRIMARY KEY pk_users (id)
);

-- -----------------------------------------------------
-- Table `user_profile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_profiles (
  id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL UNIQUE,
  registration_date DATETIME NOT NULL,
  date_of_birth DATETIME NULL,
  name VARCHAR(45) NULL,
  surname VARCHAR(45) NULL,
  telephone_number VARCHAR(20) NULL,
  location_id INT NULL,
  school_id INT NULL,
  school_graduation_year INT NULL,
  university_id INT NULL,
  university_graduation_year INT NULL,
  PRIMARY KEY pk_user_profiles (id)
);

-- -----------------------------------------------------
-- Table `tokens`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tokens (
  id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  value VARCHAR(256) NOT NULL,
  creation_date DATETIME NOT NULL,
  PRIMARY KEY pk_tokens (id)
);

-- -----------------------------------------------------
-- Table `public_messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public_messages (
  id INT NOT NULL AUTO_INCREMENT,
  creation_date DATETIME NOT NULL,
  author_id INT NOT NULL,
  tittle VARCHAR(1000) NULL,
  content VARCHAR(8000) NULL,
  is_deleted BOOLEAN DEFAULT false,
  PRIMARY KEY pk_public_messages (id)
);

-- -----------------------------------------------------
-- Table `public_message_comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS public_message_comments (
  id INT NOT NULL AUTO_INCREMENT,
  creation_date DATETIME NOT NULL,
  author_id INT NOT NULL,
  public_message_id INT NOT NULL,
  content VARCHAR(8000) NULL,
  is_deleted BOOLEAN DEFAULT false,
  PRIMARY KEY pk_public_message_comments (id)
);

-- -----------------------------------------------------
-- Table `private_messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS private_messages (
  id INT NOT NULL AUTO_INCREMENT,
  departure_date DATETIME NOT NULL,
  sender_id INT NOT NULL,
  recipient_id INT NOT NULL,
  content VARCHAR(8000) NULL,
  is_read BOOLEAN DEFAULT false,
  is_deleted BOOLEAN DEFAULT false,
  PRIMARY KEY pk_messages (id)
);

-- -----------------------------------------------------
-- Table `communities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS communities (
  id INT NOT NULL AUTO_INCREMENT,
  creation_date DATETIME NOT NULL,
  author_id INT NOT NULL,
  type VARCHAR(20) NOT NULL,
  tittle VARCHAR(100) NOT NULL,
  information VARCHAR(1000) NULL,
  is_deleted BOOLEAN DEFAULT false,
  PRIMARY KEY pk_communities (id)
);

-- -----------------------------------------------------
-- Table `posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS posts (
  id INT NOT NULL AUTO_INCREMENT,
  creation_date DATETIME NOT NULL,
  tittle VARCHAR(1000) NULL,
  content VARCHAR(8000) NULL,
  communities_id INT NOT NULL,
  is_deleted BOOLEAN DEFAULT false,
  PRIMARY KEY pk_posts (id)
);

-- -----------------------------------------------------
-- Table `post_comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS post_comments (
  id INT NOT NULL AUTO_INCREMENT,
  creation_date DATETIME NOT NULL,
  author_id INT NOT NULL,
  post_id INT NOT NULL,
  content VARCHAR(1000) NULL,
  is_deleted BOOLEAN DEFAULT false,
  PRIMARY KEY pk_post_comments (id)
);

-- -----------------------------------------------------
-- Table `friendship_requests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS weather_conditions (
  id INT NOT NULL AUTO_INCREMENT,
  location_id INT NULL,
  status VARCHAR(20) NULL,
  registration_date DATETIME NOT NULL,
  PRIMARY KEY pk_weather_conditions (id)
);

-- -----------------------------------------------------
-- Table `friendship_requests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS friendship_requests (
  user_profiles_id INT NOT NULL,
  friend_id INT NOT NULL
);

-- -----------------------------------------------------
-- Table `friends`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS friends (
  user_profiles_id INT NOT NULL,
  friend_id INT NOT NULL
);

-- -----------------------------------------------------
-- Table `community_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS community_user (
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

ALTER TABLE user_profiles
ADD CONSTRAINT fk_user_profiles_users
FOREIGN KEY (user_id)
REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE user_profiles
ADD CONSTRAINT fk_user_profiles_locations
FOREIGN KEY (location_id)
REFERENCES locations (id) ON DELETE CASCADE;

ALTER TABLE user_profiles
ADD CONSTRAINT fk_user_profiles_schools
FOREIGN KEY (school_id)
REFERENCES schools (id) ON DELETE CASCADE;

ALTER TABLE user_profiles
ADD CONSTRAINT fk_user_profiles_universities
FOREIGN KEY (university_id)
REFERENCES universities (id) ON DELETE CASCADE;

ALTER TABLE tokens
ADD CONSTRAINT fk_tokens_users
FOREIGN KEY (user_id)
REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE public_messages
ADD CONSTRAINT fk_public_messages
FOREIGN KEY (author_id)
REFERENCES user_profiles (id) ON DELETE CASCADE;

ALTER TABLE public_message_comments
ADD CONSTRAINT fk_public_message_comments_users
FOREIGN KEY (author_id)
REFERENCES user_profiles (id) ON DELETE CASCADE;

ALTER TABLE public_message_comments
ADD CONSTRAINT fk_public_message_comments_public_messages
FOREIGN KEY (public_message_id)
REFERENCES public_messages (id) ON DELETE CASCADE;

ALTER TABLE private_messages
ADD CONSTRAINT fk_private_messages_users_sender
FOREIGN KEY (sender_id)
REFERENCES user_profiles (id) ON DELETE CASCADE;

ALTER TABLE private_messages
ADD CONSTRAINT fk_private_messages_users_recipient
FOREIGN KEY (recipient_id)
REFERENCES user_profiles (id) ON DELETE CASCADE;

ALTER TABLE friendship_requests
ADD CONSTRAINT fk_friendship_requests_users_user
FOREIGN KEY (user_profiles_id)
REFERENCES user_profiles (id) ON DELETE CASCADE;

ALTER TABLE friendship_requests
ADD CONSTRAINT fk_friendship_requests_users_friend
FOREIGN KEY (friend_id)
REFERENCES user_profiles (id) ON DELETE CASCADE;

ALTER TABLE communities
ADD CONSTRAINT fk_communities_users
FOREIGN KEY (author_id)
REFERENCES user_profiles (id) ON DELETE CASCADE;

ALTER TABLE posts
ADD CONSTRAINT fk_posts_communities
FOREIGN KEY (communities_id)
REFERENCES communities (id) ON DELETE CASCADE;

ALTER TABLE post_comments
ADD CONSTRAINT fk_post_comments_users
FOREIGN KEY (author_id)
REFERENCES user_profiles (id) ON DELETE CASCADE;

ALTER TABLE post_comments
ADD CONSTRAINT fk_post_comments_posts
FOREIGN KEY (post_id)
REFERENCES posts (id) ON DELETE CASCADE;

ALTER TABLE weather_conditions
ADD CONSTRAINT fk_weather_conditions_locations
FOREIGN KEY (location_id)
REFERENCES locations (id) ON DELETE CASCADE;

ALTER TABLE friends
ADD CONSTRAINT fk_friends_users_user
FOREIGN KEY (user_profiles_id)
REFERENCES user_profiles (id) ON DELETE CASCADE;

ALTER TABLE friends
ADD CONSTRAINT fk_friends_users_friend
FOREIGN KEY (friend_id)
REFERENCES user_profiles (id) ON DELETE CASCADE;

ALTER TABLE community_user
ADD CONSTRAINT fk_community_user_communities
FOREIGN KEY (communities_id)
REFERENCES communities (id) ON DELETE CASCADE;

ALTER TABLE community_user
ADD CONSTRAINT fk_community_user_users
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

CREATE UNIQUE INDEX user_profiles_id_idx
ON user_profiles (id);

CREATE UNIQUE INDEX tokens_id_idx
ON tokens (id);

CREATE UNIQUE INDEX public_messages_id_idx
ON public_messages (id);

CREATE UNIQUE INDEX public_message_comments_id_idx
ON public_message_comments (id);

CREATE UNIQUE INDEX private_messages_id_idx
ON private_messages (id);

CREATE UNIQUE INDEX communities_id_idx
ON communities (id);

CREATE UNIQUE INDEX posts_id_idx
ON posts (id);

CREATE UNIQUE INDEX post_comments_id_idx
ON post_comments (id);

CREATE UNIQUE INDEX weather_conditions_id_idx
ON weather_conditions (id);

COMMIT;