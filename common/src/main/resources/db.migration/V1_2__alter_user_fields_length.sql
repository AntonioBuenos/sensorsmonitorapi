alter table sensors rename column locaion to location;
alter table sensors
    alter column name type varchar(30),
    alter column model type varchar(15),
    alter column location type varchar(40),
    alter column description type varchar(200);
