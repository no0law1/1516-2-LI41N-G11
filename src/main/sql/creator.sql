--Multiple movies can have the same title.
--
--However, no two movies released in the same year
--	may have the same title.
create table movie(
	id int serial primary key,
	title varchar(123),
	release_year datetime, --YYYY-MM-00 Ano e Mês
	genre varchar(123)
)

create table review(
	id int serial primary key,
	reviewer_name varchar(123),
	review_summary varchar(123),
	review varchar(255),
	rating int not null,
	movie_id int not null,

	foreign key movie_id references movie(id),
	constraint chk_rating check (rating>=1 AND rating<=5)
)

create table rating(
	numb int,
	movie_id int,
	count int,

	foreign key movie_id references movie(id),
	primary key(numb, movie_id)
)