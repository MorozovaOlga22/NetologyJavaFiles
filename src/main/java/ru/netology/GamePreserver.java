package ru.netology;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GamePreserver {
    /**
     * Метод сохраняет состояние игры
     *
     * @param archiveRelativePath -- путь к архиву, в котором в конце будут храниться файлы с GameProgress
     * @param gameProgressMap     -- мапа <Имя файла для сохранения объекта GameProgress, сам объект GameProgress>
     */
    public static void saveGameProgress(String archiveRelativePath, Map<String, GameProgress> gameProgressMap) {
        final String savegamesPath = String.join(File.separator, "Games", "savegames");

        //Сохраняем объекты GameProgress во временные файлы
        //Сразу запоминаем эти файлы, чтобы потом запаковать их и удалить
        final List<File> filesToPack = new ArrayList<>();
        for (Map.Entry<String, GameProgress> entry : gameProgressMap.entrySet()) {
            final String fileName = entry.getKey();
            final String fileRelativePath = savegamesPath + File.separator + fileName;
            final GameProgress gameProgress = entry.getValue();
            saveGame(fileRelativePath, gameProgress);
            filesToPack.add(new File(fileRelativePath));
        }

        //Запаковываем временные файлы в архив
        zipFiles(archiveRelativePath, filesToPack);

        //Пытаемся удалить временную папку
        for (File file : filesToPack) {
            if (!file.delete()) {
                System.out.println("Can't delete temp file " + file.getName() + " . You can do it yourself");
            }
        }
    }

    private static void saveGame(String fileRelativePath, GameProgress gameProgress) {
        try (final FileOutputStream fos = new FileOutputStream(fileRelativePath);
             final ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFiles(String archiveRelativePath, List<File> tmpDir) {
        try (final ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archiveRelativePath))) {
            for (File file : tmpDir) {
                try (final FileInputStream fis = new FileInputStream(file)) {
                    final ZipEntry entry = new ZipEntry(file.getName());
                    zout.putNextEntry(entry);

                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);

                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
