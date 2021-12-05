create table `order_history`(
	`id` bigint NOT NULL AUTO_INCREMENT,
  `order_no` varchar(70) NOT null unique,
  `restaurant_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `delivery_executive_id` bigint,
  `initial_amount` decimal(19,2) default '0',
  `coupon_code_applied` varchar(70),
  `discount_amount` decimal(19,2) default '0',
  `final_amount` decimal(19,2) default '0',
  `status` varchar(70) not null,
  `ordered_on` timestamp default current_timestamp,
  PRIMARY KEY (`id`),
  foreign key(`restaurant_id`) references `restaurants`(`id`)
);

create table `order_history`(
	`id` bigint NOT NULL AUTO_INCREMENT,
  `order_no` varchar(70) NOT null unique,
  `restaurant_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `delivery_executive_id` bigint,
  `initial_amount` decimal(19,2) default '0',
  `coupon_code_applied` varchar(70),
  `discount_amount` decimal(19,2) default '0',
  `final_amount` decimal(19,2) default '0',
  `status` varchar(70) not null,
  `ordered_on` timestamp default current_timestamp,
  PRIMARY KEY (`id`),
  foreign key(`restaurant_id`) references `restaurants`(`id`)
);

create table `order_history_extra_charge_details`(
	`id` bigint NOT NULL AUTO_INCREMENT,
  `order_history_id` bigint NOT null,
  `extra_charge_type` varchar(70) NOT NULL,
  `amount` decimal(19,2) default '0',
  PRIMARY KEY (`id`),
  foreign key(`order_history_id`) references `order_history`(`id`)
);