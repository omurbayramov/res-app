# Reservation_application

Postman instructions

GET http://localhost:8080/api/reservations
get all the reservations

GET http://localhost:8080/api/reservations/{"id"}
get a specific reservation by id

GET http://localhost:8080/api/reservations/active?date={"date"}
get all active reservations

POST http://localhost:8080/api/reservations
post a reservation to the list (error message if date, time, table number is the same)

PUT http://localhost:8080/api/reservations/{"id"}
update a reservation

PUT http://localhost:8080/api/reservations/setActive/{"id"}
changes status of the reservation to active

PUT http://localhost:8080/api/reservations/setInactive/{"id"}
changes status of the reservation to inactive

DELETE PUT http://localhost:8080/api/reservations/{"id"}
deletes a reservation by id