package gestiondeventas;

import java.text.DateFormat;
import java.util.Date;


public class Model_AddSale {
    public static String checkForComa(String str){
        if(str.contains(",")){
            str.replace(",", ".");
        }
        return str;
    }

    public static boolean checkForInt(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkForDouble(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String checkForDate(String str){
            boolean isValidFormat = str.matches("\\d{4}-\\d{2}-\\d{2}");
            if (isValidFormat) {
                return str;
            } else {
                String regex = "\\d{4}/\\d{2}/\\d{2}";
                if(str.matches(regex)){
                    str = str.replaceAll("/", "-");
                    return str;
                }
                
            }
            return "InvalidDateFormat";
        } 
    }

