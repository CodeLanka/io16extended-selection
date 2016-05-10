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
        System.out.println(hash);
        return new BigInteger(hash, 16);

    }

    public String getHash(String plain) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return bytesToStr(digest.digest(plain.getBytes()));
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


    private String bytesToStr(byte[] bytes)
    {
        String result = "";
        if(bytes != null) {

            String[] letters = new String[]{"0", "1" , "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
            for(int i=0;i < bytes.length;i++)
            {
                result += letters[(bytes[i] & 0x0F)];
                result += letters[((bytes[i] >> 4) & 0x0F)];
            }
        }
        return result;
    }
}
