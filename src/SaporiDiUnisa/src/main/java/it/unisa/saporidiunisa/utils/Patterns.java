package it.unisa.saporidiunisa.utils;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Patterns
{
    public static final Pattern LOGIN_PIN = Pattern.compile("^[0-9]{4}$");
    public static final Pattern INPUT_STRING = Pattern.compile("^[a-zA-Z0-9\\s]{2,255}$");
    public static final Pattern PRICE = Pattern.compile("^(\\d{1,5})(\\.\\d{0,2})?$");
    public static final Pattern QUANTITY = Pattern.compile("^\\d+$");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d/MM/uuuu");
}
