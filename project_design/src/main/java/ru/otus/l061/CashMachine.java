package ru.otus.l061;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CashMachine implements ICashMachine {

    private long balance = 0;

    private Map<Integer, ArrayList<Bill>> billsMap = new TreeMap<>();

    @Override
    public void depositMoney(List<Bill> bills) {
        for (Bill bill : bills) {
            balance += bill.getNominal();
            insertBill(bill);
        }
    }

    private void insertBill(Bill bill) {
        int nominal = bill.getNominal();
        ArrayList<Bill> items = billsMap.get(nominal);
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(bill);
        billsMap.put(nominal, items);
    }

    @Override
    public List<Bill> getMoney(long value) {
        if (value > balance &&
                !isValidByNominal(value)) {
            return null;
        }
        return null;
    }

    private boolean isValidByNominal(long value) {
        return value % getMinimalNominal() == 0;
    }

    @Override
    public long getBalance() {
        return balance;
    }

    public void printDump() {
        System.out.println("Balance: " + String.valueOf(balance));
        for (Map.Entry<Integer, ArrayList<Bill>> entry :
                billsMap.entrySet()) {
            ArrayList<Bill> items = billsMap.get(entry.getKey());
            if (items != null && items.size() > 0) {
                System.out.println("Bill nominal " + entry.getKey() + " count " + items.size());
            }
        }
        System.out.println("Minimum nominal "+getMinimalNominal());
    }

    int getMinimalNominal() {
        int minNominal = Integer.MAX_VALUE;
        for (Map.Entry<Integer, ArrayList<Bill>> entry : billsMap.entrySet()) {
            ArrayList<Bill> items = entry.getValue();
            if (items != null &&
                    items.size() > 0 &&
                    entry.getKey() < minNominal) {
                minNominal = entry.getKey();
            }
        }
        return minNominal;
    }
}
