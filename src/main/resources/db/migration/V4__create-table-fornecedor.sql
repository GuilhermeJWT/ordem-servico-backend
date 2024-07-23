create table tbl_fornecedor (
id bigint not null,
nome varchar(150),
nome_fantasia varchar(150),
cnpj varchar(20),
cep varchar(20),
cidade varchar(100),
complemento varchar(255),
endereco varchar(150),
estado varchar(50),
tipo_pessoa varchar(20),
primary key (id));