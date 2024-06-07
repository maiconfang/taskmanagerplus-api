set foreign_key_checks = 0;

delete from state;
delete from grouppp;
delete from grouppp_permission;
delete from permission;
delete from usserr;
delete from usserr_grouppp;
delete from oauth_client_details;

set foreign_key_checks = 1;

alter table state auto_increment = 1;
alter table grouppp auto_increment = 1;
alter table permission auto_increment = 1;
alter table usserr auto_increment = 1;


insert into permission (id, name, description) values (4, 'EDIT_STATES', 'Allow to create or edit states');
insert into permission (id, name, description) values (5, 'CONSULT_USSERRS_GROUPS_PERMISSIONS', 'Allow to consult users, groups and permissions');
insert into permission (id, name, description) values (6, 'EDIT_USSERRS_GROUPS_PERMISSIONS', 'Allow to create or edit users, groups and permissions');
insert into permission (id, name, description) values (10, 'VIEW_REPORTS', 'Allow to view reports');


insert into grouppp (id, name) values (1, 'Manager'), (2, 'Seller'), (3, 'Secretary'), (4, 'Register');


# Add all permissions in manager grouppp
insert into grouppp_permission (grouppp_id, permission_id)
select 1, id from permission;

# Add permissions in seller's grouppp
insert into grouppp_permission (grouppp_id, permission_id)
select 2, id from permission where name like 'CONSULTAR_%';


# Add permissions in helper grouppp
insert into grouppp_permission (grouppp_id, permission_id)
select 3, id from permission where name like 'CONSULTAR_%';


insert into usserr (id, name, email, password, dt_create, dt_update) values
(1, 'João da Silva', 'joao.ger@maiffarm.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp, utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@maiffarm.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp, utc_timestamp),
(3, 'José Souza', 'jose.aux@maiffarm.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp, utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@maiffarm.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp, utc_timestamp),
(5, 'Manoel Lima', 'manoel.loja@gmail.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp, utc_timestamp),
(6, 'Débora Mendonça', 'testesmaiconfang@gmail.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp, utc_timestamp),
(7, 'Carlos Lima', 'email.teste.aw+carlos@gmail.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp, utc_timestamp);

insert into usserr_grouppp (usserr_id, grouppp_id) values (1, 1), (1, 2), (2, 2), (3, 3), (4, 4);


insert into oauth_client_details (
  client_id, resource_ids, client_secret, 
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'maiffarm-web', null, '$2y$12$w3igMjsfS5XoAYuowoH3C.54vRFWlcXSHLjX7MwF990Kc2KKKh72e',
  'READ,WRITE', 'password', null, null,
  60 * 60 * 6, 60 * 24 * 60 * 60, null
);

insert into oauth_client_details (
  client_id, resource_ids, client_secret, 
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'foodanalytics', null, '$2y$12$fahbH37S2pyk1RPuIHKP.earzFmgAJJGo26rE.59vf4wwiiTKHnzO',
  'READ,WRITE', 'authorization_code', 'http://www.foodanalytics.local:8082', null,
  null, null, null
);

insert into oauth_client_details (
  client_id, resource_ids, client_secret, 
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'billing', null, '$2y$12$fHixriC7yXX/i1/CmpnGH.RFyK/l5YapLCFOEbIktONjE8ZDykSnu',
  'READ,WRITE', 'client_credentials', null, 'VIEW_REPORTS',
  null, null, null
);


INSERT INTO `state` (`id`, `name`, `fs`) VALUES
(1, 'Acre', 'AC'),
(2, 'Alagoas', 'AL'),
(3, 'Amazonas', 'AM'),
(4, 'Amapá', 'AP'),
(5, 'Bahia', 'BA'),
(6, 'Ceará', 'CE'),
(7, 'Distrito Federal', 'DF'),
(8, 'Espírito Santo', 'ES'),
(9, 'Goiás', 'GO'),
(10, 'Maranhão', 'MA'),
(11, 'Minas Gerais', 'MG'),
(12, 'Mato Grosso do Sul', 'MS'),
(13, 'Mato Grosso', 'MT'),
(14, 'Pará', 'PA'),
(15, 'Paraíba', 'PB'),
(16, 'Pernambuco', 'PE'),
(17, 'Piauí', 'PI'),
(18, 'Paraná', 'PR'),
(19, 'Rio de Janeiro', 'RJ'),
(20, 'Rio Grande do Norte', 'RN'),
(21, 'Rondônia', 'RO'),
(22, 'Roraima', 'RR'),
(23, 'Rio Grande do Sul', 'RS'),
(24, 'Santa Catarina', 'SC'),
(25, 'Sergipe', 'SE'),
(26, 'São Paulo', 'SP'),
(27, 'Tocantins', 'TO');



