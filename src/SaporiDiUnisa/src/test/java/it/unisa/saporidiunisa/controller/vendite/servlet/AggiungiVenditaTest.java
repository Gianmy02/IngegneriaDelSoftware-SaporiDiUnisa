package it.unisa.saporidiunisa.controller.vendite.servlet;

import it.unisa.saporidiunisa.model.entity.Dipendente;
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
import org.mockito.ArgumentCaptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AggiungiVenditaTest {

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
        servlet.doPost(request, response);


        verify(dispatcher, atLeastOnce()).forward(request, response);

        val captor = ArgumentCaptor.forClass(String.class);
        verify(request, times(1)).setAttribute(eq("message"), captor.capture());

        System.out.println(captor.getValue());
    }

    void populateJson(String prodotto, String quantita, String prezzo) throws IOException {
        String jsonData = "[{\"productId\": " + prodotto + ", \"quantity\": " + quantita + ", \"price\": " + prezzo + "}]";

        // Creare un InputStream dalla stringa JSON
        ByteArrayInputStream inputStream = new ByteArrayInputStream(jsonData.getBytes());

        // Creare un BufferedReader dallo InputStream
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // Configurare il mock per restituire il BufferedReader
        when(request.getReader()).thenReturn(bufferedReader);
    }

    @Test
    @DisplayName("2.1.1")
    void tc_2_1_1() throws IOException {
        populateJson("0", "1", "1.00");
    }
}