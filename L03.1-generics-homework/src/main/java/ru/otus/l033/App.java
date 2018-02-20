package ru.otus.l033;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class App {
    public static void main(String[] args) {

        String[] addedElements = new String[]{"11", "12", "13"};

        List<String> arrayList1 = new ArrayList<>();
        List<String> list1 = new MyArrayList<>();

        Collections.addAll(arrayList1, addedElements);
        Collections.addAll(list1, addedElements);

        if (isCollectionsEquals(arrayList1, list1)) {
            System.out.println("Collections.addAll: ok");
        } else {
            System.out.println("Collections.addAll: error");
        }

        List<String> arrayList2 = new ArrayList<>();
        List<String> list2 = new MyArrayList<>();

        String[] addedElements2 = new String[]{"21", "22", "23", "24"};

        Collections.addAll(arrayList2, addedElements2);
        Collections.addAll(list2, addedElements2);


        Collections.copy(arrayList2, arrayList1);
        Collections.copy(list2, list1);
        if (isCollectionsEquals(arrayList2, list2)) {
            System.out.println("Collections.copy: ok");
        } else {
            System.out.println("Collections.copy: error");
        }

        Comparator<String> comparator = (o1, o2) -> {
            if (o1 == null) {
                return -1;
            }
            if (o2 == null) {
                return 1;
            }
            if (o1.equals(o2)) {
                return 0;
            }
            return o1.compareTo(o2);
        };
        String[] names = new String[]{"Alexander", "Zachary", "Brandon", "Xavier"};
        List<String> arrayList3 = new ArrayList<>();
        Collections.addAll(arrayList3, names);
        Collections.sort(arrayList3, comparator);

        List<String> list3 = new MyArrayList<>();
        Collections.addAll(list3, names);
        Collections.sort(list3, comparator);

        if (isCollectionsEquals(arrayList3, list3)) {
            System.out.println("Collections.sort: ok");
        } else {
            System.out.println("Collections.sort: error");
        }
    }

    private static boolean isCollectionsEquals(List arrayList,
                                               List myArrayList) {
        if (arrayList.size() != myArrayList.size()) {
            return false;
        }
        for (int i = 0; i < myArrayList.size(); i++) {
            Object original = arrayList.get(i);
            Object comparable = myArrayList.get(i);
            if (original == null && comparable == null) {
                continue;
            }
            if (original == null || comparable == null) {
                return false;
            }
            if (!original.equals(comparable)) {
                return false;
            }
        }
        return true;
    }
}
