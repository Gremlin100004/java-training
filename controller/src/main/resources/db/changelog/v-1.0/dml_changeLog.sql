INSERT INTO locations (country, city) VALUES
('Belarus', 'Minsk'),
('Belarus', 'Gomel'),
('Belarus', 'Vitebsk'),
('Belarus', 'Grodno'),
('Belarus', 'Brest'),
('Belarus', 'Bobruisk'),
('Belarus', 'Baranovichi');

INSERT INTO schools (name, location_id) VALUES
('Secondary school number 104', 1),
('Secondary school number 95', 1),
('Secondary school number 21', 2),
('Secondary school number 69', 2),
('Secondary school number 2', 3),
('Secondary school number 4', 3),
('Secondary school number 38', 4),
('Secondary school number 31', 4),
('Secondary school number 32', 4),
('Secondary school number 8', 5),
('Secondary school number 9', 5),
('Secondary school number 1', 6),
('Secondary school number 3', 6),
('Secondary school number 12', 7),
('Secondary school number 22', 7);

INSERT INTO universities (name, location_id) VALUES
('Academy of Management under the President of the Republic of Belarus', 1),
('Belarusian State University', 1),
('Belarusian State University of Transport', 2),
('Gomel State Medical University', 2),
('Vitebsk State University named after P.M. Masherova', 3),
('Vitebsk State Technological University', 3),
('Grodno State Agrarian University', 4),
('Grodno State Medical University', 4),
('Baranovichi State University', 5),
('Brest State Technical University', 5);

INSERT INTO users (email, password, role) VALUES
('user1@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_USER'),
('user2@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_USER'),
('user3@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_USER'),
('user4@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_USER'),
('admin@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_ADMIN');

INSERT INTO user_profiles (user_id, registration_date, date_of_birth, name, surname, telephone_number, location_id, school_id, school_graduation_year, university_id, university_graduation_year) VALUES
(1, '2020-07-11 10:00', '1990-07-28', 'Petya', 'Buhmetovich', '+375(29)766-54-23', 1, 1, 2007, 1, 2013),
(2, '2020-08-11 10:00', '2004-11-12', 'Artem', 'Karchevskiy', '+375(29)467-34-62', 2, 3, 2007, 3, 2013),
(3, '2020-09-11 10:00', '1996-03-21', 'Kiril', 'Myagkov', '+375(29)123-41-67', 3, 5, 2007, 4, 2013),
(4, '2019-02-05 09:53', '2001-03-11', 'Vasya', 'Potryopovich', '+375(33)567-34-09', 4, 7, 2010,5, 2015);

INSERT INTO public_messages (creation_date, author_id, title, content, is_deleted) VALUES
('2020-07-21 10:00', 1, 'I deleted everything.', 'I deleted everything. I’m done. For those who wanted me to “address it” I did. I’m sure u can find it reposted somewhere. But I don’t want this energy in my life or on my timeline. I’m too sensitive for this shit and I’m done.', false),
('2020-08-04 10:00', 1, 'CONSPIRACY RELAUNCH IS LIVE!! :,))))', 'https://jeffreestarcosmetics.com/collections/shane-x-jeffree-conspiracy-collection', false),
('2020-08-25 10:00', 2, 'I don’t usually upload my TikToks to Twitter, but idk this one feels like it might fit...', null, false),
('2020-09-18 10:00', 2, 'The Coronavirus is very much under control', 'The Coronavirus is very much under control in the USA. We are in contact with everyone and all relevant countries. CDC & World Health have been working hard and very smart. Stock Market starting to look very good to me!', false),
('2020-10-13 10:00', 3, 'Keep an eye on my vlogging channel tomorrow at 6pm for a reveal and something to do with something that rhymes with ''Rook Pour''', null, false),
('2020-08-12 10:00', 3, 'Good morning everyone!', 'OMG IT''S #SUGGSUNDAY #SUGGSUNDAY #SUGGSUNDAY #SUGGSUNDAY #SUGGSUNDAY #SUGGSUNDAY #SUGGSUNDAY #SUGGSUNDAY #SUGGSUNDAY', false),
('2020-09-16 10:00', 4, 'Something extremely bogus is going on.', 'Was tested for covid four times today. Two tests came back negative, two came back positive. Same machine, same test, same nurse. Rapid antigen test from BD.', false),
('2020-07-18 10:00', 4, 'It''s just a shout out but for a good cause promoting social inclusion, job opportunities and leadership development for children and adults with intellectual and developmental disabilities (IDD). Check it!', 'The Best Buddies Friendship Jam Silent Auction just went live! https://e.givesmart.com/events/iCc/i/', false);

