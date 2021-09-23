package com.company.bank.cashMachine.card;

public class Card {

    private int PIN;
    private String numberOfCard;
    private double balance;
    private boolean isBlocked;
    private String newLine;

    public Card(String numb, int pin, double balance, boolean blocked){
        setBalance(balance);
        setNumberOfCard(numb);
        setPIN(pin);
        setBlocked(blocked);
    }

    public boolean isBlocked() {
        return isBlocked;
    }
    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
    public void setBlocked(){
        isBlocked = true;
    }
    public double getBalance() {
        return balance;
    }
    public int getPIN() {
        return PIN;
    }
    public String getNumberOfCard() {
        return numberOfCard;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setPIN(int PIN) {
        this.PIN = PIN;
    }
    public void setNumberOfCard(String numberOfCard) {
        this.numberOfCard = numberOfCard;
    }
    public String getNewLine(){
        if(isBlocked){
            newLine = numberOfCard + " " + PIN + " " + balance + " true";
        }else{
            newLine = numberOfCard + " " + PIN + " " + balance + " false";
        }
        return newLine;
    }

}
