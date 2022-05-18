package edu.bsuir.ti_lab4.crypting;

public class CryptResult {

    private long hashCalculated;
    private long hashRetrieved;
    private boolean isValid;
    private EDSKey key;

    public CryptResult(long hashCalculated, EDSKey key) {
        this.hashCalculated = hashCalculated;
        this.key = key;
    }

    public CryptResult(long hashCalculated, long hashRetrieved, EDSKey key) {
        this.hashCalculated = hashCalculated;
        this.hashRetrieved = hashRetrieved;
        isValid = hashCalculated == hashRetrieved;
        this.key = key;
    }


    public long getHashCalculated() {
        return hashCalculated;
    }

    public long getHashRetrieved() {
        return hashRetrieved;
    }

    public boolean isValid() {
        return isValid;
    }

    public EDSKey getKey() {
        return key;
    }
}
