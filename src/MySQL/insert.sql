insert into dipendente (ruolo, pin) values
('admin', 1234),
('cassiere', 5678),
('magazziniere', 9876),
('finanze', 5432);

insert into fornitura (giorno) values
('2024-01-11'),
('2024-01-12'),
('2024-01-13'),
('2024-01-14');

insert into prodotto (nome, marchio, prezzo, prezzo_scontato, inizio_sconto, fine_sconto, foto) values
('Pasta', 'Garofalo', 10.00, NULL, NULL, NULL, 'BLOB1'),
('Farina', 'Caputo', 15.00, NULL, NULL, NULL, 'BLOB2'),
('Cornetti', 'Kinder', 20.00, NULL, NULL, NULL, 'BLOB3'),
('Biscotti', 'Mulino Bianco', 25.00, NULL, NULL, NULL, 'BLOB4');

insert into lotto (costo, data_scadenza, quantita, quantita_attuale, fornitura, prodotto) values
(5.00, '2024-01-31', 100, 100, 1, 1),
(8.00, '2024-02-15', 50, 50, 2, 2),
(12.00, '2024-01-20', 200, 150, 3, 3),
(18.00, '2024-02-10', 75, 75, 4, 4);

insert into vendita (giorno) values
('2024-01-11'),
('2024-01-12'),
('2024-01-13'),
('2024-01-14');

insert into venduto (costo, quantita, guadagno, prodotto, giorno) values
(9.00, 2, 5.00, 1, '2024-01-11'),
(13.00, 1, 8.00, 2, '2024-01-12'),
(19.00, 3, 15.00, 3, '2024-01-13'),
(24.00, 2, 20.00, 4, '2024-01-14');

insert into esposizione (prodotto, lotto, quantita) values
(1, 1, 20),
(2, 2, 30),
(3, 3, 50),
(4, 4, 25);
