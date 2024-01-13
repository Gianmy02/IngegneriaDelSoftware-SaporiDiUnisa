drop database if exists sapori_di_unisa;
create database sapori_di_unisa;
use sapori_di_unisa;

create table dipendente
(
    id int not null auto_increment primary key,
    ruolo enum('admin', 'cassiere', 'magazziniere', 'finanze') not null,
    pin char(4) not null
);

create table fornitura
(
    id int not null auto_increment primary key,
    giorno date not null
);

create table prodotto
(
    id int not null auto_increment primary key,
    nome varchar(255) not null,
    marchio varchar(255) not null,
    prezzo decimal(5,2) not null,
    prezzo_scontato decimal(5,2),
    inizio_sconto date,
    fine_sconto date,
    foto mediumblob not null,

    check(prezzo >= 0),
    check(prezzo_scontato > 0 and prezzo_scontato < prezzo),
    check(inizio_sconto <= fine_sconto)
);

create table lotto
(
    id int not null auto_increment primary key,
    costo decimal(5,2) not null,
    data_scadenza date not null,
    quantita int not null,
    quantita_attuale int not null,
    fornitura int not null references fornitura(id) on delete cascade,
    prodotto int not null references prodotto(id) on delete cascade,

    check(costo > 0.00),
    check(quantita > 0),
    check(quantita_attuale >= 0)
);

create table vendita
(
    giorno date not null primary key
);

create table venduto
(
    costo decimal(5,2) not null,
    quantita int not null,
    guadagno decimal(5,2) not null,
    prodotto int not null references prodotto(id) on delete cascade,
    giorno date not null references vendita(giorno),

    primary key(giorno, prodotto),

    check(costo > 0.00),
    check(quantita > 0),
    check(guadagno > 0.00)
);

create table esposizione
(
    prodotto int not null references prodotto(id) on delete cascade,
    lotto int not null references lotto(id) on delete cascade,
    quantita int not null,

    primary key(lotto, prodotto),
    
    check(quantita > 0)
);

delimiter |
create trigger check_inizio_sconto
    before insert on prodotto
    for each row
	begin
		if new.inizio_sconto <= current_date() then
			signal sqlstate '45000'
				set message_text = "La data di inizio_sconto deve essere maggiore di quella odierna";
		end if;
	end;
|
delimiter ;