--
--SET @tableNames = 'tour, tour_package, tour_rating'; -- Add table names here separated by commas
--
--BEGIN
---- Iterate through the list of table names
--    WHILE LENGTH(@tableNames) > 0 DO
--        -- Get the first table name
--        SET @tableName = SUBSTRING_INDEX(@tableNames, ',', 1);
--
--        -- Check if the table exists
--        SELECT COUNT(*) INTO @tableExists
--        FROM information_schema.tables
--        WHERE table_schema = 'explorecali' -- Replace with your database name
--        AND table_name = @tableName;
--
--        -- Drop the table if it exists
--        SET @dropSql = IF(@tableExists > 0, CONCAT('DROP TABLE ', @tableName, ';'), '');
--        PREPARE dropStatement FROM @dropSql;
--        EXECUTE dropStatement;
--        DEALLOCATE PREPARE dropStatement;
--
--        -- Remove the processed table name from the list
--        SET @tableNames = TRIM(BOTH ',' FROM SUBSTRING(@tableNames, LENGTH(@tableName) + 2));
--    END WHILE;
--END;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `tour_package`;
DROP TABLE IF EXISTS `tour`;
DROP TABLE IF EXISTS `tour_rating`;
SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE IF NOT EXISTS tour_package(
  code CHAR(2) NOT NULL UNIQUE,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS tour (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  tour_package_code CHAR(2) NOT NULL,
  title VARCHAR(100) NOT NULL,
  description VARCHAR(2000) NOT NULL,
  blurb VARCHAR(2000) NOT NULL,
  bullets VARCHAR(2000) NOT NULL,
  price VARCHAR(10) not null,
  duration VARCHAR(32) NOT NULL,
  difficulty VARCHAR(16) NOT NULL,
  region VARCHAR(20) NOT NULL,
  keywords VARCHAR(100)
);
ALTER TABLE tour ADD CONSTRAINT FK_TOUR_PACKAGE_CODE FOREIGN KEY (tour_package_code) REFERENCES tour_package(code);

CREATE TABLE IF NOT EXISTS tour_rating (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tour_id BIGINT,
    customer_id BIGINT,
    score INT,
    comment VARCHAR(100));

ALTER TABLE tour_rating ADD CONSTRAINT FK_tour_id FOREIGN KEY (tour_id) REFERENCES tour(id);
ALTER TABLE tour_rating ADD UNIQUE MyConstraint (tour_id, customer_id);

