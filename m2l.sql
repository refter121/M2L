/*==============================================================*/
/* Nom de SGBD :  MySQL 4.0                                     */
/* Date de cr�ation :  15/05/2024 15:57:54                      */
/*==============================================================*/


drop index APARTIENT2_FK on APARTIENT;

drop index APARTIENT_FK on APARTIENT;

drop index HERITAGECANDIDAT_FK on EQUIPE;

drop index PARTICIPE2_FK on PARTICIPE;

drop index PARTICIPE_FK on PARTICIPE;

drop index HERITAGECANDIDAT2_FK on PERSONNE;

drop table if exists APARTIENT;

drop table if exists CANDIDAT;

drop table if exists COMPETITION;

drop table if exists EQUIPE;

drop table if exists PARTICIPE;

drop table if exists PERSONNE;

/*==============================================================*/
/* Table : APARTIENT                                            */
/*==============================================================*/
create table APARTIENT
(
   IDCANDIDAT                     int                            not null,
   IDPERSONNE                     int                            not null,
   EQU_IDCANDIDAT                 int                            not null,
   IDEQUIPE                       int                            not null,
   primary key (IDCANDIDAT, IDPERSONNE, EQU_IDCANDIDAT, IDEQUIPE)
)
 ENGINE = InnoDB;

/*==============================================================*/
/* Index : APARTIENT_FK                                         */
/*==============================================================*/
create index APARTIENT_FK on APARTIENT
(
   IDCANDIDAT,
   IDPERSONNE
);

/*==============================================================*/
/* Index : APARTIENT2_FK                                        */
/*==============================================================*/
create index APARTIENT2_FK on APARTIENT
(
   EQU_IDCANDIDAT,
   IDEQUIPE
);

/*==============================================================*/
/* Table : CANDIDAT                                             */
/*==============================================================*/
create table CANDIDAT
(
   IDCANDIDAT                     int                            not null,
   primary key (IDCANDIDAT)
)
 ENGINE = InnoDB;

/*==============================================================*/
/* Table : COMPETITION                                          */
/*==============================================================*/
create table COMPETITION
(
   IDCOMPETITION                  int                            not null,
   primary key (IDCOMPETITION)
)
 ENGINE = InnoDB;

/*==============================================================*/
/* Table : EQUIPE                                               */
/*==============================================================*/
create table EQUIPE
(
   IDCANDIDAT                     int                            not null,
   IDEQUIPE                       int                            not null,
   NOM                            text,
   primary key (IDCANDIDAT, IDEQUIPE)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Index : HERITAGECANDIDAT_FK                                  */
/*==============================================================*/
create index HERITAGECANDIDAT_FK on EQUIPE
(
   IDCANDIDAT
);

/*==============================================================*/
/* Table : PARTICIPE                                            */
/*==============================================================*/
create table PARTICIPE
(
   IDCANDIDAT                     int                            not null,
   IDCOMPETITION                  int                            not null,
   primary key (IDCANDIDAT, IDCOMPETITION)
)
 ENGINE = InnoDB;

/*==============================================================*/
/* Index : PARTICIPE_FK                                         */
/*==============================================================*/
create index PARTICIPE_FK on PARTICIPE
(
   IDCANDIDAT
);

/*==============================================================*/
/* Index : PARTICIPE2_FK                                        */
/*==============================================================*/
create index PARTICIPE2_FK on PARTICIPE
(
   IDCOMPETITION
);

/*==============================================================*/
/* Table : PERSONNE                                             */
/*==============================================================*/
create table PERSONNE
(
   IDCANDIDAT                     int                            not null,
   IDPERSONNE                     int                            not null,
   NOMPERSONE                     varchar(20),
   PRENOMPERSONNE                 varchar(20),
   EMAIL                          varchar(100),
   primary key (IDCANDIDAT, IDPERSONNE)
)
 ENGINE = InnoDB;

/*==============================================================*/
/* Index : HERITAGECANDIDAT2_FK                                 */
/*==============================================================*/
create index HERITAGECANDIDAT2_FK on PERSONNE
(
   IDCANDIDAT
);

alter table APARTIENT add constraint FK_APARTIENT foreign key (IDCANDIDAT, IDPERSONNE)
      references PERSONNE (IDCANDIDAT, IDPERSONNE) on delete restrict on update restrict;

alter table APARTIENT add constraint FK_APARTIENT2 foreign key (EQU_IDCANDIDAT, IDEQUIPE)
      references EQUIPE (IDCANDIDAT, IDEQUIPE) on delete restrict on update restrict;

alter table EQUIPE add constraint FK_HERITAGECANDIDAT foreign key (IDCANDIDAT)
      references CANDIDAT (IDCANDIDAT) on delete restrict on update restrict;

alter table PARTICIPE add constraint FK_PARTICIPE foreign key (IDCANDIDAT)
      references CANDIDAT (IDCANDIDAT) on delete restrict on update restrict;

alter table PARTICIPE add constraint FK_PARTICIPE2 foreign key (IDCOMPETITION)
      references COMPETITION (IDCOMPETITION) on delete restrict on update restrict;

alter table PERSONNE add constraint FK_HERITAGECANDIDAT2 foreign key (IDCANDIDAT)
      references CANDIDAT (IDCANDIDAT) on delete restrict on update restrict;

