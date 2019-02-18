create table if not exists route_definition
(
	id int auto_increment
		primary key,
	route json null
)
;