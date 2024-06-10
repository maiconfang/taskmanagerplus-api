set foreign_key_checks = 0;

delete from grouppp;
delete from grouppp_permission;
delete from permission;
delete from usserr;
delete from usserr_grouppp;
delete from oauth_client_details;
delete from province;
delete from task;

set foreign_key_checks = 1;

alter table grouppp auto_increment = 1;
alter table permission auto_increment = 1;
alter table usserr auto_increment = 1;
alter table province auto_increment = 1;
alter table task auto_increment = 1;


insert into permission (id, name, description) values (4, 'EDIT_PROVINCES', 'Allow to create or edit provinces');
insert into permission (id, name, description) values (5, 'CONSULT_USSERRS_GROUPS_PERMISSIONS', 'Allow to consult users, groups and permissions');
insert into permission (id, name, description) values (6, 'EDIT_USSERRS_GROUPS_PERMISSIONS', 'Allow to create or edit users, groups and permissions');
insert into permission (id, name, description) values (10, 'VIEW_REPORTS', 'Allow to view reports');
insert into permission (id, name, description) values (11, 'EDIT_TASKS', 'Allow to create or edit tasks');

insert into grouppp (id, name) values 
                    (1, 'Manager'), 
                    (2, 'Seller'), 
                    (3, 'Secretary'), 
                    (4, 'Register');


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


INSERT INTO `province` (`id`, `name`, `abbreviation`) VALUES
(1, 'Alberta', 'AB'),
(2, 'British Columbia', 'BC'),
(3, 'Manitoba', 'MB'),
(4, 'New Brunswick', 'NB'),
(5, 'Newfoundland and Labrador', 'NL'),
(6, 'Nova Scotia', 'NS'),
(7, 'Ontario', 'ON'),
(8, 'Prince Edward Island', 'PE'),
(9, 'Quebec', 'QC'),
(10, 'Saskatchewan', 'SK'),
(11, 'Northwest Territories', 'NT'),
(12, 'Nunavut', 'NU'),
(13, 'Yukon', 'YT');


INSERT INTO `task` (`id`, `title`, `description`, `due_date`, `completed`, `created_at`, `updated_at`) VALUES
(1, 'Task 1', 'Description for Task 1', '2024-06-30', 0, '2024-06-01 12:00:00', '2024-06-01 12:00:00'),
(2, 'Task 2', 'Description for Task 2', '2024-07-01', 0, '2024-06-01 13:00:00', '2024-06-01 13:00:00'),
(3, 'Task 3', 'Description for Task 3', '2024-07-02', 1, '2024-06-01 14:00:00', '2024-06-02 14:00:00'),
(4, 'Task 4', 'Description for Task 4', '2024-07-03', 0, '2024-06-01 15:00:00', '2024-06-01 15:00:00'),
(5, 'Task 5', 'Description for Task 5', '2024-07-04', 1, '2024-06-01 16:00:00', '2024-06-03 16:00:00'),
(6, 'Task 6', 'Description for Task 6', '2024-07-05', 0, '2024-06-01 17:00:00', '2024-06-01 17:00:00'),
(7, 'Task 7', 'Description for Task 7', '2024-07-06', 1, '2024-06-01 18:00:00', '2024-06-04 18:00:00'),
(8, 'Task 8', 'Description for Task 8', '2024-07-07', 0, '2024-06-01 19:00:00', '2024-06-01 19:00:00'),
(9, 'Task 9', 'Description for Task 9', '2024-07-08', 1, '2024-06-01 20:00:00', '2024-06-05 20:00:00'),
(10, 'Task 10', 'Description for Task 10', '2024-07-09', 0, '2024-06-01 21:00:00', '2024-06-01 21:00:00'),
(11, 'Task 11', 'Description for Task 11', '2024-07-10', 1, '2024-06-01 22:00:00', '2024-06-06 22:00:00'),
(12, 'Task 12', 'Description for Task 12', '2024-07-11', 0, '2024-06-01 23:00:00', '2024-06-01 23:00:00');



