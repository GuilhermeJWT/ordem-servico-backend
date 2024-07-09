create table vendas (
    id_venda bigint not null,
    data_venda timestamp(6),
    desconto numeric(38,2),
    total_itens integer,
    total_venda numeric(38,2) not null,
    id_cliente bigint,
    id_tecnico_responsavel bigint,
    primary key (id_venda))