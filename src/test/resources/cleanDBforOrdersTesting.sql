DELETE FROM order_products;
DELETE FROM orders;
DELETE FROM baskets;
DELETE FROM users;
DELETE FROM products;

INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('45','VLqdlJqA','Chai Latte','chai_latte','Franck (company)',2700);
INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('5','MMS83mf0','Australian Coffee ','australian_coffee','Gevalia',3700);
INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('35','CTlYPMYW','Irish Coffee ','irish_coffee','Barcaffe',3500);
INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('55','n1MMNc89','Kaffee Fertig ','kaffee_fertig','Flocafé',3700);
INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('60','TEkO8zPA','Kopi susu','kopi_susu','Nescafé',13800);
INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('10','v4CM8ZJu','Doppio','doppio','Massimo Zanetti Beverage Group',1800);
INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('56','hoRApgUW','Cold Brew Coffee','cold_brew_coffee','Manhattan Special',7600);


INSERT INTO `users`(`name`, `password`, `enabled`, `role`, `real_name`) VALUES ('testuser1','$2a$04$ifGHSmdKsQvqUswws2N8meEXlVHJCeJAn9oEfwUHG2lG8n4ThTYJu',1,'ROLE_USER','John Doe');
INSERT INTO `users`(`name`, `password`, `enabled`, `role`, `real_name`) VALUES ('testuser2','$2a$04$ifGHSmdKsQvqUswws2N8meEXlVHJCeJAn9oEfwUHG2lG8n4ThTYJu',1,'ROLE_USER','Joe Doe');
INSERT INTO `users`(`name`, `password`, `enabled`, `role`, `real_name`) VALUES ('testuserWithoutOrder','$2a$04$ifGHSmdKsQvqUswws2N8meEXlVHJCeJAn9oEfwUHG2lG8n4ThTYJu',1,'ROLE_USER','Joe Doe');
INSERT INTO `users`(`name`, `password`, `enabled`, `role`, `real_name`) VALUES ('admin','$2a$04$ifGHSmdKsQvqUswws2N8meEXlVHJCeJAn9oEfwUHG2lG8n4ThTYJu',1,'ROLE_ADMIN','Joe Doe');

INSERT INTO `orders`(`id`, `user_name`, `order_date`, `order_sum`, `order_status`) VALUES (1,'testuser1','2018-08-07',5000,'FULFILLED');
INSERT INTO `orders`(`id`, `user_name`, `order_date`, `order_sum`, `order_status`) VALUES (2,'testuser1','2018-08-08',6000,'ACTIVE');
INSERT INTO `orders`(`id`, `user_name`, `order_date`, `order_sum`, `order_status`) VALUES (3,'testuser1','2018-08-14',7000,'DELETED');
INSERT INTO `orders`(`id`, `user_name`, `order_date`, `order_sum`, `order_status`) VALUES (4,'testuser1','2018-08-15',8000,'ACTIVE');
INSERT INTO `orders`(`id`, `user_name`, `order_date`, `order_sum`, `order_status`) VALUES (9,'testuser1','2018-08-20',7000,'ACTIVE');
INSERT INTO `orders`(`id`, `user_name`, `order_date`, `order_sum`, `order_status`) VALUES (11,'testuser1','2018-08-25',5000,'ACTIVE');
INSERT INTO `orders`(`id`, `user_name`, `order_date`, `order_sum`, `order_status`) VALUES (5,'testuser2','2018-08-16',5000,'FULFILLED');
INSERT INTO `orders`(`id`, `user_name`, `order_date`, `order_sum`, `order_status`) VALUES (6,'testuser2','2018-08-17',3000,'ACTIVE');
INSERT INTO `orders`(`id`, `user_name`, `order_date`, `order_sum`, `order_status`) VALUES (7,'testuser2','2018-08-18',9000,'DELETED');
INSERT INTO `orders`(`id`, `user_name`, `order_date`, `order_sum`, `order_status`) VALUES (8,'testuser2','2018-08-19',2000,'ACTIVE');
INSERT INTO `orders`(`id`, `user_name`, `order_date`, `order_sum`, `order_status`) VALUES (10,'testuser2','2018-08-21',8000,'ACTIVE');
INSERT INTO `orders`(`id`, `user_name`, `order_date`, `order_sum`, `order_status`) VALUES (12,'testuser2','2018-08-28',3000,'ACTIVE');

INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (1,45);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (1,5);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (2,35);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (3,55);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (4,60);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (4,10);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (5,45);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (5,5);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (6,35);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (7,55);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (8,60);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (8,10);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (9,56);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (10,5);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (11,55);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (12,35);
INSERT INTO `order_products`(`order_id`, `product_id`) VALUES (12,60);

INSERT INTO `baskets`(`user_name`, `product_id`) VALUES ('testuser1',55);
INSERT INTO `baskets`(`user_name`, `product_id`) VALUES ('testuser1',35);
INSERT INTO `baskets`(`user_name`, `product_id`) VALUES ('testuser1',10);

INSERT INTO `baskets`(`user_name`, `product_id`) VALUES ('testuser2',5);
INSERT INTO `baskets`(`user_name`, `product_id`) VALUES ('testuser2',35);
INSERT INTO `baskets`(`user_name`, `product_id`) VALUES ('testuser2',10);