INSERT INTO public_message_comments (creation_date, author_id, public_message_id, content, is_deleted) VALUES
('2020-07-21 11:23', 1, 1, 'I know I’m a couple months late but everyone saying Shane the same person he was when he started YouTube, can you see his transformed into this caring youtuber, yeah he made funny sketches which may offend some people but at the time he was posting it to make people laugh', false),
('2020-07-21 11:45', 4, 1, 'Shane was my influencer. The dark comedy and all the jokes man. It was something I had fun with in school and helped evolve me into a funnier gay. My family thought he was hilarious.', false),
('2020-08-04 07:45', 3, 2, 'Ok pedo.', false),
('2020-08-04 08:56', 4, 2, 'I still love you Shane. I know that your a good person and would never intentionally try to hurt ANYONE!', false),
('2020-08-25 09:23', 1, 3, 'FAKE AND GAY', false),
('2020-08-25 09:57', 4, 3, 'Why did you sell your youtube channel though', false),
('2020-09-18 06:01', 3, 4, 'Too late! While you were busy in getting your ego stroked in India, the stock market took a nose dive.', false),
('2020-09-18 07:01', 1, 4, 'Why did you cut the CDC budget? Are you hoping for a pandemic?', false),
('2020-10-13 03:02', 4, 5, 'cook four?', false),
('2020-10-13 04:34', 1, 5, 'nailed it', false),
('2020-08-12 06:32', 2, 6, 'please follow me!!!!!!!!!!!!!!!!!!!!!:{:{:{:{:{:::):):):):):):', false),
('2020-08-12 10:54', 1, 6, 'he is real don''t diss', false),
('2020-09-16 02:10', 3, 7, 'Most likely scenario is that you were very recently infected with SARS-CoV-2, so that not all swabs are testing positive yet. Hit me up if you want to talk about treatment options.', false),
('2020-09-16 04:20', 2, 7, 'It’s called science Elon. Even HIV tests aren’t 100% accurate. These are brand new. There’s no bogus. Don’t feed the stupid people with more conspiracy theories.', false),
('2020-07-18 07:23', 1, 8, 'Nice blue hat Ryan.', false),
('2020-07-18 08:34', 2, 8, 'do I drop the money or no...', false);

INSERT INTO private_messages (departure_date, sender_id, recipient_id, content, is_read, is_deleted) VALUES
('2020-11-11 12:00', 2, 1, 'Your great aunt just passed away. LOL', true, false),
('2020-11-11 12:01', 1, 2, 'Why is that funny?', false, false),
('2020-11-11 12:03', 2, 1, 'it''s not funny! What do ypu mean&', false, false),
('2020-11-11 12:04', 1, 2, 'Lol means laughing out loud!!!', false, false),
('2020-11-11 12:05', 2, 1, 'Oh my goodness!! I sent that to everyone I thought it meant lots of love. I have tocall rveryone back oh god', false, false),
('2020-11-01 10:01', 1, 3, 'What does IDK? LY & TTYL mean&?', true, false),
('2020-11-01 12:02', 3, 1, 'I don''t know, talk to you later', true, false),
('2020-11-01 12:03', 1, 3, 'OK, i will ask your sister', true, false),
('2020-11-01 12:04', 3, 1, 'Well that''s fantastic', true, false),
('2020-10-28 07:01', 2, 4, 'I''m working in my university July( But in August I will go to the gradma... How are ypur family?', true, false),
('2020-10-28 07:02', 4, 2, 'Im great!! Working, sending time with friends.. waiting for university to start again', true, false),
('2020-10-28 07:03', 4, 2, 'Everyone is okay!! We all miss you', true, false),
('2020-10-28 07:04', 4, 2, 'How is your family??', true, false),
('2020-10-28 07:05', 2, 4, 'They are good) I really glad to hear from you! but i must go to sleep, because i get up very early. good night sweety!', true, false);

INSERT INTO communities (creation_date, author_id, type, title, information, is_deleted) VALUES
('2020-10-01 12:00', 1, 'MEDICINE', 'COVID-19: Updates for the US', 'This page is a timeline of Tweets with the latest information and advice from the CDC, HHS, NIH and public health authorities across the country. For more, visit https://https://cdc.gov/coronavirus.', false),
('2020-10-02 12:00', 2, 'SPORT', 'BBC Sport', 'Official http://bbc.co.uk/sport account. Also follow @bbcmotd and @bbctms.', false),
('2020-10-03 12:00', 3, 'AUTO', 'McLaren Automotive', 'Born and raised on the track, we use racing technology and expertise to create the most advanced performance cars in the world.', false),
('2020-10-04 12:00', 4, 'MUSIC', 'Lindsey Stirling', 'Fan community of violinist Lindsey Stirling.', false);

