// get reservation
SELECT * FROM reservation WHERE site_id = ?;

//book reservation
INSERT INTO reservation (site_id, name, from_date, to_date, create_date)
VALUES (?,?,?,?,NOW());


//get booked reservation
SELECT * FROM reservation WHERE site_id = ? AND name = ? AND from_date = ? AND to_date = ? AND create_date = ?;


 



