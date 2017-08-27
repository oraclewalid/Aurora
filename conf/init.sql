drop table  if exists bloc;
drop table  if exists contract;
drop table  if exists deal;

create table deal(
   id SERIAL PRIMARY KEY,
   nomDeal text NOT NULL,
   startDate date ,
   lastDate date ,
   creationDate date,
   buyerOrSeller text

);

create table contract(
   id SERIAL PRIMARY KEY,
   dealId SERIAL REFERENCES deal,
   nomContrat text NOT NULL,
   nomClient text  NOT NULL,
   typeContrat text  NOT NULL,
   startDate date ,
   lastDate date ,
   creationDate date,
   buyerOrSeller text

);

create table bloc(
   id SERIAL PRIMARY KEY,
   contractId SERIAL REFERENCES contract,
   categorie text,
   clause text  NOT NULL,
   important boolean,
   validSuperviseur boolean,
   validCompliance boolean,
   kpiNbModif int
);
select * from bloc;

insert into deal("nomdeal", "startdate", "lastdate", "creationdate", "buyerorseller") values ('mysecond', '2017-12-12', '2017-12-12', '2017-12-12', 'Seller');
insert into contract("dealid","nomcontrat", "nomclient","typecontrat","startdate", "lastdate", "creationdate", "buyerorseller")
values (1,'buy vivendi', 'vivendi', 'buy','2017-12-12', '2017-12-12', '2017-12-12', 'Seller');

insert into bloc("contractid","categorie", "clause","important","validsuperviseur", "validcompliance", "kpinbmodif")
values              (1,'price', 'blabla juridique', true, true, true, 1);
commit;
SELECT *  FROM contract where id =1;

