CREATE TABLE clientes(
    id bigint not null primary key,
    nome varchar(100) NOT NULL,
    cpf varchar(16),
    celular varchar(20),
    email varchar(100),
    endereco varchar(100),
    cidade varchar(30),
    estado varchar(20),
    cep varchar(20)
);