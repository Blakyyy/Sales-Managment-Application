package gestiondeventas;


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
            String[] parts = str.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            if (month >= 1 && month <= 12) {
                if (month == 2) { // February
                    if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) { // leap year
                        if (day <= 29) {
                            return str;
                        }
                    } else { // not a leap year
                        if (day <= 28) {
                            return str;
                        }
                    }
                } else if (month == 4 || month == 6 || month == 9 || month == 11) { // April, June, September, November
                    if (day <= 30) {
                        return str;
                    }
                } else { // January, March, May, July, August, October, December
                    if (day <= 31) {
                        return str;
                    }
                }
            }
        } else {
            String regex = "\\d{4}/\\d{2}/\\d{2}";
            if(str.matches(regex)){
                str = str.replaceAll("/", "-");
                String[] parts = str.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                if (month >= 1 && month <= 12) {
                    if (month == 2) { // February
                        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) { // leap year
                            if (day <= 29) {
                                return str;
                            }
                        } else { // not a leap year
                            if (day <= 28) {
                                return str;
                            }
                        }
                    } else if (month == 4 || month == 6 || month == 9 || month == 11) { // April, June, September, November
                        if (day <= 30) {
                            return str;
                        }
                    } else { // January, March, May, July, August, October, December
                        if (day <= 31) {
                            return str;
                        }
                    }
                }
            }
        }
        return "InvalidDateFormat";
    }
    
    }

