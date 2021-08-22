package ru.netology;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class GameInstaller {
    /**
     * Метод, создающих папки и файлы при установке игры
     */
    public static void createDirsAndFiles() {
        final StringBuilder builder = new StringBuilder("Game installation report\n");
        createDirs("Games", Arrays.asList("src", "res", "savegames", "temp"), builder);

        final String srcRelativePath = "Games" + File.separator + "src";
        createDirs(srcRelativePath, Arrays.asList("main", "test"), builder);

        createFilesInMain(builder);

        final String resRelativePath = "Games" + File.separator + "res";
        createDirs(resRelativePath, Arrays.asList("drawables", "vectors", "icons"), builder);

        createAndFillTempFile(builder);
    }

    private static void createDirs(String parentDir, List<String> dirsToCreate, StringBuilder builder) {
        for (String dirName : dirsToCreate) {
            final String dirRelativePath = parentDir + File.separator + dirName;
            createDir(dirRelativePath, builder);
        }
    }

    private static void createDir(String dirRelativePath, StringBuilder builder) {
        final File file = new File(dirRelativePath);
        if (file.mkdir()) {
            builder.append("Directory ").append(dirRelativePath).append(" created\n");
        } else {
            builder.append("Can't create directory ").append(dirRelativePath).append("\n");
        }
    }

    private static void createFilesInMain(StringBuilder builder) {
        createFileInMain("Main.java", builder);
        createFileInMain("Utils.java", builder);
    }

    private static void createFileInMain(String fileName, StringBuilder builder) {
        final String fileRelativePath = String.join(File.separator, "Games", "src", "main", fileName);
        final File file = new File(fileRelativePath);
        try {
            if (file.createNewFile()) {
                builder.append("File ").append(fileRelativePath).append(" created\n");
            } else {
                builder.append("Can't create file ").append(fileRelativePath).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            builder.append("Can't create file ").append(fileRelativePath).append("\n");
        }
    }

    private static void createAndFillTempFile(StringBuilder builder) {
        final String tempFileRelativePath = String.join(File.separator, "Games", "temp", "temp.txt");
        try (final Writer writer = new FileWriter(tempFileRelativePath)) {
            writer.write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            //Если файл temp.txt успешно создался, нет смысла писать об этом в сам файл temp.txt
            //А если не создался, то пишем об этом в лог
            builder.append("Can't create file temp.txt");
            System.out.println(builder);
        }
    }
}
