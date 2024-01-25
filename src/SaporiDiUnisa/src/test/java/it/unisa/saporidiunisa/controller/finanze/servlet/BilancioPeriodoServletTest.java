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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BilancioPeriodoServletTest
{
    BilancioPeriodoServlet servlet;
    HttpServletRequest request;
    HttpServletResponse response;

    @BeforeEach
    void beforeEach() throws ServletException, IOException
    {
        servlet = new BilancioPeriodoServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        val session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        val dipendente = new Dipendente();
        dipendente.setRuolo(Dipendente.Ruolo.FINANZE);
        when(session.getAttribute("dipendente")).thenReturn(dipendente);

        val requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(any(), any());
    }

    void populateRequest(String inizio, String fine)
    {
        when(request.getParameter("inizio")).thenReturn(inizio);
        when(request.getParameter("fine")).thenReturn(fine);
    }

    @Nested
    class WithMessage
    {
        @AfterEach
        void afterEach() throws ServletException, IOException
        {
            servlet.doPost(request, response);

            val captor = ArgumentCaptor.forClass(String.class);
            verify(request, times(1)).setAttribute(eq("message"), captor.capture());

            System.out.println(captor.getValue());
        }

        @Test
        @DisplayName("5.1.1")
        void tc_5_1_1()
        {
            populateRequest("2024-01/01", "2024-02-01");
        }

        @Test
        @DisplayName("5.1.2")
        void tc_5_1_2()
        {
            populateRequest("2025-01-01", "2024-02-01");
        }

        @Test
        @DisplayName("5.1.3")
        void tc_5_1_3()
        {
            populateRequest("2024-01-01", "2024-02/01");
        }

        @Test
        @DisplayName("5.1.4")
        void tc_5_1_4()
        {
            populateRequest("2024-01-10", "2024-01-01");
        }

        @Test
        @DisplayName("5.1.5")
        void tc_5_1_5()
        {
            populateRequest("2024-01-01", "2025-01-01");
        }
    }

    @Nested
    class WithoutMessage
    {
        @AfterEach
        void afterEach() throws ServletException, IOException
        {
            servlet.doPost(request, response);

            verify(request, never()).setAttribute(eq("message"), any());
        }

        @Test
        @DisplayName("5.1.6")
        void tc_5_1_6()
        {
            populateRequest("2024-01-10", "2024-01-12");
        }
    }
}
