delete from review;
delete from rating;
delete from movie_collection;
delete from collection;
delete from movie;
ALTER SEQUENCE movie_id_seq RESTART WITH 1;
ALTER SEQUENCE collection_id_seq RESTART WITH 1;

-- Movies

insert into movie(title, release_year, genre) 
	values ('Spotlight', 2015, 'History'),
			('Birdman', 2014, 'Comedy'),
			('12 Years a Slave', 2013, 'Drama'),
			('Argo', 2012, 'Drama'),
			('The Artist', 2011, 'Drama'),
			('The Kings Speech', 2010, 'Drama'),

			('The Shawshank Redemption', 1994, 'Drama'),
			('The Godfather', 1972, 'Drama'),
			('The Godfather: Part II', 1974, 'Action'),
			('The Dark Knight', 2008, 'Action'),
			('12 Angry Men', 1957, 'Drama'),
			('Schindlers List', 1993, 'Drama'),

			('Baahubali: The Beginning', 2015, 'Drama'),
			('Inception', 2010, 'Action'),
			('Interstellar', 2014, 'Drama'),
			('Intouchables', 2012, 'Drama'),
			('Inside Out', 2015, 'Animation'),
			('Whiplash', 2014, 'Drama');

-- Ratings
--insert into rating(mid, val, count)
--	values (1, 1, 1),
--			(1, 2, 5),
--			--(1, 3, 2),
--			(1, 4, 1),
--			(1, 5, 1),
--			(2, 1, 3),
--			(2, 2, 1),
--			(2, 3, 1),
--			(2, 4, 1),
--			(2, 5, 5),
--			(3, 1, 1),
--			(3, 2, 8),
--			(3, 3, 1),
--			(3, 4, 8),
--			(3, 5, 1);

-- Reviews
--insert into review(mid, id, reviewer_name, review_summary, review, rating)
--	values (1, 1, 'Nuno', 'Kickass Movie', 'review', 5),
--			(1, 2, 'Joao', 'Awesome Movie', 'review', 4),
--			(2, 1, 'Nuno', 'Not Worthy to see Movie', 'review', 1),
--			(2, 2, 'Andre', 'Normal Movie', 'review', 3),
--			(3, 1, 'Paulo', 'Kickass Movie', 'review', 5);

insert into collection(name, description)
	values ('Oscar Winners', 'Oscar Winners'),
			('Best 250 Movies', 'Best 250 Movies'),
			('Best Movies of 2016', 'Best Movies of 2016');

insert into movie_collection(cid, mid)
	values (1, 1),
		(1, 2),
		(1, 3),
		(1, 4),
		(1, 5),
		(1, 6),

		(2, 7),
		(2, 8),
		(2, 9),
		(2, 10),
		(2, 11),
		(2, 12),

		(3, 13),
		(3, 14),
		(3, 15),
		(3, 16),
		(3, 17),
		(3, 18);