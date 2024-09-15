create table roles (
id bigint not null,
name varchar(255) check (name in ('ROLE_ADMIN','ROLE_TECNICO','ROLE_USUARIO_COMUM','ROLE_VENDEDOR')),
primary key (id))