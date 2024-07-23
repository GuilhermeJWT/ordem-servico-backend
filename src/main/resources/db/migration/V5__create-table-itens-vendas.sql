create table tbl_itens_vendas (
id_itens_venda bigint not null,
produto bigint array,
quantidade integer array,
valor_produto numeric(38,2) array,
primary key (id_itens_venda));