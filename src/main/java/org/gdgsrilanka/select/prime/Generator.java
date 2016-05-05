package org.gdgsrilanka.select.prime;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tharu on 2016-05-05.
 */
public class Generator {

    public BigInteger getXOR(BigInteger nameHash, BigInteger timeHash) {
        return nameHash.xor(timeHash);
    }

    public BigInteger getValueFromHash(String hash) {
        return new BigInteger(hash, 16);

    }

    public String getHash(String plain) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return new String(digest.digest(plain.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;

    }

    public BigInteger getHashIntegerValueForString(String string) {
        return getValueFromHash(getHash(string));
    }

    public boolean isPrime(BigInteger bigInteger) {
        return bigInteger.isProbablePrime(5);  //the probability to detect the prime is 1 - (1/2)^5 = 0.96875
    }
}
