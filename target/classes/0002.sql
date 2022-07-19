CREATE TABLE IF NOT EXISTS USERS
(
  id_users INT NOT NULL,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  role varchar(45) DEFAULT NULL,
  status tinyint(4) DEFAULT NULL,
  hourly_rate TINYINT DEFAULT NULL,
  PRIMARY KEY (id_users)
);

CREATE TABLE IF NOT EXISTS CHANGES
(
  changeID INT NOT NULL AUTO_INCREMENT,
  id_user INT ,
  Fname VARCHAR(50),
  Lname VARCHAR(50),
  Role VARCHAR(50),
  Active BOOLEAN,
  HourRate TINYINT,
  Timestamp VARCHAR(40),
  PRIMARY KEY (changeID),
  FOREIGN KEY (id_user) REFERENCES users(id_users)
);