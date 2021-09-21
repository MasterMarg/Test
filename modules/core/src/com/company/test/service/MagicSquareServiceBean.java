package com.company.test.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service(MagicSquareService.NAME)
public class MagicSquareServiceBean implements MagicSquareService {
    public String getResult(String string) {
        int[][] data = getInputDataFromString(string);
        int cost = -1;
        int[][] closestMagicCircle = new int[3][3];
        for (int[][] baseData : getNormalMagicSquares()) {
            int tempCost = getTransformationCost(data, baseData);
            if (cost < 0 || tempCost < cost) {
                cost = tempCost;
                closestMagicCircle = baseData;
            }
        }
        StringBuilder result = new StringBuilder("Ближайший по стоимости магический квадрат:\n");
        for (int[] intArray : closestMagicCircle)
            for (int j = 0; j < intArray.length; j++) {
                result.append(intArray[j]).append(" ");
                if (j == intArray.length - 1) result.deleteCharAt(result.length() - 1).append("\n");
            }
        result.append("Стоимость: ").append(cost);
        return result.toString();
    }

    private int[][] getInputDataFromString(String string) {
        String[][] stringData = new String[3][3];
        String[] tempStringData = string.split("\n");
        for (int i = 0; i <tempStringData.length; i++)
            System.arraycopy(tempStringData[i].split(" "), 0, stringData[i], 0,
                    tempStringData[i].split(" ").length);
        int[][] intData = new int[3][3];
        for (int i = 0; i < stringData.length; i++)
            for (int j = 0; j < stringData[i].length; j++)
                intData[i][j] = Integer.parseInt(stringData[i][j]);
        return intData;
    }

    private ArrayList<int[][]> getNormalMagicSquares() {
        ArrayList<String> allPossibleSequences = new ArrayList<>();
        getAllPossibleSequences("123456789", new StringBuilder(), allPossibleSequences);
        return validateMagicSquares(allPossibleSequences);
    }

    private void getAllPossibleSequences(String numBase, StringBuilder numSeq, ArrayList<String> resultList) {
        if (numBase.length() == 0) {
            resultList.add(numSeq.toString());
            numSeq.deleteCharAt(numSeq.length() - 1);
        } else {
            for (int i = 0; i < numBase.length(); i++)
                getAllPossibleSequences(numBase.replace(Character.toString(numBase.toCharArray()[i]),
                        ""), numSeq.append(numBase.toCharArray()[i]), resultList);
            if (numSeq.length() > 0) numSeq.deleteCharAt(numSeq.length() - 1);
        }
    }

    private ArrayList<int[][]> validateMagicSquares(ArrayList<String> inputData) {
        ArrayList<String> tempResult = new ArrayList<>();
        for (String string : inputData) {
            int sum = getSumFromStringByIndexes(string, 0, 3, 6);
            if (validateRowsAndColumns(string, sum) && validateDiagonals(string, sum))
                tempResult.add(string);
        }
        return getMagicSequencesInNumbers(tempResult);
    }

    private boolean validateRowsAndColumns(String string, int sum) {
        for (int index = 0; index < 3; index++) {
            if (getSumFromStringByIndexes(string, index, index + 3, index + 6) != sum
                    || getSumFromStringByIndexes(string, 3 * index, 3 * index + 1,
                    3 * index + 2) != sum)
                return false;
        }
        return true;
    }

    private boolean validateDiagonals(String string, int sum) {
        return getSumFromStringByIndexes(string, 0, 4, 8) == sum &&
                getSumFromStringByIndexes(string, 2, 4, 6) == sum;
    }

    private int getSumFromStringByIndexes(String numData, int index1, int index2, int index3) {
        return Integer.parseInt(Character.toString(numData.toCharArray()[index1])) +
                Integer.parseInt(Character.toString(numData.toCharArray()[index2])) +
                Integer.parseInt(Character.toString(numData.toCharArray()[index3]));
    }

    private ArrayList<int[][]> getMagicSequencesInNumbers(ArrayList<String> validatedMagicSquares) {
        ArrayList<int[][]> numMagicSquares = new ArrayList<>();
        for (String string : validatedMagicSquares) {
            int[][] resultArray = new int[3][3];
            for (int i = 0; i < resultArray.length; i++)
                for (int j = 0; j < resultArray[i].length; j++)
                    resultArray[i][j] = Integer.parseInt(Character.toString(string.toCharArray()[3 * i + j]));
            numMagicSquares.add(resultArray);
        }
        return numMagicSquares;
    }

    private int getTransformationCost(int[][] data, int[][] baseData) {
        int counter = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                counter += Math.abs(data[i][j] - baseData[i][j]);
            }
        }
        return counter;
    }
}