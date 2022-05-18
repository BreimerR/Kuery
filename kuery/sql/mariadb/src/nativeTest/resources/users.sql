DROP TABLE IF EXISTS `users`;

CREATE TABLE `users`
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    isAdmin  BOOLEAN       DEFAULT false,
    `name`   VARBINARY(50) DEFAULT 'breimer',
    `weight` FLOAT(12),
    doa      DATE          DEFAULT STR_TO_DATE('', ''),
    gender   CHAR          DEFAULT 'o'
)