CREATE DATABASE drone_management_system;

USE drone_management_system;

CREATE TABLE locations(
id INT AUTO_INCREMENT,
x_coordinate DOUBLE NOT NULL,
y_coordinate DOUBLE NOT NULL,
PRIMARY KEY(id)
)Engine = InnoDB;

CREATE TABLE drones(
id INT AUTO_INCREMENT,
location_id INT NOT NULL,
battery DOUBLE NOT NULL,
capacity DOUBLE NOT NULL,
charging_rate DOUBLE NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY (location_id) REFERENCES locations(id)
)Engine = InnoDB;

CREATE TABLE warehouses(
id INT AUTO_INCREMENT,
location_id INT NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY (location_id) REFERENCES locations(id)
)Engine = InnoDB;

CREATE TABLE products(
id INT AUTO_INCREMENT,
warehouse_id INT NOT NULL,
name VARCHAR(100) NOT NULL,
quantity INT NOT NULL,
weigth_per_quantity DOUBLE NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY (warehouse_id) REFERENCES warehouses(id)
)Engine = InnoDB;

CREATE TABLE requests_log(
id INT AUTO_INCREMENT,
request_timestamp TIMESTAMP NOT NULL,
request_type VARCHAR(100) NOT NULL,
PRIMARY KEY(id)
)Engine = InnoDB;

INSERT INTO locations(x_coordinate,y_coordinate)
VALUES(42,42);
INSERT INTO locations(x_coordinate,y_coordinate)
VALUES(0,0);
INSERT INTO drones(location_id,battery,capacity,charging_rate)VALUES((SELECT id FROM locations WHERE x_coordinate = 0,y_coordinate = 0),2000,500,5);
INSERT INTO warehouses(location_id)
VALUES((SELECT id FROM locations WHERE x_coordinate = 42 AND y_coordinate = 42));

GRANT ALL
ON drone_management_system.*
TO 'secretuser'@'localhost'
IDENTIFIED BY 'verysecretpassword';
