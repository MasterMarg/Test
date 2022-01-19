package com.company.test.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service(LinesComparisonService.NAME)
public class LinesComparisonServiceBean implements LinesComparisonService {
    public String getResult(String string) {
        StringBuilder answer = new StringBuilder();
        String[][] data = getDataFromString(string);
        Arrays.stream(data[1]).flatMap(o -> Arrays.stream(data[0]).filter(o::contains)).distinct().
                sorted().forEach(o -> answer.append(o).append(" "));
        return answer.toString().trim();
    }

    /**
     * This method divides a solid string into an actual two-dimensional array of strings
     * @param string is a string input of a user made by rules in the description of data field
     * @return a {@code String} two-dimensional array
     */
    private String[][] getDataFromString(String string) {
        return Arrays.stream(string.split("\n")).map(String::trim).
                map(o -> Arrays.stream(o.split(" ")).map(String::trim).toArray(String[]::new)).
                toArray(String[][]::new);
    }
}