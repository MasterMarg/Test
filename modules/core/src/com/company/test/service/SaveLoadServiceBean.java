package com.company.test.service;

import org.springframework.stereotype.Service;
import java.io.*;

@Service(SaveLoadService.NAME)
public class SaveLoadServiceBean implements SaveLoadService {

    public void saveToFile(int type, String data, String fileName) throws IOException {
        File file = new File("Saves");
        if (!file.isDirectory()) file.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file + "\\" +
                fileName + ".txt"));
        writer.write(type + "\n" + data);
        writer.close();
    }

    public String[] loadFromFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String[] result = new String[2];
        result[0] = reader.readLine();
        StringBuilder builder = new StringBuilder();
        while(reader.ready()) builder.append(reader.readLine()).append("\n");
        builder.deleteCharAt(builder.length()-1);
        result[1] = builder.toString();
        return result;
    }
}