CREATE TABLE file_storage(
   file_id UUID PRIMARY KEY,
   article_id UUID UNIQUE,
   user_id UUID UNIQUE,
   expiry_date TIMESTAMP,
   created_on TIMESTAMP,
   сontent_type varchar(20),
   size INTEGER ,
   data BYTEA
   );