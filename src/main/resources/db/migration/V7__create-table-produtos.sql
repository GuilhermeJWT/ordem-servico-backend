create table tbl_produtos (
id bigint not null,
codigo_barras varchar(30),
descricao varchar(255),
preco_compra numeric(38,2),
preco_venda numeric(38,2),
quantidade integer,
quantidade_minima integer,
primary key (id));