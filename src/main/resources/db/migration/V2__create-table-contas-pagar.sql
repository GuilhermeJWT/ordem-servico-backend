create table tbl_contas_pagar (
id bigint not null,
data_emissao timestamp(6),
data_vencimento date not null,
forma_pagamento varchar(35),
observacao varchar(255),
status_conta_pagar varchar(35) not null,
valor numeric(38,2) not null,
id_fornecedor bigint,
primary key (id));