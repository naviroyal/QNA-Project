
INSERT INTO users(id, uuid, firstname, lastname, username, email, password, salt, country, aboutme, dob, role, number)
    	VALUES (1001,'uuid','firstname','lastname','username','email','password','salt', 'country' ,'aboutme' ,'dob' , 'admin' , 'number' );
INSERT INTO users(id, uuid, firstname, lastname, username, email, password, salt, country, aboutme, dob, role, number)
     VALUES (1002,'uuid1','firstname1','lastname1','username1','email1','password1','salt1', 'country1' ,'aboutme1' ,'dob1' , 'notadmin' , 'number1' );
INSERT INTO users(id, uuid, firstname, lastname, username, email, password, salt, country, aboutme, dob, role, number)
    VALUES (1003,'uuid2','firstname2','lastname2','username2','email2','password2','salt2', 'country2' ,'aboutme2' ,'dob2' , 'notadmin' , 'number2' );
INSERT INTO users(id, uuid, firstname, lastname, username, email, password, salt, country, aboutme, dob, role, number)
    VALUES (1004,'uuid3','firstname3','lastname3','username3','email3','password3','salt3', 'country3' ,'aboutme3' ,'dob3' , 'notadmin' , 'number3' );
INSERT INTO users(id, uuid, firstname, lastname, username, email, password, salt, country, aboutme, dob, role, number)
    VALUES (1005,'uuid4','firstname4','lastname4','username4','email4','password4','salt4', 'country4' ,'aboutme4' ,'dob4' , 'notadmin' , 'number4' );


insert into user_auth (id , uuid , user_id , access_token , expires_at , login_at, logout_at) values(1000 , 'uuid' , 1001 , 'accesstoken' , '2019-10-19 18:10:05.02' , '2019-10-19 13:05:07.02' , null);
insert into user_auth (id , uuid , user_id , access_token , expires_at , login_at , logout_at) values(1001 , 'uuid1' , 1002 , 'accesstoken1' , '2019-10-19 18:10:05.02' , '2019-10-19 13:05:07.02' , null );
insert into user_auth (id , uuid , user_id , access_token , expires_at , login_at , logout_at) values(1002 , 'uuid2' , 1003 , 'accesstoken2' , '2019-10-19 18:10:05.02' , '2019-10-19 13:05:07.02' , null );
insert into user_auth (id , uuid , user_id , access_token , expires_at , login_at , logout_at) values(1003 , 'uuid3' , 1004 , 'accesstoken3' , '2019-10-19 18:10:05.02' , '2019-10-19 13:05:07.02' , '2019-10-19 13:05:07.02' );


insert into answer(id,uuid,ans,date,user_id,question_id) values (1000,'answer_uuid','my_answer','2018-09-17 19:41:19.593',1002,1000);


insert into question (id,uuid,content,date,user_id) values(1000,'database_question_uuid','database_question_content','2018-09-17 19:41:19.593',1002);
