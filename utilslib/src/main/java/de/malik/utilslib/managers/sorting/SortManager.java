package de.malik.utilslib.managers.sorting;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

public class SortManager {

    /**
     * sorts the given records by the given sort value
     * @param records the records which will be sorted
     * @param sortValue the value by which the records will be sorted
     * @return an array list of consisting of the sorted records
     * @throws NumberFormatException if the sort value is set to ether SortValue.ID_ASC or SortValue.ID_DESC
     *                               and the records do not consist of longs
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<String> sort(@NonNull ArrayList<String> records, @NonNull SortValue sortValue) throws NumberFormatException {
        if (records.size() == 0) {
            return new ArrayList<>();
        }
        ArrayList<String> sortedList = new ArrayList<>(records);
        if (sortValue == SortValue.DATE_OLD_TO_NEW || sortValue == SortValue.DATE_NEW_TO_OLD) {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
            df.setTimeZone(TimeZone.getDefault());
            sortedList.sort((o1, o2) -> {
                int result = 0;
                try {
                    if (sortValue == SortValue.DATE_OLD_TO_NEW)
                        result = df.parse(o1).compareTo(df.parse(o2));
                    else result = df.parse(o2).compareTo(df.parse(o1));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                return result;
            });
        }
        else if (sortValue == SortValue.STRING_A_TO_Z || sortValue == SortValue.STRING_Z_TO_A) {
            sortedList.sort((o1, o2) -> {
                int result = 0;
                if (sortValue == SortValue.STRING_A_TO_Z)
                    result =  o1.compareTo(o2);
                else result =  o2.compareTo(o1);
                return result;
            });
        }
        else if (sortValue == SortValue.ID_ASC ||sortValue == SortValue.ID_DESC) {
            ArrayList<Long> recordsAsNumbers = convertStringsToLongs(records);
            recordsAsNumbers.sort((o1, o2) -> {
                int result = 0;
                if (sortValue == SortValue.ID_ASC)
                    result = o1.compareTo(o2);
                else result = o2.compareTo(o1);
                return result;
            });
            sortedList = convertLongsToStrings(recordsAsNumbers);
        }
        return sortedList;
    }

    /**
     * converts the given list of strings to a list of longs
     * @param list the list of strings which will be converted
     * @return the resulting array list of longs
     * @throws NumberFormatException if any of the strings is not a long
     */
    private static ArrayList<Long> convertStringsToLongs(@NonNull ArrayList<String> list) throws NumberFormatException {
        ArrayList<Long> longList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            longList.add(Long.parseLong(list.get(i)));
        }
        return longList;
    }

    /**
     * converts the given list of longs to a list of strings
     * @param list the list of longs which will be converted
     * @return the resulting array list of strings
     */
    private static ArrayList<String> convertLongsToStrings(@NonNull ArrayList<Long> list) {
        ArrayList<String> stringsList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            stringsList.add(list.get(i) + "");
        }
        return stringsList;
    }
}
