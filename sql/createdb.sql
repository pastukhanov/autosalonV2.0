CREATE TABLE car (
                     id INTEGER PRIMARY KEY,
                     type TEXT NOT NULL,
                     model TEXT NOT NULL,
                     brand TEXT NOT NULL
);

CREATE TABLE customer (
                          id INTEGER PRIMARY KEY,
                          name TEXT NOT NULL,
                          age INTEGER NOT NULL,
                          gender TEXT NOT NULL
);

CREATE TABLE sale (
                      id INTEGER PRIMARY KEY,
                      car_id INTEGER NOT NULL,
                      car_type TEXT NOT NULL,
                      car_model TEXT NOT NULL,
                      car_brand TEXT NOT NULL,
                      customer_id INTEGER NOT NULL,
                      FOREIGN KEY (car_id) REFERENCES Car(id),
                      FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

INSERT INTO car (type, model, brand) VALUES
                                         ('TRUCK', 'F-150', 'Ford'),
                                         ('SUV', 'Model X', 'Tesla'),
                                         ('SEDAN', 'Accord', 'Honda'),
                                         ('VAN', 'Sienna', 'Toyota'),
                                         ('PASSENGER_CAR', 'Civic', 'Honda'),
                                         ('TRUCK', 'Silverado', 'Chevrolet'),
                                         ('SUV', 'Rav4', 'Toyota'),
                                         ('SEDAN', 'Camry', 'Toyota'),
                                         ('VAN', 'Odyssey', 'Honda'),
                                         ('PASSENGER_CAR', 'Mustang', 'Ford');


INSERT INTO customer (name, age, gender) VALUES
                                             ('John Doe', 30, 'MALE'),
                                             ('Jane Doe', 45, 'FEMALE'),
                                             ('Michael Smith', 22, 'MALE'),
                                             ('Anna Johnson', 35, 'FEMALE');


INSERT INTO sale (car_id, car_type, car_model, car_brand, customer_id) VALUES
                                             (3,'SEDAN', 'Accord', 'Honda', 1),
                                             (4,'VAN', 'Sienna', 'Toyota', 3);