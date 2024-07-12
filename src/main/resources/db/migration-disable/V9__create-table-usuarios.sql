CREATE TABLE usuarios(
  id BIGINT NOT NULL PRIMARY KEY,
  nome varchar(100),
  email varchar(100) UNIQUE NOT NULL,
  password varchar(255),
  endereco varchar(100),
  cidade varchar(30),
  estado varchar(20),
  cep varchar(20));