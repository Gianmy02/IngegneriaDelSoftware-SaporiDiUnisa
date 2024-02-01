package it.unisa.saporidiunisa.unit;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.model.dao.EsposizioneDAO;
import it.unisa.saporidiunisa.model.dao.LottoDAO;
import it.unisa.saporidiunisa.model.entity.Esposizione;
import it.unisa.saporidiunisa.model.entity.Lotto;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

public class EliminaLottoTest {

    @Nested
    class eliminaLottoMagazzino{
        final int ID_LOTTO = 2;
        Lotto lotto, lottoTmp;

        @BeforeEach
        void init() {
            lotto = LottoDAO.getLottoById(ID_LOTTO);
        }

        @Test
        void test() {
            Assertions.assertTrue(MagazzinoController.eliminaLotto(lotto.getId()));

            lottoTmp = LottoDAO.getLottoById(lotto.getId());
            Assertions.assertEquals(LocalDate.now().minusDays(1), lottoTmp.getDataScadenza());
        }

        @AfterEach
        void rollback() {
            LottoDAO.updateDataScadenza(lottoTmp, lotto.getDataScadenza());
        }
    }

    @Nested
    class eliminaLottoEsposizione{
        final int ID_LOTTO = 1;
        Lotto lotto, lottoTmp;
        Esposizione esposizione;

        @BeforeEach
        void init() {
            lotto = LottoDAO.getLottoById(ID_LOTTO);
            esposizione = EsposizioneDAO.getEsposizioneByLotto(lotto);
        }

        @Test
        void test() {
            Assertions.assertTrue(MagazzinoController.eliminaLotto(lotto.getId()));

            lottoTmp = LottoDAO.getLottoById(lotto.getId());
            Assertions.assertEquals(LocalDate.now().minusDays(1), lottoTmp.getDataScadenza());
            Assertions.assertNull(EsposizioneDAO.getEsposizioneByLotto(lotto));
        }

        @AfterEach
        void rollback() {
            LottoDAO.updateDataScadenza(lottoTmp, lotto.getDataScadenza());
            EsposizioneDAO.inserisciEsposizione(esposizione.getQuantita(), lotto);
        }
    }
}