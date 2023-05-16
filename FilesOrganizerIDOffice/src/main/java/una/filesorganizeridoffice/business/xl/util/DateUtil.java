package una.filesorganizeridoffice.business.xl.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    private static final LocalDate startDate = LocalDate.of(1900, 1, 1);
    public static LocalDate toDate(String value) {
        double dias = Double.parseDouble(value);
        LocalDate date = startDate.plusDays((long) dias - 1);
        return date;
    }

    public static String toString(LocalDate date) {

        long dias = ChronoUnit.DAYS.between(startDate, date);
        return Long.toString(dias);
    }

    public static boolean isDate(String value) {
        if (value == null || !value.matches("[0-9]+(\\.[0-9]+)?")) {
            return false;
        }
        double days = Double.parseDouble(value);
        if (days <= 0) {
            return false;
        }
        LocalDate currentDate = LocalDate.now();
        if (toDate(value).isAfter(currentDate)) {
            return false;
        }
        return true;
    }
}
