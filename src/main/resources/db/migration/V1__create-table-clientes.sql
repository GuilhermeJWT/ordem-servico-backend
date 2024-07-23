create table tbl_clientes (
id bigint not null,
nome varchar(150),
celular varchar(25),
cpf varchar(20),
email varchar(100),
cep varchar(20),
cidade varchar(100),
complemento varchar(255),
endereco varchar(150),
estado varchar(50),
primary key (id));