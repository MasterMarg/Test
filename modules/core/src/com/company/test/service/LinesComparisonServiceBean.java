package com.company.test.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;

@Service(LinesComparisonService.NAME)
public class LinesComparisonServiceBean implements LinesComparisonService {
    public String getResult(String string) {
        String[][] data = getDataFromString(string);
        ArrayList<String> tempResult = new ArrayList<>();
        for (String tempString : data[0])
            for (String anotherString : data[1])
                if (anotherString.contains(tempString) && !(checkForDuplicate(tempResult, tempString))) {
                    tempResult.add(tempString);
                    break;
                }
        Collections.sort(tempResult);
        StringBuilder answer = new StringBuilder();
        for (String str : tempResult)
            answer.append(str).append(" ");
        return answer.toString().trim();
    }

    private String[][] getDataFromString(String string) {
        String[][] data = new String[2][];
        String[] temp = string.split("\n");
        int index = -1;
        for (String anotherString : temp) {
            index++;
            data[index] = anotherString.split(" ");
        }
        return data;
    }

    private boolean checkForDuplicate(ArrayList<String> data, String string) {
        for (String str : data)
            if (string.equals(str)) return true;
        return false;
    }
}