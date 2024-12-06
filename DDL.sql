drop table if exists AGGLOMERATE cascade;
drop table if exists AGGLOMERATE_INDIVIDUAL cascade;
drop table if exists AGGREGATE cascade;
drop table if exists AGGREGATED_EVENT cascade;
drop table if exists APPOINTED_EVENT cascade;
drop table if exists ASSEMBLED_EVENT cascade;
drop table if exists ATTRIBUTE cascade;
drop table if exists BIRTH_EVENT cascade;
drop table if exists BOUGHT_EVENT cascade;
drop table if exists BUILT_EVENT cascade;
drop table if exists CHANGED_EVENT cascade;
drop table if exists CLASS cascade;
drop table if exists CLASS_UNIQUELY_IDENTIFIABLE cascade;
drop table if exists CREATED_EVENT cascade;
drop table if exists CURRENCY cascade;
drop table if exists DEATH_EVENT cascade;
drop table if exists DECOMMISSIONED_EVENT cascade;
drop table if exists DELETED_EVENT cascade;
drop table if exists DESTROYED_EVENT cascade;
drop table if exists DISAGGREGATED_EVENT cascade;
drop table if exists DISASSEMBLED_EVENT cascade;
drop table if exists DISSOLVED_EVENT cascade;
drop table if exists DNA cascade;
drop table if exists EmployeeRole cascade;
drop table if exists EMPLOYMENT cascade;
drop table if exists EVENT cascade;
drop table if exists FORMED_EVENT cascade;
drop table if exists HUMAN cascade;
drop table if exists INDIVIDUAL cascade;
drop table if exists INSTALLED_EVENT cascade;
drop table if exists LANGUAGE cascade;
drop table if exists MEMBERSHIP cascade;
drop table if exists MODEL cascade;
drop table if exists MODEL_UNIQUELY_IDENTIFIABLE cascade;
drop table if exists ORGANISATION cascade;
drop table if exists OWNING cascade;
drop table if exists POSSIBLE_WORLD cascade;
drop table if exists POSSIBLE_WORLD_INDIVIDUAL cascade;
drop table if exists PROPERTY cascade;
drop table if exists PROPERTY_UNIQUELY_IDENTIFIABLE cascade;
drop table if exists REMOVED_EVENT cascade;
drop table if exists RESIGNIFIED_EVENT cascade;
drop table if exists ROLE cascade;
drop table if exists SCALAR_ATTRIBUTE cascade;
drop table if exists SCALAR_PROPERTY cascade;
drop table if exists SCALAR_PROPERTY_UNIQUELY_IDENTIFIABLE cascade;
drop table if exists SCRAPPED_EVENT cascade;
drop table if exists SIGNIFIER cascade;
drop table if exists SIGNIFYING cascade;
drop table if exists SOLD_EVENT cascade;
drop table if exists STARTED_EVENT cascade;
drop table if exists STATE cascade;
drop table if exists STOPPED_EVENT cascade;
drop table if exists TRANSFERRED_FROM_EVENT cascade;
drop table if exists TRANSFERRED_TO_EVENT cascade;
drop table if exists TRANSFERRING_OF_OWNERSHIP cascade;
drop table if exists UNIQUELY_IDENTIFIABLE cascade;
create table AGGLOMERATE (
    IDENTIFIER CHAR(36) not null,
    beginning_IDENTIFIER CHAR(36) unique,
    ending_IDENTIFIER CHAR(36) unique,
    primary key (IDENTIFIER)
);
create table AGGLOMERATE_INDIVIDUAL (
    AGGLOMERATE_IDENTIFIER CHAR(36) not null,
    parts_IDENTIFIER CHAR(36) not null unique,
    primary key (AGGLOMERATE_IDENTIFIER, parts_IDENTIFIER)
);
create table AGGREGATE (
    beginning_IDENTIFIER CHAR(36) unique,
    ending_IDENTIFIER CHAR(36) unique,
    IDENTIFIER varchar(255) not null,
    KIND VARCHAR(255) not null unique,
    QUANTITY VARCHAR(255) not null,
    primary key (IDENTIFIER)
);
create table AGGREGATED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table APPOINTED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table ASSEMBLED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table ATTRIBUTE (
    BEGINNING TIMESTAMP,
    ENDING TIMESTAMP,
    IDENTIFIER CHAR(36) not null,
    individual_IDENTIFIER CHAR(36),
    PROPERTY BLOB not null,
    primary key (IDENTIFIER)
);
create table BIRTH_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table BOUGHT_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table BUILT_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table CHANGED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table CLASS (
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table CLASS_UNIQUELY_IDENTIFIABLE (
    CLASS_IDENTIFIER CHAR(36) not null,
    members_IDENTIFIER CHAR(36) not null unique,
    primary key (CLASS_IDENTIFIER, members_IDENTIFIER)
);
create table CREATED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table CURRENCY (
    SYMBOL CHAR(1) not null,
    IDENTIFIER CHAR(36) not null,
    ABBREVIATION VARCHAR(3) not null,
    NAME VARCHAR(255) not null,
    primary key (IDENTIFIER)
);
create table DEATH_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table DECOMMISSIONED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table DELETED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table DESTROYED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table DISAGGREGATED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table DISASSEMBLED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table DISSOLVED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table DNA (
    IDENTIFIER CHAR(36) not null,
    DNA_VALUE LONGTEXT not null,
    primary key (IDENTIFIER)
);
create table EmployeeRole (
    IDENTIFIER CHAR(36) not null,
    NAME VARCHAR(255) not null,
    description varchar(255),
    primary key (IDENTIFIER)
);
create table EMPLOYMENT (
    IDENTIFIER CHAR(36) not null,
    beginning_IDENTIFIER CHAR(36) unique,
    employee_IDENTIFIER CHAR(36),
    employer_IDENTIFIER CHAR(36),
    ending_IDENTIFIER CHAR(36) unique,
    CONTRACT LONGTEXT not null,
    DESCRIPTION LONGTEXT not null,
    primary key (IDENTIFIER)
);
create table EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table FORMED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table HUMAN (
    IDENTIFIER CHAR(36) not null,
    beginning_IDENTIFIER CHAR(36) unique,
    dna_IDENTIFIER CHAR(36) unique,
    ending_IDENTIFIER CHAR(36) unique,
    languages_IDENTIFIER CHAR(36) unique,
    names_IDENTIFIER CHAR(36) unique,
    nativeLanguage_IDENTIFIER CHAR(36),
    primary key (IDENTIFIER)
);
create table INDIVIDUAL (
    IDENTIFIER CHAR(36) not null,
    beginning_IDENTIFIER CHAR(36) unique,
    ending_IDENTIFIER CHAR(36) unique,
    primary key (IDENTIFIER)
);
create table INSTALLED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table LANGUAGE (
    IDENTIFIER CHAR(36) not null,
    NAME VARCHAR(255) not null,
    primary key (IDENTIFIER)
);
create table MEMBERSHIP (
    IDENTIFIER CHAR(36) not null,
    beginning_IDENTIFIER CHAR(36) unique,
    ending_IDENTIFIER CHAR(36) unique,
    member_IDENTIFIER CHAR(36),
    role_IDENTIFIER CHAR(36),
    primary key (IDENTIFIER)
);
create table MODEL (
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table MODEL_UNIQUELY_IDENTIFIABLE (
    MODEL_IDENTIFIER CHAR(36) not null,
    things_IDENTIFIER CHAR(36) not null unique,
    primary key (MODEL_IDENTIFIER, things_IDENTIFIER)
);
create table ORGANISATION (
    IDENTIFIER CHAR(36) not null,
    beginning_IDENTIFIER CHAR(36) unique,
    ending_IDENTIFIER CHAR(36) unique,
    members_IDENTIFIER CHAR(36) unique,
    names_IDENTIFIER CHAR(36) unique,
    units_IDENTIFIER CHAR(36) unique,
    PURPOSE LONGTEXT not null,
    primary key (IDENTIFIER)
);
create table OWNING (
    IDENTIFIER CHAR(36) not null,
    beginning_IDENTIFIER CHAR(36) unique,
    ending_IDENTIFIER CHAR(36) unique,
    owned_IDENTIFIER CHAR(36),
    owner_IDENTIFIER CHAR(36),
    ACTIONS_DESCRIPTION LONGTEXT not null,
    primary key (IDENTIFIER)
);
create table POSSIBLE_WORLD (
    IDENTIFIER CHAR(36) not null,
    beginning_IDENTIFIER CHAR(36) unique,
    ending_IDENTIFIER CHAR(36) unique,
    primary key (IDENTIFIER)
);
create table POSSIBLE_WORLD_INDIVIDUAL (
    POSSIBLE_WORLD_IDENTIFIER CHAR(36) not null,
    parts_IDENTIFIER CHAR(36) not null unique,
    primary key (POSSIBLE_WORLD_IDENTIFIER, parts_IDENTIFIER)
);
create table PROPERTY (
    IDENTIFIER CHAR(36) not null,
    PROPERTY BLOB not null,
    primary key (IDENTIFIER)
);
create table PROPERTY_UNIQUELY_IDENTIFIABLE (
    PROPERTY_IDENTIFIER CHAR(36) not null,
    members_IDENTIFIER CHAR(36) not null unique,
    primary key (PROPERTY_IDENTIFIER, members_IDENTIFIER)
);
create table REMOVED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table RESIGNIFIED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table ROLE (
    IDENTIFIER CHAR(36) not null,
    NAME VARCHAR(255) not null,
    primary key (IDENTIFIER)
);
create table SCALAR_ATTRIBUTE (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    individual_IDENTIFIER CHAR(36),
    PROPERTY VARCHAR(255) not null,
    primary key (IDENTIFIER)
);
create table SCALAR_PROPERTY (
    IDENTIFIER CHAR(36) not null,
    PROPERTY VARCHAR(255) not null,
    primary key (IDENTIFIER)
);
create table SCALAR_PROPERTY_UNIQUELY_IDENTIFIABLE (
    SCALAR_PROPERTY_IDENTIFIER CHAR(36) not null,
    members_IDENTIFIER CHAR(36) not null unique,
    primary key (SCALAR_PROPERTY_IDENTIFIER, members_IDENTIFIER)
);
create table SCRAPPED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table SIGNIFIER (
    IDENTIFIER CHAR(36) not null,
    beginning_IDENTIFIER CHAR(36) unique,
    ending_IDENTIFIER CHAR(36) unique,
    language_IDENTIFIER CHAR(36),
    NAME LONGTEXT not null,
    primary key (IDENTIFIER)
);
create table SIGNIFYING (
    IDENTIFIER CHAR(36) not null,
    beginning_IDENTIFIER CHAR(36) unique,
    ending_IDENTIFIER CHAR(36) unique,
    language_IDENTIFIER CHAR(36),
    named_IDENTIFIER CHAR(36),
    ACTIONS_DESCRIPTION LONGTEXT not null,
    NAME LONGTEXT not null,
    primary key (IDENTIFIER)
);
create table SOLD_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table STARTED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table STATE (
    IDENTIFIER CHAR(36) not null,
    beginning_IDENTIFIER CHAR(36) unique,
    ending_IDENTIFIER CHAR(36) unique,
    individual_IDENTIFIER CHAR(36),
    primary key (IDENTIFIER)
);
create table STOPPED_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table TRANSFERRED_FROM_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table TRANSFERRED_TO_EVENT (
    BEGINNING timestamp(6) with time zone,
    ENDING timestamp(6) with time zone,
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
create table TRANSFERRING_OF_OWNERSHIP (
    IDENTIFIER CHAR(36) not null,
    beginning_IDENTIFIER CHAR(36) unique,
    ending_IDENTIFIER CHAR(36) unique,
    from_IDENTIFIER CHAR(36),
    to_IDENTIFIER CHAR(36),
    ACTIONS_DESCRIPTION LONGTEXT not null,
    primary key (IDENTIFIER)
);
create table UNIQUELY_IDENTIFIABLE (
    IDENTIFIER CHAR(36) not null,
    primary key (IDENTIFIER)
);
alter table if exists AGGLOMERATE
add constraint FKc2ero84e3v9toorbpvquyby0n foreign key (beginning_IDENTIFIER) references AGGREGATED_EVENT;
alter table if exists AGGLOMERATE
add constraint FKmm5pt2p5ww2o2hvjsroympo1g foreign key (ending_IDENTIFIER) references DISAGGREGATED_EVENT;
alter table if exists AGGLOMERATE_INDIVIDUAL
add constraint FKtqhamnmes9qoqmsp4hilkfvmq foreign key (parts_IDENTIFIER) references INDIVIDUAL;
alter table if exists AGGLOMERATE_INDIVIDUAL
add constraint FKs1xmtjnk51r0uj7b0hn2jlfku foreign key (AGGLOMERATE_IDENTIFIER) references AGGLOMERATE;
alter table if exists AGGREGATE
add constraint FKjmlcr29lvqntbelkr98dxlxte foreign key (beginning_IDENTIFIER) references AGGREGATED_EVENT;
alter table if exists AGGREGATE
add constraint FKdibhkprv4ps6xxkmc2a5162pf foreign key (ending_IDENTIFIER) references DISAGGREGATED_EVENT;
alter table if exists ATTRIBUTE
add constraint FKbx3e6xgmym3w0u9rqg0vem1rs foreign key (individual_IDENTIFIER) references INDIVIDUAL;
alter table if exists CLASS_UNIQUELY_IDENTIFIABLE
add constraint FKidoj3h7p0svg2r7rpqcdox2er foreign key (CLASS_IDENTIFIER) references CLASS;
alter table if exists EMPLOYMENT
add constraint FKtwl2491idc6puobild1wrs25 foreign key (beginning_IDENTIFIER) references APPOINTED_EVENT;
alter table if exists EMPLOYMENT
add constraint FKoj4pbw2kmah6j0i17o5l3yigp foreign key (employee_IDENTIFIER) references HUMAN;
alter table if exists EMPLOYMENT
add constraint FK74njls9709h6t8cxj72bbtmh3 foreign key (employer_IDENTIFIER) references ORGANISATION;
alter table if exists EMPLOYMENT
add constraint FK84wr3f0iuv3sfyqgera3cpxk1 foreign key (ending_IDENTIFIER) references REMOVED_EVENT;
alter table if exists HUMAN
add constraint FKmp85dhbow66ug4xjiy8pufedx foreign key (beginning_IDENTIFIER) references BIRTH_EVENT;
alter table if exists HUMAN
add constraint FKq5pe88xp3lq6w75t1ddwpa63n foreign key (dna_IDENTIFIER) references DNA;
alter table if exists HUMAN
add constraint FKknqx4p4gyqvtkecph3y0l71yf foreign key (ending_IDENTIFIER) references DEATH_EVENT;
alter table if exists HUMAN
add constraint FK279ux068bpja1uw5drdyra0xe foreign key (languages_IDENTIFIER) references CLASS;
alter table if exists HUMAN
add constraint FKe45r6rbu2gy24nmer7ncw5d4m foreign key (names_IDENTIFIER) references CLASS;
alter table if exists HUMAN
add constraint FK4j3tsqiklcw27l0xjgwcnot5k foreign key (nativeLanguage_IDENTIFIER) references LANGUAGE;
alter table if exists MEMBERSHIP
add constraint FKivdktxhrc07ohdunh979lep4b foreign key (beginning_IDENTIFIER) references APPOINTED_EVENT;
alter table if exists MEMBERSHIP
add constraint FKld3lx341k7b20g858l91vcetq foreign key (ending_IDENTIFIER) references REMOVED_EVENT;
alter table if exists MEMBERSHIP
add constraint FKin1w6g60x9cuj5p78lcvd5ylu foreign key (member_IDENTIFIER) references HUMAN;
alter table if exists MODEL_UNIQUELY_IDENTIFIABLE
add constraint FKp77023dy34ip8vt6nwk5ftd6m foreign key (MODEL_IDENTIFIER) references MODEL;
alter table if exists ORGANISATION
add constraint FKvjisnqq4xr8ves1vy10a8oeu foreign key (beginning_IDENTIFIER) references FORMED_EVENT;
alter table if exists ORGANISATION
add constraint FKbog2bho5fguf303xxkp8byem2 foreign key (ending_IDENTIFIER) references DISSOLVED_EVENT;
alter table if exists ORGANISATION
add constraint FKcn6o4ei2p3h183ld5mi2rhwo3 foreign key (members_IDENTIFIER) references CLASS;
alter table if exists ORGANISATION
add constraint FKi77bjgh39vly6gq38hvlh1ya5 foreign key (names_IDENTIFIER) references CLASS;
alter table if exists ORGANISATION
add constraint FKqdqruvc6dxc7jfytnape2wb67 foreign key (units_IDENTIFIER) references CLASS;
alter table if exists OWNING
add constraint FKekm03fr8w7wr9ef77crbvxni3 foreign key (beginning_IDENTIFIER) references TRANSFERRED_FROM_EVENT;
alter table if exists OWNING
add constraint FKoe4m5vetkefkcc96h1sg80ev1 foreign key (ending_IDENTIFIER) references TRANSFERRED_TO_EVENT;
alter table if exists OWNING
add constraint FKdvhbld4irtcv5vavfdie6nd0g foreign key (owned_IDENTIFIER) references INDIVIDUAL;
alter table if exists OWNING
add constraint FKsq5kj8hlup7ab9nep7qqs7nr foreign key (owner_IDENTIFIER) references INDIVIDUAL;
alter table if exists POSSIBLE_WORLD
add constraint FKph08b5lcko1tqoq6qwh4ul55d foreign key (beginning_IDENTIFIER) references CREATED_EVENT;
alter table if exists POSSIBLE_WORLD
add constraint FKe0pt20t2v4tw0kkggaq8xawiw foreign key (ending_IDENTIFIER) references DELETED_EVENT;
alter table if exists POSSIBLE_WORLD_INDIVIDUAL
add constraint FKh1dawstbxor0aro1r3dj4jfxr foreign key (parts_IDENTIFIER) references INDIVIDUAL;
alter table if exists POSSIBLE_WORLD_INDIVIDUAL
add constraint FKhv5gf9cfjbaufam7f1b8xhguc foreign key (POSSIBLE_WORLD_IDENTIFIER) references POSSIBLE_WORLD;
alter table if exists PROPERTY_UNIQUELY_IDENTIFIABLE
add constraint FKojcif1dxmp7uu29x6spmc9d5k foreign key (PROPERTY_IDENTIFIER) references PROPERTY;
alter table if exists SCALAR_ATTRIBUTE
add constraint FK9lm7gbxqkb05a8oxm9tw8tq01 foreign key (individual_IDENTIFIER) references INDIVIDUAL;
alter table if exists SCALAR_PROPERTY_UNIQUELY_IDENTIFIABLE
add constraint FK5vqw15tnppkkv2d83jrbb17hk foreign key (SCALAR_PROPERTY_IDENTIFIER) references SCALAR_PROPERTY;
alter table if exists SIGNIFIER
add constraint FK86ib7krdpl4he7n8pe9s28cq0 foreign key (beginning_IDENTIFIER) references RESIGNIFIED_EVENT;
alter table if exists SIGNIFIER
add constraint FKmn1ohhaqolxbp0pwbxn1bht6q foreign key (ending_IDENTIFIER) references RESIGNIFIED_EVENT;
alter table if exists SIGNIFIER
add constraint FKslvysmv7hmsxwnc4lkq3al89p foreign key (language_IDENTIFIER) references LANGUAGE;
alter table if exists SIGNIFYING
add constraint FKhawryhce2vkpra6v8d2pl90dy foreign key (beginning_IDENTIFIER) references RESIGNIFIED_EVENT;
alter table if exists SIGNIFYING
add constraint FK3weg4l2ovvfv4dtbosdxafklw foreign key (ending_IDENTIFIER) references RESIGNIFIED_EVENT;
alter table if exists SIGNIFYING
add constraint FK2p8nhpomjr470hdka2oral86v foreign key (language_IDENTIFIER) references LANGUAGE;
alter table if exists STATE
add constraint FKk023snfv98i3dav4o6qg3n0mc foreign key (individual_IDENTIFIER) references INDIVIDUAL;
alter table if exists TRANSFERRING_OF_OWNERSHIP
add constraint FKby6rotkxcwoyr7868wcjysabs foreign key (beginning_IDENTIFIER) references TRANSFERRED_FROM_EVENT;
alter table if exists TRANSFERRING_OF_OWNERSHIP
add constraint FKqvgxh2a3ecbacn0bdnn4ich5d foreign key (ending_IDENTIFIER) references TRANSFERRED_TO_EVENT;
alter table if exists TRANSFERRING_OF_OWNERSHIP
add constraint FK7d518ds1subqutqy6badf6ha4 foreign key (from_IDENTIFIER) references OWNING;
alter table if exists TRANSFERRING_OF_OWNERSHIP
add constraint FKm35lp0nhy968woavhpckbpm7v foreign key (to_IDENTIFIER) references OWNING;