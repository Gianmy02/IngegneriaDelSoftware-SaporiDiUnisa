package it.unisa.saporidiunisa.controller;

import it.unisa.saporidiunisa.model.entity.Dipendente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.val;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public abstract class ServletTest
{
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected void init()
    {
        this.request = mock();
        this.response = mock();
    }

    protected void mockSession()
    {
        val session = mock(HttpSession.class);
        when(this.request.getSession()).thenReturn(session);
    }

    protected void mockDipendente(final Dipendente.Ruolo ruolo)
    {
        val dipendente = new Dipendente();
        dipendente.setRuolo(ruolo);
        when(this.request.getSession().getAttribute("dipendente")).thenReturn(dipendente);
    }

    protected void mockDispatcher()
    {
        val dispatcher = mock(RequestDispatcher.class);
        when(this.request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    protected void populateRequest(final Map<String, String> parameters)
    {
        parameters.forEach((key, value) -> when(this.request.getParameter(key)).thenReturn(value));
    }
}
