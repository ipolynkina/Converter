package ru.ipolynkina.converter.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Property {

    private static String dirIn;
    private static String dirOut;

    private Property(){}

    public static String getDirIn() {
        return dirIn;
    }

    public static void setDirIn(String directoryIn) {
        dirIn = directoryIn;
    }

    public static String getDirOut() {
        return dirOut;
    }

    public static void setDirOut(String directoryOut) {
        dirOut = directoryOut;
    }

    private static void initDirectory() {
        String homeDir = System.getProperty("user.home");
        Properties properties = new Properties();
        String filename = homeDir + File.separator + "converter.properties";
        try {
            FileInputStream fis = new FileInputStream(filename);
            properties.load(fis);

            dirIn = properties.getProperty("dirIn");
            if(!new File(dirIn).exists()) dirIn = ".";

            dirOut = properties.getProperty("dirOut");
            if(!new File(dirOut).exists()) dirOut = ".";

            fis.close();
        } catch(Exception exc) {
            // converter.properties does not exist
            exc.printStackTrace();
            dirIn = new File(".").getName();
            dirOut = new File(".").getName();
        }
    }

    private static void saveDirectory() {
        Properties properties = new Properties();
        properties.setProperty("dirIn", dirIn);
        properties.setProperty("dirOut", dirOut);

        try(FileOutputStream fos = new FileOutputStream(
                System.getProperty("user.home") + File.separator + "converter.properties")) {
            properties.store(fos, "saved");
        } catch(Exception exc) {
            exc.printStackTrace();
        }
    }
}
