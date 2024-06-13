CREATE TABLE ordemservico(
id bigint not null primary key,
defeito varchar(255),
descricao varchar(255),
laudo_tecnico varchar(255),
status varchar(15),
data_inicial date NOT NULL,
data_final date NOT NULL,
cliente_id int NOT NULL,
CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES clientes (id));