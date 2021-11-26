package de.malik.utilslib.managers.files;

import android.nfc.FormatException;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileManager {

    /**
     * the tag used to output text with Log class
     */
    public static final String TAG = FileManager.class.getName();

    /**
     * an char array which contains all the reserved chars for file or folder names
     */
    public static final char[] RESERVED_CHARS = new char[] {
            '|', '\\', '?', '*', '<', '\"', ':', '>', '+', '[', ']', '/', '\''
    };

    /**
     * all the reserved chars for file or folder names summed up in a string
     */
    public static final String RESERVED_CHARS_AS_STRING = FileManagerHelper.convertCharsToString(RESERVED_CHARS);

    /**
     * a HashMap which contains all the files which were created with this library
     */
    public static final Map<String, File> CREATED_FILES = new HashMap<>();

    /**
     * a HasMap which contains all the folder which were created with this library
     */
    public static final Map<String, File> CREATED_FOLDERS = new HashMap<>();

    /**
     * if and only if a folder with that name does not exist at the given path, a new folder will be created.
     * The created folder will be added to the <code>CREATED_FOLDERS</code> map.
     * @param folderPath the path where the folder will be created
     * @param folderName the name of the new folder
     * @return the newly created folder
     * @throws FormatException if the folder name contains a reserved char or file name is empty
     */
    public static File createFolder(@NonNull String folderPath, @NonNull String folderName) throws FormatException {
        if (folderPath.isEmpty() || folderName.isEmpty()) {
            throw new FormatException("folder path and folder name cannot be empty");
        }
        if (FileManagerHelper.containsChar(folderName, RESERVED_CHARS)) {
            throw new FormatException("The folder name cannot contain any of these chars:" + RESERVED_CHARS_AS_STRING);
        }
        File folder = new File(folderPath, folderName);
        boolean wasCreated = false;

        if (!folder.exists()) {
            wasCreated = folder.mkdir();
        }
        if (!wasCreated) {
            Log.w(TAG, "Folder \"" + folderName + "\" was not created. Does it already exist?");
        }
        CREATED_FOLDERS.put(folderName, folder);
        return folder;
    }

    /**
     * if and only if a file with that name does not exist in the given folder, a new file will be created.
     * The created file will be added to the <code>CREATED_FILES</code> map.
     * @param folder the folder where the file will be created
     * @param fileName the name of the new file
     * @return the newly created file
     * @throws FormatException if the file name contains a reserved char of file name is empty
     * @throws IOException if an I/O error occurred
     */
    public static File createFile(@NonNull File folder, @NonNull String fileName) throws FormatException, IOException {
        if (fileName.isEmpty()) {
            throw new FormatException("file name cannot be empty");
        }
        if (FileManagerHelper.containsChar(fileName, RESERVED_CHARS)) {
            throw new FormatException("The file name cannot contain any of these chars:" + RESERVED_CHARS_AS_STRING);
        }
        File file = new File(folder, fileName);
        boolean wasCreated = file.createNewFile();
        if (!wasCreated) {
            Log.w(TAG, "File \"" + fileName + "\" was not created. Does it already exist?");
        }
        CREATED_FILES.put(fileName, file);
        return file;
    }

    /**
     * creates a new instance of the UtilsLibrary reader class
     * @return a new instance of ULReader class
     */
    public static ULReader getReader() {
        return new ULReader();
    }

    /**
     * creates a new instance of the UtilsLibrary printer class
     * @return a new instance of ULPrinter class
     */
    public static ULPrinter getPrinter() {
        return new ULPrinter();
    }
}
