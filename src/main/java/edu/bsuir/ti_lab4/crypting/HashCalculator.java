package edu.bsuir.ti_lab4.crypting;

import edu.bsuir.ti_lab4.logic.ArithmeticOperations;

public class HashCalculator {

    private static final long startH = 100;

    public static long getHash(byte[] bytesArray, long n) {
        long h = startH;
        for (int i = 0; i < bytesArray.length; i++) {
            h = ArithmeticOperations.fastExpModule(h + bytesArray[i], 2, n);
        }
        return h;
    }

}