INSERT INTO posts (creation_date, title, content, communities_id, is_deleted) VALUES
('2020-10-01 20:11', 'El Paso County sees drop in COVID-19 hospitalizations', 'COVID hospitalizations in El Paso have been going down for about 2 weeks now. The number of people in El Paso testing positive for COVID has been declining also. The positivity rate for all of Texas has declined for 14 consecutive days.', 1, false),
('2020-10-02 12:11', '#COVID19 affects everyone differently', 'Cases and hospitalizations are rising, with adults ages 65+ much more likely to be hospitalized. Protect your loved ones to make sure they’re healthy for upcoming holidays and the new year. Learn more: https://bit.ly/2HwJo2q', 1, false),
('2020-10-03 06:34', null, 'Since one or more #COVID19 vaccines may be available in limited supply before the end of the year, CDC is working closely with health departments and partners to get ready for when vaccines are available. Learn more: https://bit.ly/3ppRU4H.', 1, false),
('2020-10-04 11:11', 'COVID fatigue is real.', 'COVID fatigue is real. COVID amnesia is deadly. Don’t forget where we were.', 1, false),
('2020-10-05 11:34', 'When Lineker Met Maradona', 'At 22:00, we''ll be showing our documentary with @GaryLineker from 2006. @GaryLineker talks to Diego Maradona about football, genius and *that* 1986 World Cup quarter-final.', 2, false),
('2020-10-06 06:11', 'Great news!', 'Snooker will welcome back spectators at the Masters in January. Read all about it: https://bbc.in/3qeSXoH', 2, false),
('2020-10-07 09:43', 'ST HELENS HAVE DONE IT IN DRAMATIC FASHION!', 'Right at the death, tied at 4-4, a long drop-goal attempt hits the post and Jack Welsby chases it down to get the try as the buzzer goes! Wigan Warriors 4-8 St Helens. Incredible drama! Follow LIVE: http://bbc.in/2JcRnCM', 2, false),
('2020-10-08 04:32', 'FT Crystal Palace 0-2 Newcastle', 'Two late goals for Callum Wilson and Joelinton earn the three points for the Magpies. #CRYNEW live: http://bbc.in/3q4tfCZ', 2, false),
('2020-10-09 03:12', 'It''s #WallpaperWednesday time. This week we have some new images of the #McLarenGT in Japan. #GTadventures ', 'content', 3, false),
('2020-10-10 01:52', 'Artura is the purest distillation of everything that we have done to date. We poured every drop of our expertise in super-light engineering, extreme power, electrification and race-honed agility into its DNA to deliver a uniquely intense McLaren experience. #Artura', null, 3, false),
('2020-10-11 05:23', 'How the McLaren Sennas active aerodynamics works', 'The McLaren Senna is capable of generating an incredible 800kg of downforce. How? Via a cutting edge active aerodynamics system. Join Senior Engineer Stephen Paine for an in-depth look at how the system works.', 3, false),
('2020-10-12 02:15', 'In the McLaren 765LT our longtail DNA is woven into everything. How? ', 'New carbon fibre body panels, motorsport-inspired polycarbonate glazing, a unique dramatic titanium exhaust system and not to mention light dual-spring suspension. #765LT', 3, false),
('2020-10-13 06:42', 'Home for the Holidays Special', 'Join me for my Home for the Holidays Special where all your favorite seasonal tunes will be brought to life by stylized sets and glittering costumes, with the best view right from your home. Tickets on sale tomorrow 10/29 at 9am PT! Ticket options here: https://found.ee/LS_HomeForTheHolidays', 4, false),
('2020-10-14 02:48', 'So excited to share that the song @dariusrucker and I will perform on #CMAChristmas is now streaming!', 'Listen to #WhatChildIsThis here: https://found.ee/LS_WhatChildIsThis', 4, false),
('2020-10-15 11:58', 'New merch just in time for my Home for the Holidays Special! Use code ''HOMEFORTHEHOLIDAYS'' for 15% off your order and add a plush blanket for only $30 when you spend $75!', 'Check out all the new items here: https://found.ee/LSmerch', 4, false),
('2020-10-16 03:23', 'Stay In Touch With The People Who Matter Most To You', 'Gratitude post number 4: I’m grateful for the @marcopoloapp. I’m grateful that my friends and family can have continuous group conversations to stay connected even through distance. It’s really helped my mental health. #givethanks', 4, false);

