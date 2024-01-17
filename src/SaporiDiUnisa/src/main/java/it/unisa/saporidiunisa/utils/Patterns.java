package it.unisa.saporidiunisa.utils;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Patterns
{
    public static final Pattern LOGIN_PIN = Pattern.compile("^[0-9]{4}$");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d/MM/uuuu");
}
