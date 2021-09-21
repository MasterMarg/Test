package com.company.test.service;

import java.io.IOException;

public interface SaveLoadService {
    String NAME = "test_SaveLoadService";

    void saveToFile(int type, String data, String filename) throws IOException;

    String[] loadFromFile(String path) throws IOException;

}