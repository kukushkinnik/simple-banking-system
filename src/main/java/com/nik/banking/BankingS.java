package com.nik.banking;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class BankingS {
    private final Random random;
    private String cardNum;
    private String pinNum;

    public BankingS() {
        random = new Random();
        DB.createTable();
    }


    public void createAccount() {
        this.cardNum = this.generateCardNumber();
        this.pinNum = this.generatePinNumber();
        DB.insertData(cardNum, pinNum);
    }

    public boolean logIn(String cardNumber, String pinNumber) {
        boolean logedIn = false;

        ArrayList<String> data = DB.getCardAndPin() ;

        for (int i = 0; i < data.size(); i++) {
            if (Objects.equals(data.get(i), cardNumber) && Objects.equals(data.get(i + 1), pinNumber)) {
                logedIn = true;
                this.cardNum = cardNumber;
                break;
            }
        }
        return logedIn;
    }


    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        String mii = "400000";
        String checkSum;

        cardNumber.append(mii);

        for (int i = 0; i < 9; i++) {
            cardNumber.append(random.nextInt(9 + 1));
        }

        checkSum = this.checkSum(String.valueOf(cardNumber));

        cardNumber.append(checkSum);

        return cardNumber.toString();
    }


    private String generatePinNumber() {
        StringBuilder pinNumber = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            pinNumber.append(random.nextInt(9 + 1));
        }

        return pinNumber.toString();
    }


    //creating check sum for validity of card number
    private String checkSum(String cardNumber) {

        String[] splitNumber = cardNumber.split("");
        int[] arr = new int[splitNumber.length];
        int sum = 0;
        int checkSum;

        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(splitNumber[i]);

            if (i % 2 == 0) {
                arr[i] *= 2;
            }

            if (arr[i] > 9) {
                //finding sum of two-digit number
                int firstD = arr[i];
                int secondD = arr[i] % 10;

                while (firstD >= 10) {
                    firstD /= 10;
                }
                arr[i] = firstD + secondD;
            }

            sum += arr[i];
        }


        if (sum % 10 == 0) {
            checkSum = 0;
        } else {
            checkSum = 10 - (sum % 10);
        }

        return String.valueOf(checkSum);
    }

    public void addIncome(int income, String cardNum) {
        DB.updateBalance(income, cardNum);
    }
    public void takeIncome(String cardNum, int amount) {
        DB.takeIncome(cardNum, amount);
    }

    public int getBalance(String cardNum) {
        return DB.getBalance(cardNum);
    }

    public void deleteAcc(String cardNum) {
        DB.deleteAcc(cardNum);
    }

    public void transfer(String cardNumberTo, int amount) {

        //TODO(nik):can make to do all the works and make helper methods as private

    }

    public String getCardNum() {
        return this.cardNum;
    }

    public String getPinNum() {
        return this.pinNum;
    }

    public boolean checkCardNumber (String cardNum) {
        boolean result = false;
        String cardNumNoCheckSum = cardNum.substring(0, cardNum.length() - 1);

        String checkSum = this.checkSum(cardNumNoCheckSum);
        String checkSumFromReciver = cardNum.substring(cardNum.length() - 1);

        System.out.println(checkSum);
        System.out.println(checkSumFromReciver);
        if (checkSumFromReciver.equals(checkSum)) {
            result = true;
        }
        return  result;

    }

    public boolean isItIn(String cardNum) {
        ArrayList<String> listOfCards = DB.getCardAndPin();
        boolean isIn = false;
        for (int i = 0; i < listOfCards.size(); i++) {
            if (Objects.equals(listOfCards.get(i), cardNum)) {
                isIn = true;
                break;
            }
        }
        return isIn;
    }


}