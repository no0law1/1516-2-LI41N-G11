--CREATE DATABASE movies;
--CREATE DATABASE movies_test;
--drop table movie_collection;
--drop table rating;
--drop table review;
--drop table collection;
--drop table student;
--drop table movie;

--Multiple movies can have the same title.
--
--However, no two movies released in the same year
--	may have the same title.
create table student(
  number int primary key,
  name varchar(128)
);

create table movie(
	id serial primary key,
	title varchar(128),
	release_year int,
	genre varchar(128),

	unique(title, release_year)
);

create table review(
	mid int,
	id int,
	reviewer_name varchar(128),
	review_summary varchar(128),
	review varchar(256),
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
	foreign key (mid) references movie(id),
	constraint chk_rating check (val>=1 AND val<=5)
);

create table collection(
    id serial,
    name varchar(128),
    description varchar(256),

    unique(name),
    primary key (id)
);

create table movie_collection(
    cid int,
    mid int,

    primary key(cid, mid),
    foreign key (mid) references movie(id),
    foreign key (cid) references collection(id)
);