create table if not exists mydb.commercial_area
(
    id         bigint auto_increment
    primary key,
    area_name  varchar(50)    not null,
    rental_fee decimal(38, 2) not null,
    geom       polygon        not null
    );

create spatial index geom
    on mydb.commercial_area (geom);