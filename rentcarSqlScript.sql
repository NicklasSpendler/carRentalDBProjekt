-- create the database
DROP DATABASE IF EXISTS carrental;
CREATE DATABASE carrental;
use carrental;
CREATE TABLE privateCustomer (customerID int(10) NOT NULL AUTO_INCREMENT,
firstName varChar(25),
lastName varChar(25),
zipCode int(10),
cityName varChar(30),
adress varChar(50),
mobileNr int(12),
email varChar(100),
driversLicenceNumber int(10),
CustomerSinceDate date,
PRIMARY KEY (customerID));
CREATE TABLE cars (carID int(10) NOT NULL AUTO_INCREMENT,
carType varChar(255),
brand varChar(255),
model varChar(255),
regNumberPlate varChar(255),
firstRegYearMonth timestamp,
kmDriven int(100),
PRIMARY KEY (carID));
CREATE TABLE Booking (bookingID int(7) NOT NULL AUTO_INCREMENT,
customerID int(10),
carID int(10),
rentDate datetime,
returnDate datetime,
PRIMARY KEY (bookingID));




ALTER TABLE booking ADD CONSTRAINT booking_FK_car FOREIGN KEY(carID) REFERENCES cars (carID);
ALTER TABLE booking ADD CONSTRAINT booking_FK_customer FOREIGN KEY(customerID) REFERENCES privatecustomer (customerID);


INSERT into privatecustomer (firstName, lastName, zipCode, cityName, adress, mobileNr, email, driversLicenceNumber, CustomerSinceDate)
values ('Jesper', 'Nielsen', 2605, 'Brondby', 'Tranehaven 1', 12345678, 'no@nop.niks', 1023456789, 20210101);
INSERT into privatecustomer (firstName, lastName, zipCode, cityName, adress, mobileNr, email, driversLicenceNumber, CustomerSinceDate)
values ('Jens', 'Nielsen', 2605, 'Brondby', 'Tranehaven 1', 12345678, 'no@nop.niks', 1023456789, 20210101);



INSERT into cars (carType, brand, model, regNumberPlate, firstRegYearMonth, kmDriven)
Values ('Luxury', 'Porsche', '911 GT3', 'AU 318 221', 20200101, 5000);

INSERT into cars (carType, brand, model, regNumberPlate, firstRegYearMonth, kmDriven)
Values ('Sport', 'ferrari', 'La ferrari', 'KE 133 769', 20200101, 50000);

INSERT into cars (carType, brand, model, regNumberPlate, firstRegYearMonth, kmDriven)
Values ('Sport', 'Bugatti', 'veyron', 'KE 691 337', 20200101, 500000);

INSERT into booking (customerID, carID, rentDate, returnDate)
Values (1, 1, 20210326, 20210402);


-- UPDATE cars SET carType = "test",brand = "test",model = "test",regNumberPlate = "11111111",firstRegYearMonth = "20200101",kmDriven = "111"where carID = 2;

select * from cars;

ALTER TABLE `carrental`.`booking` 
DROP FOREIGN KEY `booking_FK_car`,
DROP FOREIGN KEY `booking_FK_customer`;
ALTER TABLE `carrental`.`booking` 
ADD CONSTRAINT `booking_FK_car`
  FOREIGN KEY (`carID`)
  REFERENCES `carrental`.`cars` (`carID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `booking_FK_customer`
  FOREIGN KEY (`customerID`)
  REFERENCES `carrental`.`privatecustomer` (`customerID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
