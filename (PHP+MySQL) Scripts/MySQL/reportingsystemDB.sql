

CREATE TABLE `notes` (
  `id` int(11) NOT NULL,
  `note` text NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `user_email` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=Aria DEFAULT CHARSET=latin1;


ALTER TABLE `notes`
  ADD PRIMARY KEY (`id`);

INSERT INTO `notes` (`id`, `note`, `user_name`, `user_email`, `created_at`) VALUES
(5, 'Default note', 'Default user', 'user@default.com', '2018-04-06 07:14:05');


CREATE TABLE `users` (
  `unique_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `level` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created_at` varchar(100) NOT NULL,
  `updated_at` varchar(100) NOT NULL
) ENGINE=Aria DEFAULT CHARSET=latin1;


ALTER TABLE `users`
  ADD PRIMARY KEY (`unique_id`);


