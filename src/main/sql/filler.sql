ALTER SEQUENCE movie_id_seq RESTART WITH 1

-- Movies
insert into movie(title, release_year, genre) 
	values ('movie1', 1993, 'action'),
			('movie2', 1993, 'action'),
			('movie3', 1993, 'action');

-- Ratings
insert into rating(mid, val, count)
	values (1, 1, 1),
			(1, 2, 5),
			--(1, 3, 2),
			(1, 4, 1),
			(1, 5, 1),
			(2, 1, 3),
			--(2, 2, 1),
			(2, 3, 1),
			--(2, 4, 1),
			(2, 5, 5),
			(3, 1, 1),
			(3, 2, 8),
			--(3, 3, 1),
			(3, 4, 8),
			(3, 5, 1);

-- Reviews
insert into review(mid, id, reviewer_name, review_summary, review, rating)
	values (1, 1, 'Nuno', 'Kickass Movie', 'review', 5),
			(1, 2, 'Joao', 'Awesome Movie', 'review', 4),
			(2, 1, 'Nuno', 'Not Worthy to see Movie', 'review', 1),
			(2, 2, 'Andre', 'Normal Movie', 'review', 3),
			(3, 1, 'Paulo', 'Kickass Movie', 'review', 5);