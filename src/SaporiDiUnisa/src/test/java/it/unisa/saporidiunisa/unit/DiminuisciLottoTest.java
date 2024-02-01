package it.unisa.saporidiunisa.unit;

import it.unisa.saporidiunisa.controller.scaffale.ScaffaleController;
import it.unisa.saporidiunisa.model.dao.LottoDAO;
import it.unisa.saporidiunisa.model.entity.Lotto;
import org.junit.jupiter.api.*;


public class DiminuisciLottoTest {

    @Nested
    class diminuisciLottoMagazzino{
        final int ID_LOTTO = 1;
        final int QNT = 3;
        int qntIniziale;
        int qntAttuale;
        Lotto lotto;

        @BeforeEach
        void init() {
            lotto = LottoDAO.getLottoById(ID_LOTTO);
            assert lotto != null;
            qntIniziale = lotto.getQuantitaAttuale();
        }

        @Test
        void test() {
            ScaffaleController.diminuisciLotto(ID_LOTTO, QNT);
            qntAttuale = LottoDAO.getLottoById(ID_LOTTO).getQuantitaAttuale();
            Assertions.assertEquals(qntIniziale - QNT, qntAttuale);
        }
    }

}
