package com.company.test.service;

import com.haulmont.cuba.core.global.Messages;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service(MagicSquareService.NAME)
public class MagicSquareServiceBean implements MagicSquareService {
    @Inject
    private Messages messages;

    @Override
    public String getResult(String string) {
        BigInteger[][] data = getInputDataFromString(string);
        BigInteger cost = new BigInteger("-1");
        StringBuilder result = new StringBuilder(messages.getMainMessage("title")).append("\n");
        int[][] closestMagicCircle = new int[3][3];
        for (int[][] baseData : getNormalMagicSquares()) {
            BigInteger tempCost = getTransformationCost(data, baseData);
            if (cost.compareTo(new BigInteger("0")) < 0 || tempCost.compareTo(cost) < 0) {
                cost = tempCost;
                closestMagicCircle = baseData;
            }
        }
        Arrays.stream(closestMagicCircle).forEach(o -> {
            Arrays.stream(o).forEach(o1 -> result.append(o1).append(" "));
            result.deleteCharAt(result.length() - 1).append("\n");
        });
        return result.append(messages.getMainMessage("ending")).append(" ").append(cost).toString();
    }

    /**
     * This method divides a solid string representation of a two-dimensional array of numbers into an actual
     * two-dimensional array and turns all elements to {@link BigInteger} just in case the numbers
     * are too large for any other class of integer numbers
     *
     * @param string is a string representation of a two-dimensional array of numbers
     * @return a {@code BigInteger} two-dimensional array
     */
    private BigInteger[][] getInputDataFromString(String string) {
        return Arrays.stream(Arrays.stream(string.split("\n")).map(String::trim).
                map(o -> Arrays.stream(o.split(" ")).toArray(String[]::new)).toArray(String[][]::new)).
                map(o -> Arrays.stream(o).map(BigInteger::new).toArray(BigInteger[]::new)).
                toArray(BigInteger[][]::new);
    }

    /**
     * This method calculates all possible normal magic squares or a 3x3 size
     *
     * @return an {@code ArrayList} of a two-dimensional {@code int} arrays representing normal magic squares
     */
    private ArrayList<int[][]> getNormalMagicSquares() {
        ArrayList<String> allPossibleSequences = new ArrayList<>();
        getAllPossibleSequences("123456789", new StringBuilder(), allPossibleSequences);
        return validateMagicSquares(allPossibleSequences);
    }

    /**
     * This method generates all possible sequences from the characters in a given base string
     *
     * @param numBase    is a string base for generation
     * @param numSeq     is a {@link StringBuilder} to build sequences
     * @param resultList is an {@link ArrayList} to store completed sequences
     */
    private void getAllPossibleSequences(String numBase, StringBuilder numSeq, ArrayList<String> resultList) {
        if (numBase.length() == 0) {
            if (numSeq.length() > 0) {
                resultList.add(numSeq.toString());
                numSeq.deleteCharAt(numSeq.length() - 1);
            }
        } else {
            for (int i = 0; i < numBase.length(); i++)
                getAllPossibleSequences(numBase.replace(Character.toString(numBase.toCharArray()[i]),
                        ""), numSeq.append(numBase.toCharArray()[i]), resultList);
            if (numSeq.length() > 0) numSeq.deleteCharAt(numSeq.length() - 1);
        }
    }

    /**
     * This method chooses magic squares from all possible sequences and turns it into actual
     * {@link ArrayList} of two-dimensional {@code int} arrays
     *
     * @param inputData an {@code ArrayList} of {@link String} representations of magic squares to validate
     * @return an {@code ArrayList} of two-dimensional {@code int} arrays representing true magic squares
     */
    private ArrayList<int[][]> validateMagicSquares(ArrayList<String> inputData) {
        return getMagicSequencesInNumbers(new ArrayList<>(inputData.stream().filter(o ->
                validateRowsAndColumns(o, getSumFromStringByIndexes(o, 0, 4, 8))
                        && validateDiagonal(o, getSumFromStringByIndexes(o, 0, 4, 8))).
                collect(Collectors.toList())));
    }

    /**
     * This method validates rows and columns of magic square
     *
     * @param string is a {@link String} representation of a magic square
     * @param sum    is an {@code int} sum of a magic square
     * @return {@code true} if all row and columns have the same sum as given in parameter
     */
    private boolean validateRowsAndColumns(String string, int sum) {
        for (int index = 0; index < 3; index++) {
            if (getSumFromStringByIndexes(string, index, index + 3, index + 6) != sum
                    || getSumFromStringByIndexes(string, 3 * index, 3 * index + 1,
                    3 * index + 2) != sum)
                return false;
        }
        return true;
    }

    /**
     * This method validates diagonal of a magic square
     *
     * @param string is a {@link String} representation of a magic square
     * @param sum    is an {@code int} sum of this magic square
     * @return {@code true} if diagonal have the same sum as given in parameter
     */
    private boolean validateDiagonal(String string, int sum) {
        return getSumFromStringByIndexes(string, 2, 4, 6) == sum;
    }

    /**
     * This method calculates the sum of digits from the string, generating a probable sum of a magic square
     *
     * @param numData is a {@link String} representation of a magic square
     * @param index1  is an {@code int} index of a first digit in a given string
     * @param index2  is an {@code int} index of a second digit in a given string
     * @param index3  is an {@code int} index of a third digit in a given string
     * @return an {@code int} sum of three chosen digits
     */
    private int getSumFromStringByIndexes(String numData, int index1, int index2, int index3) {
        return Integer.parseInt(Character.toString(numData.toCharArray()[index1])) +
                Integer.parseInt(Character.toString(numData.toCharArray()[index2])) +
                Integer.parseInt(Character.toString(numData.toCharArray()[index3]));
    }

    /**
     * This method turns a {@link String} representations of a true magic squares into  actual
     * two-dimensional {@code int} arrays of digits
     *
     * @param validatedMagicSquares is an {@code ArrayList} of string representations of true magic squares
     * @return an {@code ArrayLIst} of two-dimensional {@code int} arrays of digits
     */
    private ArrayList<int[][]> getMagicSequencesInNumbers(ArrayList<String> validatedMagicSquares) {
        return validatedMagicSquares.stream().map(o -> Arrays.stream(new String[]{o.substring(0, 3),
                o.substring(3, 6), o.substring(6, 9)}).map(o1 -> Arrays.stream(new String[]{o1.substring(0, 1),
                o1.substring(1, 2), o1.substring(2, 3)}).mapToInt(Integer::parseInt).toArray()).
                toArray(int[][]::new)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * This method counts a transformation cost for a given square and chosen true magic square
     *
     * @param data     is a {@link BigInteger} two-dimensional array representing given square
     * @param baseData is an {@code int} two-dimensional array representing chosen true magic square
     * @return a {@code BigInteger} transformation cost
     */
    private BigInteger getTransformationCost(BigInteger[][] data, int[][] baseData) {
        BigInteger counter = new BigInteger("0");
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++)
                counter = counter.add((data[i][j].
                        subtract(new BigInteger(Integer.toString(baseData[i][j])))).abs());
        }
        return counter;
    }
}