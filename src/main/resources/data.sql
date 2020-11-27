
INSERT INTO categoria(nome)
VALUES('mercearia');
INSERT INTO categoria(nome)
VALUES('higiene');
INSERT INTO categoria(nome)
VALUES('limpeza');


INSERT INTO produto(codigo_barra, fabricante, nome, preco, situacao, categoria_id)
VALUES('123451', 'Sazon', 'Tempero', 2.5, 2, 1);
INSERT INTO produto (codigo_barra, fabricante, nome, preco, situacao, categoria_id)
VALUES('123452', 'Protex', 'Sabonete', 1.5, 1, 2);
INSERT INTO produto(codigo_barra, fabricante, nome, preco, situacao, categoria_id)
VALUES('123453', 'Flocao', 'Cuscuz', 3.0, 2, 1);
INSERT INTO produto(codigo_barra, fabricante, nome, preco, situacao, categoria_id)
VALUES('123454', 'Dragao', 'Agua Sanitaria', 5.0, 2, 3);
INSERT INTO produto(codigo_barra, fabricante, nome, preco, situacao, categoria_id)
VALUES('123455', 'Nissin', 'Miojo', 3.5, 1, 1);


INSERT INTO lote(data_de_validade, numero_de_itens, produto_id)
VALUES('2020-12-12', 5, 2);

INSERT INTO lote(data_de_validade, numero_de_itens, produto_id)
VALUES('2020-12-30', 5, 5);

INSERT INTO user (login, senha)
VALUES('admin', '$2a$10$pjRTCBNJ0XPTQ.o8N2q2T.Qy3Qs7qOAV8S6R8zRM1PeOqaRKdYqVW');
INSERT INTO perfis(user_id, perfis)
VALUES (1, 1);
