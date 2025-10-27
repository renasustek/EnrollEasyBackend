CREATE DATABASE IF NOT EXISTS `enroll_easy` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `enroll_easy`;

CREATE TABLE members
(
    id       CHAR(36) PRIMARY KEY,
    membername VARCHAR(50) UNIQUE NOT NULL,
    membershipvalidtill     DATE          );

INSERT INTO members (id, membername, membershipvalidtill)
VALUES ('4f9b08c2-1af7-4b1b-b8f3-05e634012c7d', 'user1', null),
       ('5a7ef0f3-88f1-4fc4-a42a-2f2644c53fa4', 'user2', null),
       ('857ebcd4-8cb6-4f96-859e-75e1b52b07fc', 'user3', null),
       ('ca0b5eb4-52be-4d16-92e7-7756e9358c01', 'user4', null),
       ('a3e5f9cb-50a0-4233-9c2b-21957e9a1e33', 'user5', CURDATE()+1),
       ('0d121942-44ff-4e29-bc7d-8e1a127f0c44', 'user6', CURDATE()+1),
       ('e3fc5e56-3f6a-4a8a-9c49-8e2c7a52e98b', 'user7', CURDATE()+1),
       ('12f6f992-6f08-4f52-b88c-8d6c78d74e84', 'user8', CURDATE()+1),
       ('38389fc3-b96b-4d70-a84d-1a351e8f9ad7', 'user9', CURDATE()+1),
       ('fa913058-3f9a-442a-8823-dc4ae2c3d6f2', 'user10',CURDATE()+1);
