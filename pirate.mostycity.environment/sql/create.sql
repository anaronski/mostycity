drop database if exists mostycity;
create database mostycity;
use mostycity;

create table sex (
sex_id bigint not null AUTO_INCREMENT PRIMARY KEY,
name varchar (20)
);
  
create table account_type (
account_type_id bigint not null AUTO_INCREMENT PRIMARY KEY,
name varchar (20)
);

create table account_status (
account_status_id bigint not null AUTO_INCREMENT PRIMARY KEY,
name varchar (10)
);

create table news_item_status (
news_item_status_id bigint not null AUTO_INCREMENT PRIMARY KEY,
name varchar (10)
);

create table account (
account_id bigint not null AUTO_INCREMENT PRIMARY KEY,
first_name varchar (50),
last_name varchar (50),
account_type_id bigint not null,
account_status_id bigint not null,
create_ts timestamp not null,
last_login_ts timestamp not null,
last_update_acc_id long not null,
CONSTRAINT FK_ACCOUNT_ACCOUNT_TYPE FOREIGN KEY (account_type_id)
         REFERENCES account_type (account_type_id),
CONSTRAINT FK_ACCOUNT_ACCOUNT_STATUS FOREIGN KEY (account_status_id)
         REFERENCES account_status (account_status_id)
);

create table account_info (
account_id bigint not null primary key,
birthday_ts date,
sex_id bigint not null,
email varchar(50) not null,
skype varchar(50),
isq varchar(20),
aim varchar(50),
vkontakte varchar(80),
avatar MEDIUMBLOB,
CONSTRAINT FK_ACCOUNT_INFO_ACCOUNT FOREIGN KEY (account_id)
         REFERENCES account (account_id)
);

create table user_auth (
account_id bigint primary key,
login varchar(50) not null,
password varchar(50) not null,
CONSTRAINT FK_USER_AUTH_ACCOUNT FOREIGN KEY (account_id)
         REFERENCES account (account_id)
);

create table news_item(
news_item_id bigint not null primary key auto_increment,
account_id bigint not null,
create_ts timestamp not null,
viewed_count bigint,
news_item_status_id bigint not null,
last_mod_account_id bigint not null,
is_main_flag boolean not null,
news_item_title varchar(1000) not null,
news_item_desc varchar(4000) not null,
image_path varchar(1000),
CONSTRAINT FK_NEWS_ITEM_ACCOUNT FOREIGN KEY (account_id)
         REFERENCES account (account_id),
CONSTRAINT FK_NEWS_ITEM_NEWS_ITEM_STATUS FOREIGN KEY (news_item_status_id)
         REFERENCES news_item_status (news_item_status_id),
CONSTRAINT FK_LAST_MOD_NEW_ACCOUNT_ID_ACCOUNT FOREIGN KEY (last_mod_account_id)
         REFERENCES account (account_id)
);


create table comment_item(
comment_item_id bigint not null primary key auto_increment,
account_id bigint not null,
news_item_id bigint not null,
create_ts timestamp not null,
comment_txt varchar(5000),
active_flag boolean not null,
last_mod_account_id bigint not null,
CONSTRAINT FK_COMMENT_ITEM_NEWS_ITEM FOREIGN KEY (news_item_id)
         REFERENCES news_item (news_item_id),
CONSTRAINT FK_COMMENT_ITEM_ACCOUNT FOREIGN KEY (account_id)
         REFERENCES account (account_id),
CONSTRAINT FK_LAST_MOD_COMMENT_ACCOUNT_ID_ACCOUNT FOREIGN KEY (last_mod_account_id)
         REFERENCES account (account_id)
);

create table messages(
message_id bigint not null AUTO_INCREMENT PRIMARY KEY,
from_account_id bigint not null,
to_account_id bigint not null,
message_txt varchar(5000),
create_ts timestamp not null,
new_flag boolean not null,
delete_account_id bigint,
CONSTRAINT FK_MESSAGE_TO_ACCOUNT_ACCOUNT FOREIGN KEY (to_account_id)
         REFERENCES account (account_id),
CONSTRAINT FK_MESSAGE_FROM_ACCOUNT_ACCOUNT FOREIGN KEY (from_account_id)
         REFERENCES account (account_id)
);


create table votings(
voting_id bigint not null PRIMARY KEY auto_increment,
voting_name varchar(500) not null,
active_flag boolean not null,
account_id bigint not null,
create_ts timestamp not null,
answers_count bigint not null,
variants_count int not null,
CONSTRAINT FK_VOTING_ACCOUNT FOREIGN KEY (account_id)
         REFERENCES account (account_id)
);

create table voting_variants(
voting_variant_id bigint not null PRIMARY KEY auto_increment,
voting_id bigint not null,
variant_name varchar(500) not null,
answers_count bigint not null,
CONSTRAINT FK_VOTING_VARIANT_VOTING FOREIGN KEY (voting_id)
         REFERENCES votings (voting_id)
);