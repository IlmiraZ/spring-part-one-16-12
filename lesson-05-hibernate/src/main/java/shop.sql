-- shop.products definition

CREATE
DATABASE `shop`;

ALTER
DATABASE `shop`
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci;


CREATE TABLE `products`
(
    `id`    bigint         NOT NULL AUTO_INCREMENT,
    `price` decimal(19, 2) NOT NULL,
    `title` varchar(255)   NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_products_title` (`title`)
) ENGINE=InnoDB;


-- shop.users definition

CREATE TABLE `users`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_users_name` (`name`)
) ENGINE=InnoDB;


-- shop.clients definition

CREATE TABLE `clients`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY       `FK_clients_users_id` (`user_id`),
    CONSTRAINT `FK_clients_users_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB;


-- shop.orders definition

CREATE TABLE `orders`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `date`      datetime(6) NOT NULL,
    `client_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY         `FK_orders_clients_id` (`client_id`),
    CONSTRAINT `FK_orders_clients_id` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB;


-- shop.product_items definition

CREATE TABLE `order_items`
(
    `id`         bigint         NOT NULL AUTO_INCREMENT,
    `price`      decimal(19, 2) NOT NULL,
    `quantity`   int            NOT NULL,
    `order_id`   bigint         NOT NULL,
    `product_id` bigint         NOT NULL,
    PRIMARY KEY (`id`),
    KEY          `FK_order_items_orders_id` (`order_id`),
    KEY          `FK_order_items_products_id` (`product_id`),
    CONSTRAINT `FK_order_items_products_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
    CONSTRAINT `FK_order_items_orders_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB;


INSERT INTO products (title, price)
VALUES ('product1', 22.22),
       ('product2', 26.68),
       ('product3', 51.80),
       ('product4', 99.33),
       ('product5', 10.01);

INSERT INTO users (name)
VALUES ('Tom'),
       ('Jerry'),
       ('John');

INSERT INTO clients (user_id)
VALUES (1),
       (2),
       (3);

INSERT INTO orders (`date`, client_id)
VALUES ('2020-01-20 12:00:00', 1),
       ('2020-01-20 13:15:00', 2),
       ('2020-01-20 15:20:00', 3);

INSERT INTO order_items (order_id, product_id, quantity, price)
VALUES (1, 2, 3, 22.22),
       (2, 3, 5, 10.02),
       (3, 1, 2, 45.05);