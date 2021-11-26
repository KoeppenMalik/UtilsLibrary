package de.malik.utilslib.managers.files;

import androidx.annotation.NonNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ULPrinter {

    /**
     * prints all the given records into the given file
     * @param file the file where the records will be printed in
     * @param append if true, the file will keep all it's content, otherwise the content will be overridden
     * @param records the records which will be printed into the file
     * @throws IOException if an I/O error occurred
     */
    public void print(@NonNull File file, boolean append, @NonNull String... records) throws IOException {
        if (records.length == 0) return;
        print(file, append, new ArrayList<>(Arrays.asList(records)));
    }

    /**
     * prints all the given records into the given file
     * @param file the file where the records will be printed in
     * @param append if true, the file will keep all it's content, otherwise the content will be overridden
     * @param records the records which will be printed into the file
     * @throws IOException if an I/O error occurred
     */
    public void print(@NonNull File file, boolean append, @NonNull Collection<? extends String> records) throws IOException {
        if (records.size() == 0) return;
        PrintWriter printer = createPrinter(file, append);
        try {
            for (String record : records) {
                printer.println(record);
            }
        } finally {
            printer.flush();
            printer.close();
        }
    }

    /**
     * creates a print writer which will write into the given file
     * @param file the file which will be written in
     * @param append if false, the printer will override all the content the file contains. If true,
     *               the file will keep all the content
     * @return a print writer which will write into the given file
     * @throws IOException if an I/O error occurred
     */
    private PrintWriter createPrinter(@NonNull File file, boolean append) throws IOException {
        return new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
    }
}
