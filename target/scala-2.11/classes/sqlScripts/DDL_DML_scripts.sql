CREATE DATABASE IF NOT EXISTS samplePlayApp;

create table account_type(
role_id int(11) not null auto_increment,
account_type varchar(50) not null,
description varchar(200) not null,
status varchar(10) not null default "active",
primary key(role_id),
constraint ukAccountType unique(account_type)
);

insert into account_type (account_type, description) values ("Editor", "Writer's profile");
insert into account_type (account_type, description) values ("Viewer", "Viewer's profile");

create table user(
user_id int(10) not null auto_increment,
user_name varchar(50) not null,
email_address varchar(100) not null,
password varchar(50) not null,
role_id int(11) not null,
last_login timestamp not null,
created timestamp not null default CURRENT_TIMESTAMP,
status varchar(50) not null default "active",
reset_code varchar(50) not null default "-",
reset_flag int(1) not null default 0,
primary key(user_id),
constraint ukUser unique(user_name,email_address),
constraint fkRoleId foreign key(role_id) references account_type(role_id)
);

insert into user (user_name, email_address, password, role_id) values ("vamsi", "vamsi@ionosnetworks.com", "Dude123", 1);
insert into user (user_name, email_address, password, role_id) values ("sprokr", "sprokr@ionosnetworks.com", "Dude123", 2);
