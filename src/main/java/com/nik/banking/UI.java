package com.nik.banking;

import java.util.Scanner;

public class UI {
    private final BankingS bankingS;
    private final Scanner scanner;

    public UI(Scanner scanner) {
        this.bankingS = new BankingS();
        this.scanner = scanner;
    }

    public void start() {

        while (true) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");

            String command = this.scanner.nextLine();

            if (command.equals("0")) {
                this.exit();
                break;
            }

            if (command.equals("1")) {
                this.cardCreation();
            }

            if (command.equals("2")) {
                String exitCon = this.logInText();
                if (exitCon.equals("0")) {
                    this.exit();
                    break;
                }

            }
        }
    }

    private String logInText() {
        System.out.println();
        System.out.println("Enter your card number:");
        String cardNum = this.scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pinNum = this.scanner.nextLine();

        if (this.bankingS.logIn(cardNum, pinNum)) {
            System.out.println();
            System.out.println("You have successfully logged in!");
            while (true) {
                System.out.println();
                System.out.println("1. Balance");
                System.out.println("2. Add income");
                System.out.println("3. Do transfer");
                System.out.println("4. Close account");
                System.out.println("5. Log out");
                System.out.println("0. Exit");

                String command = this.scanner.nextLine();

                if ("5".equals(command)) {
                    System.out.println();
                    System.out.println("You have successfully logged out!");
                    break;
                }

                if ("1".equals(command)) {
                    System.out.println();
                    System.out.printf("Balance: %d", this.bankingS.getBalance(cardNum));
                    System.out.println();
                }

                if ("2".equals(command)) {
                    System.out.println();
                    System.out.println("Enter income:");
                    int income = Integer.parseInt(this.scanner.nextLine());

                    this.bankingS.addIncome(income, cardNum);
                    System.out.println("Income was added!");
                }

                if ("3".equals(command)) {
                    System.out.println("Transfer");
                    System.out.println("Enter card number:");
                    String cardNumberTo = this.scanner.nextLine();
                    System.out.println();
                    String currentCardNum = this.bankingS.getCardNum();

                    if (!this.bankingS.checkCardNumber(cardNumberTo)) {
                        System.out.println("Probably you made a mistake in the card number. Please try again! ");
                    }else  if (currentCardNum.equals(cardNumberTo)) {
                        System.out.println("You can't transfer money to the same account!");
                    } else if (!this.bankingS.isItIn(cardNumberTo)) {
                        System.out.println("Such a card does not exist.");

                    } else {
                        System.out.println("Enter how much money you want to transfer:");
                        int amount = this.scanner.nextInt();
                        if (this.bankingS.getBalance(currentCardNum) >= amount) {
                            this.bankingS.addIncome(amount, cardNumberTo);
                            this.bankingS.takeIncome(currentCardNum, amount);
                        }
                        if (this.bankingS.getBalance(currentCardNum) < amount) {
                            System.out.println("Not enough money!");
                        }
                    }
                }

                if ("4".equals(command)) {
                    System.out.println("The account has been closed!");
                    this.bankingS.deleteAcc(cardNum);
                }

                if ("0".equals(command)) {
                    return "0";
                }

            }
        } else {
            System.out.println();
            System.out.println("Wrong card number or PIN!");
            System.out.println();
        }
        return "1";
    }

    private void cardCreation() {
        System.out.println();
        this.bankingS.createAccount();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(this.bankingS.getCardNum());
        System.out.println("Your card PIN:");
        System.out.println(this.bankingS.getPinNum());
        System.out.println();
    }

    private void exit() {
        System.out.println();
        System.out.println("Bye!");

    }


}
