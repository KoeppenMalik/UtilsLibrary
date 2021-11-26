package de.malik.utilslib.managers.files;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ULReader {

    /**
     * reads the lines of the given file and returns an ArrayList of String containing all the content of the file
     * @param file the file which will be read
     * @return an ArrayList of String containing all the content of the file
     * @throws IOException if an I/O error occurred
     */
    public ArrayList<String> readLines(@NonNull File file) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader = createReader(file);
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            reader.close();
        }
        return lines;
    }

    /**
     * creates a buffered reader which will read the given file
     * @param file the file which will be read
     * @return a buffered reader which will read the content of the given file
     * @throws IOException if an I/O error occurred
     */
    private BufferedReader createReader(@NonNull File file) throws IOException {
        return new BufferedReader(new FileReader(file));
    }
}
