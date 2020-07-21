DELETE FROM order_products;
DELETE FROM orders;
DELETE FROM baskets;
DELETE FROM users;
DELETE FROM products;

INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('56','VLqdlJqA','Chai Latte','chai_latte','Franck (company)',2700);
INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('58','MMS83mf0','Australian Coffee ','australian_coffee','Gevalia',3700);
INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('59','CTlYPMYW','Irish Coffee ','irish_coffee','Barcaffe',3500);
INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('34','n1MMNc89','Kaffee Fertig ','kaffee_fertig','Flocafé',3700);
INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('35','TEkO8zPA','Kopi susu','kopi_susu','Nescafé',13800);
INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('36','v4CM8ZJu','Doppio','doppio','Massimo Zanetti Beverage Group',1800);
INSERT INTO `products`(`id`,`product_key`, `name`, `url`, `manufacturer`, `price`) VALUES ('37','hoRApgUW','Cold Brew Coffee','cold_brew_coffee','Manhattan Special',7600);


INSERT INTO `users`(`name`, `password`, `enabled`, `role`, `real_name`) VALUES ('Anna','$2a$04$ifGHSmdKsQvqUswws2N8meEXlVHJCeJAn9oEfwUHG2lG8n4ThTYJu',1,'ROLE_ADMIN','Joe Doe');
INSERT INTO `users`(`name`, `password`, `enabled`, `role`, `real_name`) VALUES ('Feri','$2a$04$ifGHSmdKsQvqUswws2N8meEXlVHJCeJAn9oEfwUHG2lG8n4ThTYJu',1,'ROLE_ADMIN','Joe Doe');
INSERT INTO `users`(`name`, `password`, `enabled`, `role`, `real_name`) VALUES ('Antal','$2a$04$ifGHSmdKsQvqUswws2N8meEXlVHJCeJAn9oEfwUHG2lG8n4ThTYJu',1,'ROLE_ADMIN','Joe Doe');

INSERT INTO baskets (user_name, product_id) VALUES ('Anna', 56);
INSERT INTO baskets (user_name, product_id) VALUES ('Anna', 58);
INSERT INTO baskets (user_name, product_id) VALUES ('Anna', 59);
INSERT INTO baskets (user_name, product_id) VALUES ('Feri', 34);
INSERT INTO baskets (user_name, product_id) VALUES ('Feri', 35);
INSERT INTO baskets (user_name, product_id) VALUES ('Feri', 36);
INSERT INTO baskets (user_name, product_id) VALUES ('Feri', 37);