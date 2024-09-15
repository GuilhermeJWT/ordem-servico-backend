create table tbl_usuarios (
id bigint not null,
email varchar(255) NOT NULL UNIQUE,
senha varchar(255) NOT NULL,
primary key (id))