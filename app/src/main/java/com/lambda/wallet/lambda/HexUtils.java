package com.lambda.wallet.lambda;

import org.bouncycastle.util.encoders.Hex;

/**
 * Utilities for going to and from ASCII-HEX representation.
 */
public class HexUtils {

    /**
     * Encodes an array of bytes as hex symbols.
     *
     * @param bytes
     *           the array of bytes to encode
     * @return the resulting hex string
     */
    public static String toHex(byte[] bytes) {
        return toHex(bytes, null);
    }

    /**
     * Encodes an array of bytes as hex symbols.
     *
     * @param bytes
     *           the array of bytes to encode
     * @param separator
     *           the separator to use between two bytes, can be null
     * @return the resulting hex string
     */
    public static String toHex(byte[] bytes, String separator) {
        return toHex(bytes, 0, bytes.length, separator);
    }

    /**
     * Encodes an array of bytes as hex symbols.
     *
     * @param bytes
     *           the array of bytes to encode
     * @param offset
     *           the start offset in the array of bytes
     * @param length
     *           the number of bytes to encode
     * @return the resulting hex string
     */
    public static String toHex(byte[] bytes, int offset, int length) {
        return toHex(bytes, offset, length, null);
    }

    /**
     * Encodes a single byte to hex symbols.
     *
     * @param b the byte to encode
     * @return the resulting hex string
     */
    public static String toHex(byte b) {
        StringBuilder sb = new StringBuilder();
        appendByteAsHex(sb, b);
        return sb.toString();
    }


    /**
     * Encodes an array of bytes as hex symbols.
     *
     * @param bytes
     *           the array of bytes to encode
     * @param offset
     *           the start offset in the array of bytes
     * @param length
     *           the number of bytes to encode
     * @param separator
     *           the separator to use between two bytes, can be null
     * @return the resulting hex string
     */
    public static String toHex(byte[] bytes, int offset, int length, String separator) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int unsignedByte = bytes[i + offset] & 0xff;

            if (unsignedByte < 16) {
                result.append("0");
            }

            result.append(Integer.toHexString(unsignedByte));
            if (separator != null && i + 1 < length) {
                result.append(separator);
            }
        }
        return result.toString();
    }

    /**
     * Get the byte representation of an ASCII-HEX string.
     *
     * @param hexString
     *           The string to convert to bytes
     * @return The byte representation of the ASCII-HEX string.
     */
    public static byte[] toBytes(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            throw new RuntimeException("Input string must contain an even number of characters");
        }
        char[] hex = hexString.toCharArray();
        int length = hex.length / 2;
        byte[] raw = new byte[length];
        for (int i = 0; i < length; i++) {
            int high = Character.digit(hex[i * 2], 16);
            int low = Character.digit(hex[i * 2 + 1], 16);
            if (high < 0 || low < 0){
                throw new RuntimeException("Invalid hex digit " + hex[i * 2] + hex[i * 2 + 1]);
            }
            int value = (high << 4) | low;
            if (value > 127)
                value -= 256;
            raw[i] = (byte) value;
        }
        return raw;
    }

    public static byte[] toBytesReversed( String hexString) {
         byte[] rawBytes = toBytes( hexString );

        for ( int i = 0; i < rawBytes.length / 2;i++ ) {
            byte temp = rawBytes[ rawBytes.length - i - 1];
            rawBytes[ rawBytes.length - i - 1] = rawBytes[ i ];
            rawBytes[ i ] = temp;
        }

        return rawBytes;
    }

    public static void appendByteAsHex(StringBuilder sb, byte b) {
        int unsignedByte = b & 0xFF;
        if (unsignedByte < 16) {
            sb.append("0");
        }
        sb.append(Integer.toHexString(unsignedByte));
    }

    /**
     * 字符串转换成十六进制字符串
     * @param
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str)
    {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++)
        {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 十六进制转换字符串
     * @param   :[616C6B])
     * @return String 对应的字符串
     */
    public static String hexStr2Str(String hexStr)
    {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++)
        {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * bytes转换成十六进制字符串
     * @param b byte数组
     * @return String 每个Byte值之间空格分隔
     */
    public static String byte2HexStr(byte[] b)
    {
        String stmp="";
        StringBuilder sb = new StringBuilder("");
        for (int n=0;n<b.length;n++)
        {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length()==1)? "0"+stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * bytes字符串转换为Byte值
     * @param src Byte字符串，每个Byte之间没有分隔符
     * @return byte[]
     */
    public static byte[] hexStr2Bytes(String src)
    {
        int m=0,n=0;
        int l=src.length()/2;
        System.out.println(l);
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++)
        {
            m=i*2+1;
            n=m+1;
            ret[i] = Byte.decode("0x" + src.substring(i*2, m) + src.substring(m,n));
        }
        return ret;
    }

    /**
     * String的字符串转换成unicode的String
     * @param  strText 全角字符串
     * @return String 每个unicode之间无分隔符
     * @throws Exception
     */
    public static String strToUnicode(String strText)
            throws Exception
    {
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++)
        {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128)
                str.append("\\u" + strHex);
            else // 低位在前面补00
                str.append("\\u00" + strHex);
        }
        return str.toString();
    }

    /**
     * unicode的String转换成String的字符串
     * @param  hex 16进制值字符串 （一个unicode为2byte）
     * @return String 全角字符串
     */
    public static String unicodeToString(String hex)
    {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++)
        {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // 高位需要补上00再转
            String s1 = s.substring(2, 4) + "00";
            // 低位直接转
            String s2 = s.substring(4);
            // 将16进制的string转为int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // 将int转换为字符
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        return Hex.decode(s);
    }

    public static String bytesToHex(byte[] bytes) {
        return Hex.toHexString(bytes);
    }

}

