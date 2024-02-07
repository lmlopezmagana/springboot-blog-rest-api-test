delete from users;
delete from comments;
delete from posts;
delete from roles;
INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_USER');
<<<<<<< HEAD
INSERT INTO users (id, name, username, email, password) VALUES (2, 'Pedro', 'ToRechulon', 'pedro@gmail.com', '1234');
insert into users (id,name,username,email,password) values (1, 'Juan', 'lacabra_7', 'juanillolacabra@gmail.com','lacabra');
insert into categories (id, name, description) values (1, 'hola', 'holas a mundo');
insert into posts (id, title, description, content, category_id) values ( 1, 'hola', 'hola mundo java', 'System.out.println("Hola Mundo")', 1);
=======
INSERT INTO users (id, name, username, email, password) VALUES (301, 'Pedro', 'ToRechulon', 'pedro@gmail.com', '$2b$12$FV5uUswMRP9NMgZGDeMm6ejlQ37SIAe6biliXr5Dc1dEL4sQLB6Yq');
insert into users (id,name,username,email,password) values (300, 'Juan', 'lacabra_7', 'juanillolacabra@gmail.com','lacabra');
>>>>>>> 8697482dc68c89355ea0823c1f7ddd79df7d6e1d
