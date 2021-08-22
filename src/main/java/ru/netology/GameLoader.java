package ru.netology;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GameLoader {
    /**
     * Метод для разархивации файлов GameProgress
     *
     * @param pathToArchive       -- путь к архиву, где хранятся файлы с GameProgress
     * @param pathToDirWhereUnzip -- путь к папке, куда нужно извлечь файлы
     */
    public static void openZip(String pathToArchive, String pathToDirWhereUnzip) {
        try (final ZipInputStream zin = new ZipInputStream(new FileInputStream(pathToArchive))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                final String fileRelativePath = pathToDirWhereUnzip + File.separator + entry.getName();
                try (FileOutputStream fout = new FileOutputStream(fileRelativePath)) {
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }
                    fout.flush();
                    zin.closeEntry();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Метод для получения экземпляра GameProgress из файла
     *
     * @param fileWithGameProgressPath -- путь к файлу, где хранится GameProgress
     * @return -- экземпляр GameProgress или null, если не удалось извлечь GameProgress
     */
    public static GameProgress openProgress(String fileWithGameProgressPath) {
        try (FileInputStream fis = new FileInputStream(fileWithGameProgressPath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
