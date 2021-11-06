CREATE TABLE `restaurants` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(70) NOT NULL,
  `address` varchar(70) NOT NULL,
  `opens_at` varchar(70) NOT NULL,
  `closes_at` varchar(70) NOT NULL,
  `dining_category` varchar(70) NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(70) NOT NULL,
  `food_category` varchar(70) NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `restaurant_food_category_mapping` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint NOT NULL,
  `food_category` varchar(70) NOT NULL,
  PRIMARY KEY (`id`),
  unique key `uniqueRestaurantIdAndFoodCategory` (`restaurant_id`,`food_category`),
  foreign key (`restaurant_id`) references restaurants(`id`)
);

CREATE TABLE `restaurant_item_mapping` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint NOT NULL,
  `item_id` bigint NOT NULL,
  `ratings` varchar(70),
  `avg_preparation_time` bigint NOT NULL,
  `serve_type` varchar(70) not NULL,
  `serve_quantity` bigint not null,
  `price` bigint NOT NULL,
  `is_available` tinyint(1) default '0',
  foreign key (`restaurant_id`) references restaurants(`id`),
  foreign key (`item_id`) references items(`id`),
  PRIMARY KEY (`id`)
);

ALTER TABLE restaurants ADD is_closed TINYINT(1) DEFAULT 0 NULL;
