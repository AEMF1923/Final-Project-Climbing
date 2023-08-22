DROP TABLE IF EXISTS route_permit; 
DROP TABLE IF EXISTS permit; 
DROP TABLE IF EXISTS climber; 
DROP TABLE IF EXISTS route; 

CREATE TABLE route (
route_id int unsigned NOT NULL AUTO_INCREMENT,
route_name  VARCHAR(128) NOT NULL,
route_grade VARCHAR(6) NOT NULL, 
route_grade_range VARCHAR(128), 
route_grade_range_desc VARCHAR(300),
route_state VARCHAR(128),
route_notes VARCHAR(500),
PRIMARY KEY(route_id)
);


CREATE TABLE climber (
climbing_id int unsigned   NOT NULL AUTO_INCREMENT,
route_id int unsigned  NOT NULL,
climber_first_name VARCHAR(128) NOT NULL,
climber_last_name VARCHAR(128) NOT NULL,
climber_age int, 
climber_email VARCHAR(256),
date_of_route_climbed VARCHAR(12),
PRIMARY KEY (climbing_id),
FOREIGN KEY (route_id) REFERENCES route (route_id) ON DELETE CASCADE
);


CREATE TABLE permit(
permit_id int unsigned  NOT NULL AUTO_INCREMENT, 
-- route_id int unsigned  NOT NULL,
permit_type VARCHAR(128), 
permit_price int,
PRIMARY KEY (permit_id)
-- FOREIGN KEY (route_id) REFERENCES route (route_id) ON DELETE CASCADE
);

CREATE TABLE route_permit (
route_id int unsigned  NOT NULL, 
permit_id int unsigned  NOT NULL, 
FOREIGN KEY (route_id) REFERENCES route (route_id) ON DELETE CASCADE,
FOREIGN KEY (permit_id) REFERENCES permit (permit_id) ON DELETE CASCADE
);