INSERT INTO users (id, username, email, password) VALUES (1, 'manolo', 'manolo@user.com', '1234');
INSERT INTO users (id, username, email, password) VALUES (2, 'vicente', 'vicente@user.com', '1234');
INSERT INTO users (id, username, email, password) VALUES (3, 'fran', 'fran@user.com', '1234');

INSERT INTO categories (id, name, description) VALUES (1, 'asd1', 'asdasd');
INSERT INTO categories (id, name, description) VALUES (2, 'asd2', 'asdasd');

INSERT INTO posts (id, title, description, content, category_id) VALUES (1, 'example1', 'this is the description of example1', 'this is the content of example1', 1);
INSERT INTO posts (id, title, description, content) VALUES (2, 'example2', 'this is the description of example2', 'this is the content of example2');
INSERT INTO posts (id, title, description, content) VALUES (3, 'example3', 'this is the description of example3', 'this is the content of example3');
