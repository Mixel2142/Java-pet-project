CREATE TABLE auth_data(
   user_id UUID PRIMARY KEY NOT NULL,
   login varchar(250) UNIQUE NOT NULL ,
   password varchar(250) NOT NULL,
   roles varchar(250) NOT NULL,
   credentials varchar(250),
   access_token varchar(2040) NOT NULL,
   refresh_token varchar(2040) NOT NULL
   );