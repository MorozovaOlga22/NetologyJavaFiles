package ru.netology;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        GameInstaller.createDirsAndFiles();

        final String archivePath = String.join(File.separator, "Games", "savegames", "archive.zip");
        GamePreserver.saveGameProgress(archivePath, createTestGameProgressMap());

        final String tmpPath = String.join(File.separator, "Games", "temp");
        GameLoader.openZip(archivePath, tmpPath);

        final String save1Path = String.join(File.separator, tmpPath, "save1.dat");
        System.out.println(GameLoader.openProgress(save1Path));
    }

    private static Map<String, GameProgress> createTestGameProgressMap() {
        final Map<String, GameProgress> gameProgressMap = new HashMap<>();
        gameProgressMap.put("save1.dat", new GameProgress(10, 5, 3, 2.5));
        gameProgressMap.put("save2.dat", new GameProgress(20, 50, 7, 2.5));
        gameProgressMap.put("save3.dat", new GameProgress(5, 5, 1, 5.5));
        return gameProgressMap;
    }
}
