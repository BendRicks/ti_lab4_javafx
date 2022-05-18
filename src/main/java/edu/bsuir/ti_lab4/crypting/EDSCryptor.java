package edu.bsuir.ti_lab4.crypting;

import edu.bsuir.ti_lab4.logic.ArithmeticOperations;

import java.io.File;
import java.io.IOException;

public class EDSCryptor {

    //Переписать на параметры text и key
    public static CryptResult cryptData(File openFile, File saveFile, EDSKey key) throws IOException {
        long hash = HashCalculator.getHash(FIO.readFileAsByteArray(openFile), key.getR());
        long s = ArithmeticOperations.fastExpModule(hash, key.getD(), key.getR());
        FIO.signFileWithEDS(openFile, saveFile, s);
        key.setEds(s);
        return new CryptResult(hash, key);
    }

    public static CryptResult DecryptData(File openFile, EDSKey key) throws IOException {
        byte[] data = FIO.readFileWithEDSasByteArray(openFile, key);
        long hash = HashCalculator.getHash(data, key.getR());
        long s = ArithmeticOperations.fastExpModule(key.getEDS(), key.getE(), key.getR());
        return new CryptResult(hash, s, key);
    }

}
