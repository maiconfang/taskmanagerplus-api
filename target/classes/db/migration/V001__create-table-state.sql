
CREATE TABLE state (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(80) NOT NULL,
  abbreviation varchar(5) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8