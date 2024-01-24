package it.unisa.saporidiunisa.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.val;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

public class Utils
{
    // Reindirizza l'utente alla pagina di errore contenente un messaggio esplicativo
    public static void dispatchError(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("message", message);
        request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
    }

    // Reindirizza l'utente alla pagina di successo contenente un messaggio esplicativo
    public static void dispatchSuccess(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("message", message);
        request.getRequestDispatcher("WEB-INF/success.jsp").forward(request, response);
    }

    // Invia un oggetto JSON contenente un messaggio esplicativo di errore
    public static void sendMessage(String message, HttpServletResponse response) throws IOException
    {
        val json = new JSONObject();
        json.put("message", message);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        response.getWriter().write(json.toString());
    }

    // Converte una stringa a oggetto Integer
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

    // Converte una stringa a oggetto Float
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

    // Converte una stringa a oggetto LocalDate
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

    // Converte un oggetto Part in una stringa
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

    // Controlla se un oggetto Part è un'immagine con estensione jpg, jpeg o png
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

    // Controlla se un oggetto Part è un'immagine con dimensioni 1:1
    public static boolean assureSquareImage(final Part part)
    {
        try (val fileContent = part.getInputStream())
        {
            val image = ImageIO.read(fileContent);
            return image != null && image.getWidth() == image.getHeight();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
