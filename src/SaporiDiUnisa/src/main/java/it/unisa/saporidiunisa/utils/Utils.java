package it.unisa.saporidiunisa.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Utils
{
    public static void dispatchError(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("message", message);
        request.getRequestDispatcher("view/error.jsp").forward(request, response);
    }

    // Converte un oggetto Part in una stringa
    public static String readPart(final Part part)
    {
        try (InputStream is = part.getInputStream();
             val reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Controlla se l'oggetto Part è un'immagine valida
    public static boolean isImage(final Part part)
    {
        val contentDisposition = part.getHeader("content-disposition");
        val tokens = contentDisposition.split(";");

        for (val token : tokens) {
            if (token.trim().startsWith("filename")) {
                val fileName = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
                int lastDotIndex = fileName.lastIndexOf('.');
                if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
                    val extension = fileName.substring(lastDotIndex + 1);
                    return switch (extension) {
                        case "jpg", "jpeg", "png" -> true;
                        default -> false;
                    };
                }
            }
        }
        return false;
    }
}
