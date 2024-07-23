create table tbl_ordemservico (
id bigint not null,
data_final date,
data_inicial date,
defeito varchar(255),
descricao varchar(255),
laudo_tecnico varchar(255),
status varchar(30),
cliente_id bigint,
tecnico_id bigint,
primary key (id));