# Reservation_application

Postman instructions

GET http://localhost:8080/api/reservations
get all the reservations

GET http://localhost:8080/api/reservations/{"id"}
get a specific reservationEntity by id

GET http://localhost:8080/api/reservations/active?date={"date"}
get all active reservations

POST http://localhost:8080/api/reservations
post a reservationEntity to the list (error message if date, time, table number is the same)

PUT http://localhost:8080/api/reservations/{"id"}
update a reservationEntity

PUT http://localhost:8080/api/reservations/setActive/{"id"}
changes status of the reservationEntity to active

PUT http://localhost:8080/api/reservations/setInactive/{"id"}
changes status of the reservationEntity to inactive

DELETE PUT http://localhost:8080/api/reservations/{"id"}
deletes a reservationEntity by id