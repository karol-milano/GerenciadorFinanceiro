CREATE USER gerenciador WITH PASSWORD 'G3r3nc14d0r';

CREATE DATABASE 'GerenciadorFinanceiro' WITH TEMPLATE = template0;

ALTER DATABASE 'GerenciadorFinanceiro' OWNER TO gerenciador;

INSERT INTO cliente (id, nome, senha, usuario) VALUES (1, 'Usuario Teste', '123', 'teste');

INSERT INTO categoria (id, nome, tipo) VALUES (1, 'Alimentacao', 'd');
INSERT INTO categoria (id, nome, tipo) VALUES (2, 'Casa', 'd');
INSERT INTO categoria (id, nome, tipo) VALUES (3, 'Comunicacao', 'd');
INSERT INTO categoria (id, nome, tipo) VALUES (4, 'Despesas Pessoais', 'd');
INSERT INTO categoria (id, nome, tipo) VALUES (5, 'Educacao', 'd');
INSERT INTO categoria (id, nome, tipo) VALUES (6, 'Lazer', 'd');
INSERT INTO categoria (id, nome, tipo) VALUES (7, 'Saude', 'd');
INSERT INTO categoria (id, nome, tipo) VALUES (8, 'Transporte', 'd');
INSERT INTO categoria (id, nome, tipo) VALUES (9, 'Receitas', 'r');


INSERT INTO grupo (id, nome, categoria_id) VALUES (1, 'Feira', 1);
INSERT INTO grupo (id, nome, categoria_id) VALUES (2, 'Refeicao', 1);
INSERT INTO grupo (id, nome, categoria_id) VALUES (3, 'Mercado', 1);
INSERT INTO grupo (id, nome, categoria_id) VALUES (4, 'Aluguel', 2);
INSERT INTO grupo (id, nome, categoria_id) VALUES (5, 'Agua', 2);
INSERT INTO grupo (id, nome, categoria_id) VALUES (6, 'Energia', 2);
INSERT INTO grupo (id, nome, categoria_id) VALUES (7, 'Colegio', 5);
INSERT INTO grupo (id, nome, categoria_id) VALUES (8, 'Cursos', 5);
INSERT INTO grupo (id, nome, categoria_id) VALUES (9, 'Cinema', 6);
INSERT INTO grupo (id, nome, categoria_id) VALUES (10, 'Medico', 7);
INSERT INTO grupo (id, nome, categoria_id) VALUES (11, 'Combustivel', 8);
INSERT INTO grupo (id, nome, categoria_id) VALUES (12, 'Salario', 9);
INSERT INTO grupo (id, nome, categoria_id) VALUES (13, 'Bonificacao', 9);


INSERT INTO movimentacao (id, data, descricao, tipo, valor, categoria_id, cliente_id, grupo_id) VALUES (1, '2017-11-16', 'Casa', 'd', 16.449999999999999, 2, 1, 4);
INSERT INTO movimentacao (id, data, descricao, tipo, valor, categoria_id, cliente_id) VALUES (2, '2017-11-16', 'Teste', 'd', 66.450000000000003, 3, 1);
INSERT INTO movimentacao (id, data, descricao, tipo, valor, categoria_id, cliente_id, grupo_id) VALUES (3, '2017-11-16', 'Salario', 'r', 200.5, 9, 1, 12);
INSERT INTO movimentacao (id, data, descricao, tipo, valor, categoria_id, cliente_id, grupo_id) VALUES (4, '2017-10-10', '13 salario', 'r', 200.5, 9, 1, 13);
INSERT INTO movimentacao (id, data, descricao, tipo, valor, categoria_id, cliente_id, grupo_id) VALUES (5, '2017-10-10', 'Teste32', 'd', 100.5, 8, 1, 11);