INSERT INTO post_comments (creation_date, author_id, post_id, content, is_deleted) VALUES
('2020-10-02 09:23', 1, 1, 'Do lockdowns, we need them.', false),
('2020-10-02 11:12', 3, 1, 'No thanks to you...  I''ll keep thanking the people aiding the sick and those developing novel therapies for covid.', false),
('2020-10-03 10:23', 4, 2, 'It makes no sense that we cannot visit a loved one that is fighting for their life, whom we may never see again, BUT we allow people to pack in restaurants and bars.  This rule is wrong on so many levels!', false),
('2020-10-03 11:23', 3, 2, 'People are math challenged and hardwired for FEAR. My age group: 50-64 is HOSPITALIZED at a rate of 15/100,000 which is less than 2/100ths of 1%..which is to say 0.00015% In context, the cancer DEATH rate is 158.3/100,000. Should we quiver in fear of cancer, or live our lives?', false),
('2020-10-04 07:23', 2, 3, 'Will Walmart have pre-orders like they did on the PS5s?  Will the UK scalper bots get them all?', false),
('2020-10-04 08:23', 4, 3, 'We need rapid at home testing! Over the counter!', false),
('2020-10-05 01:23', 4, 4, 'I''m tired of stupid and uncaring people who refuse to stay home and wear  a mask when they must go out. That''s what I''m tired of.', false),
('2020-10-05 02:23', 2, 4, 'Perhaps they can turn churches into temporary covid treatment centers or morgues.', false),
('2020-10-06 04:23', 1, 5, 'They have the same nose, Diego’s probably a little more used.', false),
('2020-10-06 05:23', 3, 5, 'Just watched it on YouTube, really good, once @GaryLineker finally gets him sat down Face with tears of joyZany face', false),
('2020-10-07 09:23', 1, 6, 'I''ll be there with my 9 flatmates!', false),
('2020-10-07 10:23', 4, 6, 'So will I', false),
('2020-10-08 02:23', 1, 7, 'Touch judge wins the Harry Sunderland Trophy for his match winning turn for St Helens.', false),
('2020-10-08 03:23', 3, 7, 'Crazy, sport can be entertaining but also very cruel.', false),
('2020-10-09 05:23', 4, 8, 'Imagine being so bad Joelinton scores against you Face with tears of joy', false),
('2020-10-09 06:23', 1, 8, 'I don''t have to imagine.', false),
('2020-10-10 03:23', 2, 9, 'The fascinating Japanese architecture of the past with the British automotive elegance and refinement of the present. Past and present in a beautiful photo. 2 very special design approache', false),
('2020-10-10 04:23', 1, 9, 'I wish this were taken as a panoramic shot, or just in landscape mode.Slightly smiling face very beautiful, of course. Thanks!', false),
('2020-10-11 09:23', 1, 10, 'any hints as to what it is?', false),
('2020-10-11 10:23', 4, 10, 'Graphics card. Bold move for McLaren to take on Nvidia.', false),
('2020-10-12 10:23', 2, 11, 'Clearly explained', false),
('2020-10-12 11:23', 4, 11, 'Awesome', false),
('2020-10-13 01:23', 1, 12, '"Long tail" ... meer inches longer than the 720s.', false),
('2020-10-13 04:23', 2, 12, 'The first couple of shots looked like a forzavista cinematic look around and made me think this was in horizon 4. Really hope it comes to that game', false),
('2020-10-14 03:23', 3, 13, 'I honestly would grab tickets but being in Australia the times aren''t in my favour one bit . I''d be asleep for the first show and then at work during the second ', false),
('2020-10-14 09:23', 1, 13, 'hello, Lind when he comes to play for us here in Brazil, I want to see you live dancing and letting you see on the violin until the strings explode', false),
('2020-10-15 01:23', 2, 14, 'This sounds absolutely lovely! Looking forward to listening to it/see you play on #CMAChristmas', false),
('2020-10-15 10:23', 3, 14, 'It was an excellent and very beautiful song ', false),
('2020-10-16 03:23', 2, 15, 'I got the violin hat t shirt and the fireplace t shirt! Thanks Lindsey!', false),
('2020-10-16 06:23', 1, 15, 'I love the blankets. I have the red and blue from the last two years. Time to do a little shopping for some friends.', false),
('2020-10-17 06:23', 2, 16, 'Thank you for sharing this app and for the last live stream, it was so much fun and sweet', false),
('2020-10-17 11:23', 3, 16, 'I''m happy to hear you''re able to stay connected with your friends and family. That app is pretty cool.', false);

INSERT INTO friends VALUES
(1, 3), (1, 4), (2, 4);

INSERT INTO friendship_requests VALUES
(1, 2);

INSERT INTO community_user VALUES
(1, 2), (1, 3), (1, 4), (2, 1), (2, 4), (3, 4), (4, 3), (4, 2);

ALTER SEQUENCE communities_id_seq RESTART WITH 42;

COMMIT;