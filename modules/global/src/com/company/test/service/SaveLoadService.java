package com.company.test.service;

import java.io.IOException;

public interface SaveLoadService {
    String NAME = "test_SaveLoadService";

    /**
     * Saves data from parameters to a file with a given name
     *
     * @param type     is an int number, representing a type of a chosen task on main screen
     * @param data     is a string input of a user on main screen
     * @param filename is a string representing a file name to save data in it
     * @throws IOException if an I/O error occurs
     */
    void saveToFile(int type, String data, String filename) throws IOException;

    /**
     * Loading data from a file with specified path
     *
     * @param path is a string representation of a file path
     * @return an array of strings, consisting of string representation of task's type and string
     * representation of data itself
     * @throws IOException if I/O error occurs
     */
    String[] loadFromFile(String path) throws IOException;

}