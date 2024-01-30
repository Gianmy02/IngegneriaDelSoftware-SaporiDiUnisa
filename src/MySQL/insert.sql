use sapori_di_unisa;
insert into fornitura (giorno) values
('2024-01-11');

insert into prodotto (nome, marchio, prezzo, prezzo_scontato, inizio_sconto, fine_sconto, foto) values
('Pasta', 'Garofalo', 10.00, NULL, NULL, NULL, 'BLOB1');

insert into lotto (costo, data_scadenza, quantita, quantita_attuale, fornitura, prodotto) values
(500.00, '2025-01-31', 100, 80, 1, 1),
(500.00, '2025-01-31', 100, 100, 1, 1);

insert into esposizione (prodotto, lotto, quantita) values
(1, 1, 20);
