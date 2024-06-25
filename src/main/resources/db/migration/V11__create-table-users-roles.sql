CREATE TABLE users_roles(
   user_id bigint not null,
   role_id bigint not null,
CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES usuarios (id),
CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES roles (id));