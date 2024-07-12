CREATE TABLE produtos(
     id bigint not null primary key,
     descricao varchar(255) NOT NULL,
     quantidade int NOT NULL,
     quantidade_minima int,
     preco_compra numeric,
     preco_venda numeric,
     codigo_barras varchar(255)
);