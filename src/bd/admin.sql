insert into usuarios (usuario_id, nome, telefone, sexo) values (1, 'Administrador', '33223322', 'M');
insert into usuarios_conta (username, usuario_id, senha) values ('admin', 1, 'admin');
insert into usuarios_acesso (usuario_id, usuarios, produtos, categorias, empresas, rfid, entrada, saida) values (1, true, true, true, true, true, true, true);
