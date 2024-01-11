drop database if exists sapori_di_unisa;
create database sapori_di_unisa;
use sapori_di_unisa;

create table dipendente
(
    id int not null auto_increment primary key,
    ruolo enum('admin', 'cassiere', 'magazziniere', 'finanze') not null,
    pin int(4) not null
);

create table fornitura
(
    id int not null auto_increment primary key,
    giorno date not null /*poi sarà current_date all'inserimento*/
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

INSERT INTO dipendente (ruolo, pin) VALUES
('admin', 1234),
('cassiere', 5678),
('magazziniere', 9876),
('finanze', 5432);

INSERT INTO fornitura (giorno) VALUES
('2024-01-11'),
('2024-01-12'),
('2024-01-13'),
('2024-01-14');

INSERT INTO prodotto (nome, marchio, prezzo, prezzo_scontato, inizio_sconto, fine_sconto, foto) VALUES
('Pasta', 'Garofalo', 10.00, 8.50, '2024-01-15', '2024-01-20', 'BLOB1'),
('Farina', 'Caputo', 15.00, NULL, NULL, NULL, 'BLOB2'),
('Cornetti', 'Kinder', 20.00, 18.00, '2024-01-12', '2024-01-15', 'BLOB3'),
('Biscotti', 'Mulino Bianco', 25.00, NULL, NULL, NULL, 'BLOB4');

INSERT INTO lotto (costo, data_scadenza, quantita, quantita_attuale, fornitura, prodotto) VALUES
(5.00, '2024-01-31', 100, 100, 1, 1),
(8.00, '2024-02-15', 50, 50, 2, 2),
(12.00, '2024-01-20', 200, 150, 3, 3),
(18.00, '2024-02-10', 75, 75, 4, 4);

INSERT INTO vendita (giorno) VALUES
('2024-01-11'),
('2024-01-12'),
('2024-01-13'),
('2024-01-14');

INSERT INTO venduto (costo, quantita, guadagno, prodotto, giorno) VALUES
(9.00, 2, 5.00, 1, '2024-01-11'),
(13.00, 1, 8.00, 2, '2024-01-12'),
(19.00, 3, 15.00, 3, '2024-01-13'),
(24.00, 2, 20.00, 4, '2024-01-14');

INSERT INTO esposizione (prodotto, lotto, quantita) VALUES
(1, 1, 20),
(2, 2, 30),
(3, 3, 50),
(4, 4, 25);




