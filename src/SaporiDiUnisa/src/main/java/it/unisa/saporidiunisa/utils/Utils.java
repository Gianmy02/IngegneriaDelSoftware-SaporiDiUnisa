package it.unisa.saporidiunisa.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.val;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

/**
 * La classe <code>Utils</code> ha al suo interno un insieme di metodi utili a tutti i programmatori per eliminare ridondanze e
 * migliorare la leggibilità
 */
public class Utils
{
    /**
     * Il metodo <code>dispatchError</code> reindirizza l'utente alla pagina di errore contenente un messaggio esplicativo
     * @param message stringa che spiega l'errore
     * @param request parametro della servlet nel quale mettere il messaggio e fare il dispatch
     * @param response parametro della servlet con il quale fare il forward con la request
      */
    public static void dispatchError(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("message", message);
        request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
    }

    /**
     * Il metodo <code>dispatchSuccess</code> reindirizza l'utente alla pagina di successo contenente un messaggio esplicativo
     * @param message stringa che spiega l'operazione andata a buon fine
     * @param request parametro della servlet nel quale mettere il messaggio e fare il dispatch
     * @param response parametro della servlet con il quale fare il forward con la request
     */

    public static void dispatchSuccess(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("message", message);
        request.getRequestDispatcher("WEB-INF/success.jsp").forward(request, response);
    }

    /**
     * Il metodo <code>sendMessage</code> invia un oggetto JSON contenente un messaggio esplicativo di errore
     * @param message stringa che spiega l'errore
     * @param response parametro nel quale scrivere il json
     */
    public static void sendMessage(String message, HttpServletResponse response) throws IOException
    {
        val json = new JSONObject();
        json.put("message", message);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        response.getWriter().write(json.toString());
    }

    /**
     * Il metodo <code>parseAsInteger</code> converte una stringa a oggetto Integer
     * @param str stringa da cambiare a tipo intero
     * @return intero o null in caso di cast andato male
     */
    public static Integer parseAsInteger(String str)
    {
        try
        {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    /**
     * Il metodo <code>parseAsFloat</code> converte una stringa a oggetto Float
     * @param str stringa da cambiare a tipo float
     * @return float o null in caso di cast andato male
     */
    public static Float parseAsFloat(String str)
    {
        try
        {
            return Float.parseFloat(str);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    /**
     * Il metodo <code>parseAsLocalDate</code> converte una stringa a oggetto LocalDate
     * @param str stringa da cambiare a tipo LocalDate
     * @return LocalDate o null in caso di cast andato male
     */
    public static LocalDate parseAsLocalDate(String str)
    {
        try
        {
            return LocalDate.parse(str);
        }
        catch (DateTimeParseException e)
        {
            return null;
        }
    }

    // Ottiene un oggetto Enum a partire dal suo nome

    public static <E extends Enum<E>> E getEnum(Class<E> enumClass, String name)
    {
        try
        {
            return Enum.valueOf(enumClass, name);
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
    }

    /**
     * Il metodo <code>readPart</code> converte un oggetto part a tipo Stringa
     * @param part part da cambiare a tipo stringa
     * @return stringa o null in caso di cast andato male
     */
    public static String readPart(final Part part)
    {
        try (val is = part.getInputStream();
             val reader = new BufferedReader(new InputStreamReader(is)))
        {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Il metodo <code>checkImageExtension</code> controlla se un oggetto Part è un'immagine con estensione jpg, jpeg o png
     * @param part immagine
     * @return booleano di conferma, true è uno dei tipi citati, false no
     */
    // TODO: https://stackoverflow.com/a/43891850
    public static boolean checkImageExtension(final Part part)
    {
        val contentDisposition = part.getHeader("content-disposition");
        val tokens = contentDisposition.split(";");

        for (val token : tokens)
        {
            if (token.trim().startsWith("filename"))
            {
                val fileName = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
                int lastDotIndex = fileName.lastIndexOf('.');
                if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1)
                {
                    val extension = fileName.substring(lastDotIndex + 1);
                    return switch (extension)
                    {
                        case "jpg", "jpeg", "png" -> true;
                        default -> false;
                    };
                }
            }
        }

        return false;
    }
}
