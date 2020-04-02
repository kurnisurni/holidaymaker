/*CREATE VIEW roomsXroom_types AS
SELECT rooms.id AS id, room_types.guests_capacity AS guests_capacity, room_types.description AS description, room_types.accommodation AS accommodation 
FROM (rooms JOIN room_types ON((rooms.room_type = room_types.id)));*/


/*CREATE VIEW bookingRooms_date AS
SELECT bookings.check_in AS check_in, bookings.check_out AS check_out, booking_x_rooms.id AS room_id 
FROM (bookings JOIN booking_x_rooms ON((bookings.id = booking_x_rooms.booking)));*/

/* add guest
INSERT INTO guests SET NAME='Maria', email='mar@g.com', username='mar123'; */

/* add booking
INSERT INTO bookings (check_in, check_out, guest) VALUES ('2020-06-04', '2020-06-08', 4);*/

/* list accommodation according to facility
SELECT id, name FROM accommodations WHERE facility=3; */

/*list facilities
SELECT facilities.id, CONCAT('pool:', facilities.pool, ', restaurant:', facilities.restaurant, ', kids_club:', facilities.kids_club, ', night_club:', facilities.night_club) 
AS facilities_desc 
FROM facilities;*/

/* find guest by username
SELECT id FROM guests WHERE username = 'lee1';*/

/*get available rooms 
SELECT * FROM bookingrooms_date RIGHT JOIN roomsxroom_types ON room_id=id 
WHERE accommodation = 1 AND guests_capacity=2 
AND ((check_in IS NULL AND check_out IS NULL));*/

/* check all bookings
SELECT * FROM bookings;*/

/* delete booking
DELETE FROM bookings WHERE id = 50; */
