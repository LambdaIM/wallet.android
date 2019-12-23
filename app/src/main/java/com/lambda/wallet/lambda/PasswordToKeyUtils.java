package com.lambda.wallet.lambda;

import com.lambda.wallet.lambda.crypto.BCrypt;
import com.lambda.wallet.lambda.crypto.TweetNacl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Random;

import static com.lambda.wallet.lambda.crypto.TweetNacl.Box.nonceLength;

/**
 * 加密工具类
 */
public class PasswordToKeyUtils {

    public static HashMap encryptPwdForPrivate(String pwd, String privateKey) throws UnsupportedEncodingException {
        //生成用户的盐
        Random random = new Random();
        byte[] usersalt= new byte[16];
        random.nextBytes(usersalt);

        String usersalt2base64 = android.util.Base64.encodeToString(usersalt, android.util.Base64.DEFAULT).replace("\n", "");

        //生成加密盐
        String salt = BCrypt.gensalt(12, usersalt);

        String hashedPassword = BCrypt.hashpw(pwd, salt);

        String userkey = Sha256.from(hashedPassword.getBytes("utf-8")).toString();

        byte[] userkeyF8 =HexUtils.hexStringToByteArray(userkey);

        byte[] praviteFB = addBytes("22222".getBytes("utf-8"), HexUtils.hexStringToByteArray(privateKey));

        // explicit nonce
        byte[] theNonce = new byte[nonceLength];
        TweetNacl.randombytes(theNonce, nonceLength);


        //TweetNacl加密
        TweetNacl.SecretBox sbox = new TweetNacl.SecretBox(userkeyF8);
        byte[] cipher = sbox.box(praviteFB, theNonce);

        byte[] result = addBytes(theNonce, cipher);

        String theResult = android.util.Base64.encodeToString(result, android.util.Base64.DEFAULT).replace("\n", "");

        HashMap hashMap = new HashMap();
        hashMap.put("userSalt", usersalt2base64);
        hashMap.put("result", theResult);

        return hashMap;
    }

    public static String dncryptPwdForPrivate(String pwd, String privateKey,String salt1) throws UnsupportedEncodingException {
        //生成用户的盐
        byte[] usersalt = android.util.Base64.decode(salt1, android.util.Base64.DEFAULT);

        //生成加密盐
        String salt = BCrypt.gensalt(12, usersalt);
        String hashedPassword = BCrypt.hashpw(pwd, salt);

        String userkey = Sha256.from(hashedPassword.getBytes("utf-8")).toString();

        byte[] userkeyF8 =HexUtils.hexStringToByteArray(userkey);

        byte[] praviteFB = android.util.Base64.decode(privateKey, android.util.Base64.DEFAULT);

        // explicit nonce
        byte[] theNonce = subBytes(praviteFB,0,24);


        //TweetNacl解密
        byte[] data = subBytes(praviteFB,24,praviteFB.length-24);

        TweetNacl.SecretBox sbox = new TweetNacl.SecretBox(userkeyF8);
        byte[] message = sbox.open(data, theNonce);


        try {
            return HexUtils.bytesToHex(message).substring(10,HexUtils.bytesToHex(message).length());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  
     *  *  
     *  * @param data1 
     *  * @param data2 
     *  * @return data1 与 data2拼接的结果 
     *  
     */
    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;
    }

    /**
     * 从一个byte[]数组中截取一部分
     * @param src
     * @param begin
     * @param count
     * @return
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i=begin; i<begin+count; i++) bs[i-begin] = src[i];
        return bs;
    }

}
