--CREATE DATABASE movies;

--Multiple movies can have the same title.
--
--However, no two movies released in the same year
--	may have the same title.
create table movie(
	id serial primary key,
	title varchar(123),
	release_year int, --YYYY-MM-00 Ano e Mês
	genre varchar(123)
);

create table review(
	mid int,
	id int,
	reviewer_name varchar(123),
	review_summary varchar(123),
	review varchar(255),
	rating int not null,

	primary key(mid, id),
	foreign key (mid) references movie(id),
	constraint chk_rating check (rating>=1 AND rating<=5)
);

create table rating(
	mid int,
	val int,
	count int not null,
	
	primary key(mid, val),
	foreign key (mid) references movie(id)
);