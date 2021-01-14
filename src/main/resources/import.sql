# Users
insert into user(id, firstName, lastName, userName, password) values(10001, 'al', 'dupree', 'adupree@governmentcio.com', 'password');
insert into user(id, firstName, lastName, userName, password) values(10002, 'william', 'drew', 'wdrew@governmentcio.com', 'password');
insert into user(id, firstName, lastName, userName, password) values(10003,'elsa', 'reed', 'ereed@governmentcio.com', 'password');

# Roles
insert into role(id, roletype, name, description) values(10001,'PROJECT_MANAGER', 'Project manager', 'Manages the project');
insert into role(id, roletype, name, description) values(10002,'TECHNICAL_LEAD','Technical lead', 'Provides technical leadership');
insert into role(id, roletype, name, description) values(10004,'SECURITY_ANALYST','Security analyst', 'Provides guidance and oversight on cyber issues');

insert into user_role(user_id, role_id, projectID) values(10001, 10001, 90001);
insert into user_role(user_id, role_id, projectID) values(10002, 10004, 90002);


