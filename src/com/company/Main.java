package com.company;

import java.io.*;
import java.util.*;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        Bank myBank = new Bank();

        int user_choice = 2;


        do {
            //display menu to user
            //ask user for his choice and validate it (make sure it is between 1 and 6)
            System.out.println();
            System.out.println("1) Open a new bank account");
            System.out.println("2) Deposit to a bank account");
            System.out.println("3) Withdraw to bank account");
            System.out.println("4) Print account balance");
            System.out.println("5) Pay Utility Bills");
            System.out.println("6) Transfer between Accounts");
            System.out.println("7) Exit");
            System.out.println();
            System.out.print("Enter choice [1-6]: ");
            user_choice = s.nextInt();
            switch (user_choice) {
                case 1:
                    System.out.println("We need to authenticate if you are an Advisor");
                    System.out.println("Enter Username");
                    String username = s.next();
                    System.out.println("Enter Password");
                    String password = s.next();
                    Boolean isAdvisor = checkIfAdvisor(username, password);
                    if(isAdvisor){
                        System.out.println("Enter a customer name");
                        String cn = s.next();
                        System.out.println("Enter a opening balance");
                        double d = s.nextDouble();
                        System.out.println("Choose Account type: ");
                        System.out.println("1 : Chequing ");
                        System.out.println("2 : Saving ");
                        System.out.println("3 : Investment");
                        int at = s.nextInt();
                        AccountType accountType = myBank.chooseAccount(at);
                        myBank.openNewAccount(cn, d, accountType);
                    }else{
                        System.out.println(ANSI_RED + "You must authenticate in order to create a new account" + ANSI_RESET);
                    }

                    break;
                case 2: System.out.println("Enter a account number");
                    int an = s.nextInt();
                    System.out.println("Enter a deposit amount");
                    double da = s.nextDouble();
                    myBank.depositTo(an, da);
                    break;
                case 3:
                    System.out.println("Enter a account number");
                    int acn = s.nextInt();
                    System.out.println("Enter a withdraw amount");
                    double wa = s.nextDouble();
                    myBank.withdrawFrom(acn, wa, false);
                    break;
                case 4: System.out.println("Enter a account number");
                    int anum = s.nextInt();
                    myBank.printAccountInfo(anum);
                    break;
                case 5: System.out.println("Pay Utility Bills");
                    System.out.println("Enter a account number");
                    int acctNo = s.nextInt();
                    System.out.println("Choose Utility type");
                    System.out.println("1 : Electricity ");
                    System.out.println("2 : Gas ");
                    System.out.println("3 : Water");
                    System.out.println("4 : Internet");
                    int chooseUtilityType = s.nextInt();
                    UtilityType utilityType = myBank.chooseUtility(chooseUtilityType);
                    System.out.println("Enter your bill amount");
                    double ba = s.nextDouble();
                    myBank.payUtilities(utilityType, acctNo, ba);
                    break;
                case 6:
                    System.out.println("Transfer Between accounts");
                    System.out.println("Enter a account number to withdraw from");
                    int acctFrom = s.nextInt();
                    System.out.println("Enter a account number to deposit to");
                    int acctTo = s.nextInt();
                    System.out.println("Enter Amount");
                    double transferAmount = s.nextDouble();
                    myBank.transferBetweenAccounts(acctFrom, acctTo, transferAmount);
                    break;
                case 7:
                    System.exit(0);
            }
        }
        while (user_choice != '7');
    }

    private static Boolean checkIfAdvisor(String username, String password) {
        String preUsername = "lambton";
        String prePassword = "lambton123";
        if(username.equalsIgnoreCase(preUsername) && password.equalsIgnoreCase(prePassword)){
            return true;
        }else {
            return false;
        }
    }

    static class Bank implements Serializable{

        private List<BankAccount> accounts;

        public Bank() throws IOException {
//            accounts = new BankAccount[5];
//            numOfAccounts = 0;
        }


        private AccountType chooseAccount(int choice){
            switch (choice) {
                case 1:
                    return AccountType.CHEQUING;
                case 2:
                    return AccountType.SAVING;
                case 3:
                    return AccountType.INVESTMENT;
                default:
                    return AccountType.CHEQUING;
            }
        }

        private UtilityType chooseUtility(int choice){
            switch (choice) {
                case 1:
                    return UtilityType.ELECTRICITY;
                case 2:
                    return UtilityType.GAS;
                case 3:
                    return UtilityType.INTERNET;
                default:
                    return UtilityType.WATER;
            }
        }


        /**
         * Create a new bank account.Account number is auto incremented
         * @param customerName
         * @param openingBalance
         * @return
         * @throws IOException
         */
        public void openNewAccount(String customerName, double openingBalance, AccountType accountType) throws IOException {

            BankAccount b = new BankAccount(customerName, openingBalance, accountType);
//            accounts[numOfAccounts] = b;
//            numOfAccounts++;
            String outputOpenAccount = b.customerName + "," + b.accountNum + "," + b.balance + "," + b.accountType;
            saveToFile("Accounts.txt", outputOpenAccount,true );
            System.out.println(ANSI_CYAN + "--------------------------------------------" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "------------Welcome to Lambton Bank---------" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "--------------------------------------------" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "We have opened a new account with the following information" + ANSI_RESET);
            System.out.println("Customer Name: " + b.customerName);
            System.out.println("Account Number: " + b.accountNum);
            System.out.println("Balance: " + b.balance);
            System.out.println("Account Type: " + b.accountType);
            System.out.println(ANSI_CYAN + "--------------------------------------------" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "--------------------------------------------" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "--------------------------------------------" + ANSI_RESET);
        }


        /**
         * Withdraw from account based on acct number and the amount to withdraw
         * @param accountNum
         * @param amount
         */
        public void withdrawFrom(int accountNum, double amount, Boolean billPayment) {
            depositOrWithdraw(accountNum, amount, false, billPayment);
        }

        /**
         * Given an account number and amount, will deposit the amount only with greater than 0.
         * @param accountNum
         * @param amount
         */
        public void depositTo(int accountNum, double amount) {
            depositOrWithdraw(accountNum, amount, true, false);
        }

        private void depositOrWithdraw(int accountNum, double amount, Boolean deposit, Boolean billPayment) {
            File file= new File("Accounts.txt");
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String customerName, accountNumber, balance;
            int lineNum = 0;
            String s;
            String totalStr = "";
            while (scanner.hasNext()){
                s = scanner.next();
                totalStr += s + System.lineSeparator();
                String an = String.valueOf(accountNum);
                lineNum++;
                String[] words = s.split(",");
                customerName = words[0];
                accountNumber = words[1];
                balance = words[2];
                double totalBalance = 0.0;
                if(accountNumber.contains(an)){
//                    scanner.useDelimiter(",");
                    if(amount < 0 ){
                        System.out.println(ANSI_RED + "Must be greater than 0" + ANSI_RESET);
                        break;
                    }else{
                        double currentBalance = Double.parseDouble(balance);
                        if(deposit) {
                            totalBalance = currentBalance + amount;
                        }else{
                            if(amount > currentBalance){
                                System.out.println("Not enough funds to withdraw");
                            }else {
                                totalBalance = currentBalance - amount;
                            }
                        }
                        String updatedBalance = String.valueOf(totalBalance);
                        totalStr = totalStr.replace(balance, updatedBalance);
                        System.out.println(ANSI_GREEN + "--------------------------------------------" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "-----------Updated Account Summary----------" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "--------------------------------------------" + ANSI_RESET);
                        if(deposit) {
                            System.out.println(ANSI_BLUE + "Your deposit is successful" + ANSI_RESET);
                        }else{
                            if(billPayment){
                                System.out.println(ANSI_BLUE + "Your Bill Payment is successful" + ANSI_RESET);

                            }else {
                                System.out.println(ANSI_BLUE + "Your Withdraw is successful" + ANSI_RESET);
                            }
                        }
                        System.out.println("Customer Name: " + customerName);
                        System.out.println("Account Number: " + accountNumber);
                        System.out.println("Balance: " + updatedBalance);
                        System.out.println(ANSI_GREEN + "--------------------------------------------" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "--------------------------------------------" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "--------------------------------------------" + ANSI_RESET);


                    }



                }
                saveToFile("Accounts.txt", totalStr, false);

            }
            scanner.close();
        }

        /**
         * Print account information given the account number
         * @param accountNum
         */
        public void printAccountInfo(int accountNum) {
            Scanner scanner = readFromFile("Accounts.txt");
            String customerName, accountNumber, balance, type;
            int lineNum = 0;
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                lineNum++;
                String[] words = line.split(",");
                customerName = words[0];
                accountNumber = words[1];
                balance = words[2];
                type = words[3];
                if(accountNumber.contains(String.valueOf(accountNum))){
                    System.out.println(ANSI_YELLOW + "--------------------------------------------" + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "--------------Account Summary---------------" + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "--------------------------------------------" + ANSI_RESET);
                    System.out.println("Customer Name: " + customerName);
                    System.out.println("Account Number: " + accountNumber);
                    System.out.println("Balance: " + balance);
                    System.out.println("Account Type: " + type);
                    System.out.println(ANSI_YELLOW + "--------------------------------------------" + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "--------------------------------------------" + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "--------------------------------------------" + ANSI_RESET);


                }

            }
            scanner.close();
        }

        /**
         *  Saves the given text to file, can decide to append or replace as a whole.
         * @param fileName
         * @param text
         * @param append
         * @return
         */
        private String saveToFile(String fileName, String text, boolean append) {
            File f = new File(fileName);
            try {
                FileWriter fileWriter = new FileWriter(f, append);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.println(text);
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Saved Successfully";
        }

        /**
         * Reads the text from file
         * @param fileName
         * @return
         */
        private Scanner readFromFile(String fileName) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(new File(fileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return scanner;
        }


        public void payUtilities(UtilityType utilityType, int acctNo, double ba) {
            withdrawFrom(acctNo, ba, true);
            if(utilityType == UtilityType.ELECTRICITY){
                System.out.println(ANSI_YELLOW + "--------------------------------------------" + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "Your Electricity bill is paid successfully" + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "--------------------------------------------" + ANSI_RESET);
            }else if(utilityType == UtilityType.GAS){
                System.out.println(ANSI_YELLOW + "--------------------------------------------" + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "Your Gas bill is paid successfully" + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "--------------------------------------------" + ANSI_RESET);
            }else if(utilityType == UtilityType.INTERNET){
                System.out.println(ANSI_YELLOW + "--------------------------------------------" + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "Your Gas Internet is paid successfully" + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "--------------------------------------------" + ANSI_RESET);
            }else{
                System.out.println(ANSI_YELLOW + "--------------------------------------------" + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "Your Water bill is paid successfully" + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "--------------------------------------------" + ANSI_RESET);
            }
        }

        public void transferBetweenAccounts(int acctFrom, int acctTo, double transferAmount) {
            if(transferAmount > 0){
                withdrawFrom(acctFrom, transferAmount, false);
                depositTo(acctTo, transferAmount);
            }
        }
    }


    static class BankAccount implements Serializable{
        private int accountNum;
        private String customerName;
        private double balance;
        private AccountType accountType;
        private  static int noOfAccounts=1;


        public BankAccount(String abc, double xyz, AccountType at){
            customerName = abc;
            balance = xyz;
            accountNum = noOfAccounts;
            noOfAccounts ++;
            accountType = at;
        }
    }

    static enum AccountType{
        CHEQUING,
        SAVING,
        INVESTMENT
    }

    static enum UtilityType{
        GAS,
        WATER,
        ELECTRICITY,
        INTERNET
    }



}
