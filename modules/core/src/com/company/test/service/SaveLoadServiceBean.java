package com.company.test.service;

import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service(SaveLoadService.NAME)
public class SaveLoadServiceBean implements SaveLoadService {

    @Override
    public void saveToFile(int type, String data, String fileName) throws IOException {
        File file = new File("Saves");
        if (!file.isDirectory()) file.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file + "\\" +
                fileName + ".txt"));
        writer.write(type + "\n" + data);
        writer.close();
    }

    @Override
    public String[] loadFromFile(String path) throws IOException {
        String[] data =  Files.lines(Paths.get(path)).toArray(String[]::new);
        StringBuilder builder = new StringBuilder(data[1]);
        for (int index = 2; index < data.length; index++) builder.append("\n").append(data[index]);
        return new String[]{data[0], builder.toString()};
    }
}