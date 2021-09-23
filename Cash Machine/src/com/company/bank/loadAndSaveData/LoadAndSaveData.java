package com.company.bank.loadAndSaveData;

import com.company.bank.cashMachine.CashMachine;
import com.company.bank.cashMachine.card.Card;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoadAndSaveData {

    public static void saveCard(int numbOfList, Card authorizedUser) throws IOException {
        List<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("src\\com\\company\\data\\cards.txt\\")));
        lines.set(numbOfList, authorizedUser.getNewLine());
        Files.write(Paths.get("src\\com\\company\\data\\cards.txt\\"), lines);
    }

    public static void saveAvailableCash() throws IOException {
        PrintWriter write = new PrintWriter("src\\com\\company\\data\\availableCash.txt\\");
        write.write(String.valueOf(CashMachine.getCashInCashMachine()));
        write.flush();
        write.close();
    }

    public static ArrayList<Card> loadCards() throws IOException{
        ArrayList<Card> cards = new ArrayList<>();
        try{
            List<String> lines = Files.readAllLines(Paths.get("src\\com\\company\\data\\cards.txt\\"));
            for(String line : lines){
                String[] fragments = line.split(" ");
                cards.add(new Card(fragments[0],
                        Integer.parseInt(fragments[1]),
                        Double.parseDouble(fragments[2]),
                        Boolean.parseBoolean(fragments[3])));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return cards;
    }

    public static double loadAvailableCash() throws IOException{
        Scanner scan = new Scanner(new FileInputStream("src\\com\\company\\data\\availableCash.txt\\"));
        String cash = scan.next();
        scan.close();
        return Double.parseDouble(cash);
    }
}
