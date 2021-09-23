package com.company.bank;
import com.company.bank.cashMachine.card.Card;
import com.company.bank.cashMachine.CashMachine;

import java.util.Scanner;

public class Bank {
    private static final Scanner scanner = new Scanner(System.in);
    private static double withdraw;
    private static double deposit;


    public static void checkBalance(Card authorizedUser){
        System.out.println("Your current balance: " + authorizedUser.getBalance()+"\n");
    }

    public static void withdrawCash(Card authorizedUser){
        System.out.println("\nHow much do you want to cash out?");
        withdraw = scanner.nextDouble();
        if(withdraw <= authorizedUser.getBalance() && withdraw <= CashMachine.getCashInCashMachine()){
                authorizedUser.setBalance(authorizedUser.getBalance() - withdraw);
                CashMachine.setCashInCashMachine(-1 * withdraw);
                System.out.println("Money cashed successfully\n");
        }else{
            if(withdraw > authorizedUser.getBalance()){
                System.out.println("Insufficient funds\n");
            }else if(withdraw > CashMachine.getCashInCashMachine()){
                System.out.println("There are not enough funds in the cash machine\n");
            }
        }
    }

    public static void depositCash(Card authorizedUser){
        System.out.println("\nHow much money do you want to deposit?");
        deposit = scanner.nextDouble();
        if(deposit > 1000000){
            System.out.println("Error, deposit less than 1,000,000\n");
        }else{
            authorizedUser.setBalance(authorizedUser.getBalance()+deposit);
            System.out.println("The money has been successfully credited to the account\n");
            CashMachine.setCashInCashMachine(deposit);
        }
    }
}
