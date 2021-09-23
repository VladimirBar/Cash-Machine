package com.company.bank.cashMachine;

import com.company.bank.Bank;
import com.company.bank.cashMachine.card.Card;
import com.company.bank.loadAndSaveData.LoadAndSaveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CashMachine {

    private final Scanner scanner = new Scanner(System.in);
    private List entries = new ArrayList();
    private boolean isExit = false;
    private final ArrayList<Card> cards;
    private Card authorizedUser;
    private static double cashInCashMachine;
    private boolean isAuthorized = false;
    private int numbOfList;
    private int countOfTry = 0;

    public CashMachine() throws IOException {
        cards = LoadAndSaveData.loadCards();
        cashInCashMachine = LoadAndSaveData.loadAvailableCash();
        //showResult(); //uncomment to make it easier to test
        while(!isAuthorized) {authorization();}
        if(isAuthorized) {menu();}
    }

    public static double getCashInCashMachine() {
        return cashInCashMachine;
    }
    public static void setCashInCashMachine(double cash){
        cashInCashMachine = cashInCashMachine+ cash;
    }

    public void showResult(){
        for(int i = 0; i < cards.size(); i++){
            System.out.print(cards.get(i).getNumberOfCard() + " ");
            System.out.print(cards.get(i).getPIN() + " ");
            System.out.print(cards.get(i).getBalance() + " ");
            if(cards.get(i).isBlocked()){
                System.out.println(" blocked");
            }else System.out.println("not blocked");
        }
        System.out.println(cashInCashMachine);
    }

    public void menu() throws IOException {
        System.out.println("\nSelect the required operation: ");
        System.out.println("    1. Check the current balance");
        System.out.println("    2. Withdraw cash");
        System.out.println("    3. Deposit money");
        System.out.println("    0. Sign out");
        int option = scanner.nextInt();
        switch (option){
            case 0: exit();
            case 1: {
                Bank.checkBalance(authorizedUser);
                proceed();
            }
            case 2:{
                Bank.withdrawCash(authorizedUser);
                proceed();
            }
            case 3: {
                Bank.depositCash(authorizedUser);
                proceed();
            }
        }
    }

    public void proceed() throws IOException {
        System.out.println("Do you want to continue? y/n");
        String choiceContinue = scanner.next();
        if(choiceContinue.equals("y") || choiceContinue.equals("yes")){
            confirmPin();
        }else if(choiceContinue.equals("n") || choiceContinue.equals("no")){
            exit();
        }else{
            System.out.print("Input Error. End of session. ");
            exit();
        }
    }

    public void confirmPin() throws IOException {
        System.out.println("Input PIN to continue: ");
        while (true){
            int inputPin = scanner.nextInt();
            if(authorizedUser.getPIN() == inputPin){
                menu();
                countOfTry=0;
                System.out.println("\n");
                break;
            }else{
                countOfTry++;
                if(countOfTry>=3){
                    authorizedUser.setBlocked();
                    System.out.println("The card is blocked, contact the bank branch.");
                    exit();
                }
                System.out.println("Incorrect PIN, try again");
            }
        }
    }

    public void authorization() throws IOException {
        System.out.println("\nInput card! It should be like XXXXX-XXXXX-XXXXX-XXXXX");
        String inputNumber = scanner.next();
        for (int i = 0; i < cards.size(); i++) {
            if (inputNumber.equals(cards.get(i).getNumberOfCard())) {
                if (cards.get(i).isBlocked()) {
                    System.out.println("The card is blocked, contact the bank branch.");
                    System.exit(0);
                }else{
                    System.out.println("Input your PIN");
                    while(true){
                        int inputPin = scanner.nextInt();
                        if (inputPin == cards.get(i).getPIN()) {
                            System.out.println("You are successfully logged in!");
                            authorizedUser = cards.get(i);
                            isAuthorized = true;
                            numbOfList = i;
                            countOfTry=0;
                            break;
                        }else{
                            countOfTry++;
                            if(countOfTry>=3){
                                System.out.println("The card is blocked, contact the bank branch");
                                cards.get(i).setBlocked();
                                authorizedUser = cards.get(i);
                                numbOfList = i;
                                exit();
                            }
                            System.out.println("Wrong PIN, please try again");
                        }
                    }
                    if(isAuthorized){
                        break;
                    }
                }
            }
        }
        if(!isAuthorized){
            System.out.println("Incorrect data. Failed authorization");
        }
    }

    public void exit() throws IOException {
        LoadAndSaveData.saveCard(numbOfList, authorizedUser);
        LoadAndSaveData.saveAvailableCash();
        System.out.println("Good luck!");
        System.exit(0);
    }

}
