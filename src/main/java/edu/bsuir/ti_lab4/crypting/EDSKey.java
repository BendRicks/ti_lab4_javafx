package edu.bsuir.ti_lab4.crypting;

import edu.bsuir.ti_lab4.logic.ArithmeticOperations;

public class EDSKey {

    private long p;
    private long q;
    private long r;
    private long e;
    private long d;
    private long eds;

    public EDSKey(long p, long q, long d) {
        this.p = p;
        this.q = q;
        this.r = p * q;
        this.d = d;
        e = ArithmeticOperations.extendedEuclidFunc(d, ArithmeticOperations.calcEulerFunc(p, q));
    }

    public long getP() {
        return p;
    }

    public long getQ() {
        return q;
    }

    public long getR() {
        return r;
    }

    public long getE() {
        return e;
    }

    public long getD() {
        return d;
    }

    public void setEds(long eds) {
        this.eds = eds;
    }

    public long getEDS() {
        return eds;
    }

}
