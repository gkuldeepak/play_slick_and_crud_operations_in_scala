# --- !Ups
CREATE TABLE `user_profile` (
   `id`            BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
   `first_name`    VARCHAR(254)   NOT NULL,
   `last_name`     VARCHAR(254)   NOT NULL,
   `email`         VARCHAR(254)   NOT NULL,
   `department`    VARCHAR(254)   NOT NULL,
   `created`       DATETIME       NOT NULL,
   `status`        BOOLEAN        NOT NULL DEFAULT 1
);

insert into user_profile values (1, 'test', 'test', 'test@example.com', 'scala', current_timestamp,true);

# ---- !Downs
DROP TABLE `user_profile`;
