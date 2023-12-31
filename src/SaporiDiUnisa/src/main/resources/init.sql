create database if not exists saporiDiUnisa;
use saporiDiUnisa;

create table if not exists dipendente(
    id int not null auto_increment primary key,
    ruolo enum('admin', 'cassiere', 'magazziniere', 'finanze') not null,
    pin char(4) not null
);

create table if not exists fornitura(
    id int not null auto_increment primary key,
    giorno date not null
);

create table if not exists prodotto(
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
    # check(inizio_sconto > current_date),
    check(inizio_sconto <= fine_sconto)
);

create trigger if not exists check_inizio_sconto
    before insert on prodotto
    for each row
begin
    if NEW.inizio_sconto <= CURRENT_DATE THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La data di inizio_sconto deve essere maggiore di quella odierna';
    end if;
end;

create table if not exists lotto(
    id int not null auto_increment primary key,
    costo decimal(5,2) not null,
    data_scadenza date not null,
    quantita int not null,
    quantita_attuale int not null,
    fornitura int not null references fornitura(id),
    prodotto int not null references prodotto(id),
    check(costo > 0),
    check(quantita > 0),
    check(quantita_attuale >= 0)
);

create table if not exists vendita(
    giorno date not null primary key
);

create table if not exists vendita_prodotto(
    costo decimal(5,2) not null,
    quantita int not null,
    guadagno decimal(5,2) not null,
    prodotto int not null references prodotto(id) on delete cascade,
    giorno date not null references vendita(giorno),
    primary key(giorno, prodotto),
    check(costo > 0),
    check(quantita > 0),
    check(guadagno > 0)
);

create table if not exists esposizione(
    prodotto int not null references prodotto(id) on delete cascade,
    lotto int not null references lotto(id) on delete cascade,
    quantita int not null,
    primary key(lotto, prodotto),
    check(quantita > 0)
);