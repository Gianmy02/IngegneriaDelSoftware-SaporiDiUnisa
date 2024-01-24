package it.unisa.saporidiunisa.controller.finanze.servlet;

import it.unisa.saporidiunisa.model.entity.Dipendente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static org.mockito.Mockito.*;

class ImpostaScontoServletTest
{
    ImpostaScontoServlet servlet;
    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher dispatcher;

    @BeforeEach
    void beforeEach() throws ServletException, IOException
    {
        servlet = new ImpostaScontoServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        val session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        val dipendente = new Dipendente();
        dipendente.setRuolo(Dipendente.Ruolo.FINANZE);
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

    void populateRequest(String prodotto, String dataInizioSconto, String dataFineSconto, String sconto)
    {
        when(request.getParameter("prodotto")).thenReturn(prodotto);
        when(request.getParameter("dataInizioSconto")).thenReturn(dataInizioSconto);
        when(request.getParameter("dataFineSconto")).thenReturn(dataFineSconto);
        when(request.getParameter("sconto")).thenReturn(sconto);
    }

    @Test
    @DisplayName("5.2.1")
    void tc_5_2_1()
    {
        populateRequest("0", "2025-01-01", "2025-01-02", "1");
    }

    @Test
    @DisplayName("5.2.2")
    void tc_5_2_2()
    {
        populateRequest("5", "2025-01-01", "2025-01-02", "1");
    }

    @Test
    @DisplayName("5.2.3")
    void tc_5_2_3()
    {
        populateRequest("1", "2025-01-00", "2025-01-02", "1");
    }

    @Test
    @DisplayName("5.2.4")
    void tc_5_2_4()
    {
        populateRequest("1", "2020-01-01", "2025-01-02", "1");
    }

    @Test
    @DisplayName("5.2.5")
    void tc_5_2_5()
    {
        populateRequest("1", "2025-01-01", "2025-01-00", "1");
    }

    @Test
    @DisplayName("5.2.6")
    void tc_5_2_6()
    {
        populateRequest("1", "2025-01-02", "2025-01-01", "1");
    }

    @Test
    @DisplayName("5.2.7")
    void tc_5_2_7()
    {
        populateRequest("1", "2025-01-01", "2025-01-02", "-1");
    }

    @Test
    @DisplayName("5.2.8")
    void tc_5_2_8()
    {
        populateRequest("1", "2025-01-01", "2025-01-02", "101");
    }

    @Test
    @DisplayName("5.2.9")
    void tc_5_2_9()
    {
        populateRequest("1", "2025-01-01", "2025-01-02", "50a");
    }

    @Test
    @DisplayName("5.2.10")
    void tc_5_2_10()
    {
        populateRequest("1", "2025-01-01", "2025-01-02", "1");
    }
}
