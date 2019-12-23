package com.lambda.wallet.lambda;

import android.util.Base64;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/11/22
 * Time: 17:02
 */
public class SignUtils {
    public static String sign( String privateKey,byte[] msg) {
        ECKey k = ECKey.fromPrivate(new BigInteger(privateKey, 16));

        return getSignature(k,msg);
    }
    /**
     * Gets signature.
     *
     * @param key        the key
     * @param toSignByte the to sign byte
     * @return the signature
     */
    public static String getSignature(ECKey key, byte[] toSignByte) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] toSignHash = digest.digest(toSignByte);
        ECKey.ECDSASignature Signature = key.sign(Sha256Hash.wrap(toSignHash));
        byte[] sigData = new byte[64];
        System.arraycopy(integerToBytes(Signature.r, 32), 0, sigData, 0, 32);
        System.arraycopy(integerToBytes(Signature.s, 32), 0, sigData, 32, 32);
        return Base64.encodeToString(sigData, Base64.DEFAULT).replace("\n", "");
    }
    /**
     * Integer to bytes byte [ ].
     *
     * @param s      the s
     * @param length the length
     * @return the byte [ ]
     */
    public static byte[] integerToBytes(BigInteger s, int length) {
        byte[] bytes = s.toByteArray();

        if (length < bytes.length) {
            byte[] tmp = new byte[length];
            System.arraycopy(bytes, bytes.length - tmp.length, tmp, 0, tmp.length);
            return tmp;
        } else if (length > bytes.length) {
            byte[] tmp = new byte[length];
            System.arraycopy(bytes, 0, tmp, tmp.length - bytes.length, bytes.length);
            return tmp;
        }
        return bytes;
    }


}
