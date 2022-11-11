create sequence if not exists customers_id_seq;

alter sequence customers_id_seq owner to postgres;

create table if not exists users
(
    id                bigint default nextval('sensorsdb.customers_id_seq'::regclass) not null
        constraint customers_pk
            primary key,
    role              varchar(20)                                                    not null,
    creation_date     timestamp(6)                                                   not null,
    modification_date timestamp(6),
    is_deleted        boolean                                                        not null,
    termination_date  timestamp(6),
    login             varchar(30)                                                    not null,
    password          varchar                                                        not null
);

alter table users
    owner to postgres;

alter sequence customers_id_seq owned by users.id;

create unique index if not exists customers_id_uindex
    on users (id);

create sequence if not exists goods_sensors_id_seq;

alter sequence goods_sensors_id_seq owner to postgres;

create table if not exists sensors
(
    id                bigint default nextval('sensorsdb.goods_sensors_id_seq'::regclass) not null
        constraint goods_sensors_pk
            primary key,
    name              varchar(20)                                                        not null,
    model             varchar(30)                                                        not null,
    range_from        integer,
    range_to          integer,
    type              varchar(20)                                                        not null,
    locaion           varchar(20),
    description       varchar(15),
    creation_date     timestamp(6)                                                       not null,
    modification_date timestamp(6),
    is_deleted        boolean                                                            not null,
    termination_date  timestamp(6)
);

alter table sensors
    owner to postgres;

alter sequence goods_sensors_id_seq owned by sensors.id;

create unique index if not exists goods_guitars_id_uindex
    on sensors (id);

