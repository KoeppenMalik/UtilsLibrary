package de.malik.utilslib.managers.files;

import androidx.annotation.NonNull;

public class FileManagerHelper {

    /**
     * converts the given chars into a whole string
     * @param chars the chars that will be converted
     * @return a whole string consisting of the given chars
     */
    protected static String convertCharsToString(@NonNull char... chars) {
        String resultString = "";
        for (char c : chars) {
            resultString += " " + c;
        }
        return resultString;
    }

    /**
     * checks if the given string contains any of the given chars
     * @param source the string that will be checked
     * @param chars the string will be checked if it contains even one of them
     * @return true if the string contains even one of the given chars, false otherwise
     */
    protected static boolean containsChar(@NonNull String source, @NonNull char... chars) {
        if (source.length() == 0 ||chars.length == 0) {
            return false;
        }
        boolean containsChar = false;
        for (char sourceChar : source.toCharArray()) {
            for (char c : chars) {
                if (sourceChar == c) {
                    containsChar = true;
                    break;
                }
            }
        }
        return containsChar;
    }
}
