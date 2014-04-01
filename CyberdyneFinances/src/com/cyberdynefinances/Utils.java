package com.cyberdynefinances;

import java.text.DecimalFormat;

/**
 * Utility Class.
 * @author Cyberdyne Finances
 */
public class Utils 
{
    //CHECKSTYLE:OFF    suppress error wrong name format
    public static final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    public static final DecimalFormat formatWithNeg = new DecimalFormat("+\u00A4###,##0.00;-\u00A4###,##0.00"); //"\u00A4" is the code for the currency symbol for the current local
    public static final DecimalFormat formatPos = new DecimalFormat("\u00A4###,##0.00;\u00A4###,##0.00");
    //CHECKSTYLE:ON
}
