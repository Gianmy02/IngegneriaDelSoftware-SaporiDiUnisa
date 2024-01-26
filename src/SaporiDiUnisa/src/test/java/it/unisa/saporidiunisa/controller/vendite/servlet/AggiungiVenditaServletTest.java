package it.unisa.saporidiunisa.controller.vendite.servlet;

import it.unisa.saporidiunisa.controller.magazzino.MagazzinoController;
import it.unisa.saporidiunisa.controller.scaffale.ScaffaleController;
import it.unisa.saporidiunisa.model.entity.Dipendente;
import it.unisa.saporidiunisa.model.entity.Prodotto;
import it.unisa.saporidiunisa.utils.Utils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AggiungiVenditaServletTest
{
    AggiungiVenditaServlet servlet;
    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher dispatcher;


    @BeforeEach
    void beforeEach() throws ServletException, IOException
    {
        servlet = new AggiungiVenditaServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        val session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        val dipendente = new Dipendente();
        dipendente.setRuolo(Dipendente.Ruolo.CASSIERE);
        when(session.getAttribute("dipendente")).thenReturn(dipendente);

        dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(any(), any());

    }

    @AfterEach
    void afterEach() throws ServletException, IOException
    {
        try(val magazzinoController = mockStatic(MagazzinoController.class)){
            Prodotto prodotto = new Prodotto(1,"Farina", "Caputo", 1.00F,0, null,null, null);
            Prodotto prodotto2 = new Prodotto(2,"Farina", "Caputo", 1.00F,0, null,null, null);

            magazzinoController.when(() -> MagazzinoController.getProdottoById(1)).thenReturn(prodotto);
            magazzinoController.when(() -> MagazzinoController.getProdottoById(2)).thenReturn(prodotto2);
            try(val scaffaleController = mockStatic(ScaffaleController.class)){
                scaffaleController.when(() -> ScaffaleController.getEspostiByProdotto(prodotto)).thenReturn(10);
                scaffaleController.when(() -> ScaffaleController.getEspostiByProdotto(prodotto2)).thenReturn(1);
            }
        }

        try (val utils = mockStatic(Utils.class))
        {
            // Stubba Utils.sendMessage ma ottieni il messaggio di errore
            val captor = ArgumentCaptor.forClass(String.class);
            utils.when(() -> Utils.sendMessage(captor.capture(), eq(response))).thenAnswer(Answers.RETURNS_DEFAULTS);

            servlet.doPost(request, response);

            System.out.println(captor.getValue());
        }
    }

    void populateJson(String prodotto, String quantita, String prezzo) throws IOException
    {
        val jsonString = "[{\"productId\": " + prodotto + ", \"quantity\": " + quantita + ", \"price\": " + prezzo + "}]";

        // Creare un InputStream dalla stringa JSON
        val inputStream = new ByteArrayInputStream(jsonString.getBytes());

        // Creare un BufferedReader dallo InputStream
        val bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // Configurare il mock per restituire il BufferedReader
        when(request.getReader()).thenReturn(bufferedReader);
    }

    @Test
    @DisplayName("2.1.1")
    void tc_2_1_1() throws IOException
    {
        populateJson("0", "1", "1.00");
    }

    @Test
    @DisplayName("2.1.2")
    void tc_2_1_2() throws IOException
    {
        populateJson("1", "a", "1.00");
    }

    @Test
    @DisplayName("2.1.3")
    void tc_2_1_3() throws IOException
    {
        populateJson("1", "0", "1.00");
    }

    @Test
    @DisplayName("2.1.4")
    void tc_2_1_4() throws IOException
    {
        populateJson("1", "1000000", "1.00");
    }

    @Test
    @DisplayName("2.1.5")
    void tc_2_1_5() throws IOException
    {
        populateJson("2", "5", "1.00");
    }

    @Test
    @DisplayName("2.1.6")
    void tc_2_1_6() throws IOException
    {
        populateJson("1", "5", "10a");
    }

    @Test
    @DisplayName("2.1.7")
    void tc_2_1_7() throws IOException
    {
        populateJson("1", "5", "-1.00");
    }

    @Test
    @DisplayName("2.1.8")
    void tc_2_1_8() throws IOException
    {
        populateJson("1", "1", "100000.00");
    }

    @Test
    @DisplayName("2.1.9")
    void tc_2_1_9() throws IOException
    {
        populateJson("1", "1", "1.00");
    }


}