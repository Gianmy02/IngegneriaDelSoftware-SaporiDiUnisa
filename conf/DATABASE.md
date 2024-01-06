# Sapori di Unisa
## Configurazione: Database
### Effettuare la connessione a database MySQL tramite JDBC
1. Copiare il file [database.properties](../src/Database/database.properties) in `src/SaporiDiUnisa/src/main/resources` (creare la cartella `resources` qualora non esistesse).
2. Popolare il file appena copiato con le proprie credenziali di accesso del database.
3. Verificare che il test `DatabaseTest` dia esito positivo.

#### Nota
Il proprio file `resources/database.properties` non verrà versionato.
