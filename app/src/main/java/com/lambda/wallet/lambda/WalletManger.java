package com.lambda.wallet.lambda;

import android.util.Base64;

import com.google.common.collect.ImmutableList;
import com.lambda.wallet.util.Utils;
import com.lzy.okgo.utils.OkLogger;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The type Wallet manger.
 */
public class WalletManger {
    private static final String HD_PATH = "44H/364H/0H/0/0";

    /**
     * Get entropy byte [ ].
     *
     * @return the byte [ ] 生成种子
     */
    public static byte[] getEntropy() {
        byte[] seed = new byte[32];
        new SecureRandom().nextBytes(seed);
        return seed;
    }

    /**
     * Gets random mnemonic.
     * 随机生成助记词
     *
     * @param entropy the entropy
     * @return the random mnemonic
     */
    public static List<String> getRandomMnemonic(byte[] entropy) {
        List<String> result = new ArrayList<>();
        try {
            result = MnemonicCode.INSTANCE.toMnemonic(entropy);

        } catch (MnemonicException.MnemonicLengthException e) {

            e.printStackTrace();

        }
        return result;
    }

    /**
     * To entropy byte [ ].
     *
     * @param words the words
     * @return the byte [ ]
     */
    public static byte[] toEntropy(ArrayList<String> words) {
        try {
            return new MnemonicCode().toEntropy(words);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Get hd seed byte [ ].
     *
     * @param entropy the entropy
     * @return the byte [ ]
     */
    public static byte[] getHDSeed(byte[] entropy) {
        try {
            return MnemonicCode.toSeed(MnemonicCode.INSTANCE.toMnemonic(entropy), "");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets dp address from entropy.
     *
     * @param entropy the entropy
     * @return the dp address from entropy 通过种子获取钱包地址
     */
    public static String getDpAddressFromEntropy(byte[] entropy) {
        return getDpAddressWithPath(Utils.ByteArrayToHexString(getHDSeed(entropy)), 0);
    }

    /**
     * Get byte hd seed from words byte [ ].
     *
     * @param words the words
     * @return the byte [ ]
     */
    public static byte[] getByteHdSeedFromWords(ArrayList<String> words) {
        return getHDSeed(toEntropy(words));
    }


    /**
     * Gets parent path.
     *
     * @return the parent path
     */
    public static List<ChildNumber> getParentPath() {
        return ImmutableList.of(new ChildNumber(44, true), new ChildNumber(364, true), ChildNumber.ZERO_HARDENED, ChildNumber.ZERO);
    }


    /**
     * Gets key with pathfrom entropy.
     *
     * @param entropy the entropy
     * @return the key with pathfrom entropy' 通过种子查私钥
     */
    public static DeterministicKey getKeyWithPathfromEntropy(String entropy) {
        DeterministicKey masterKey = HDKeyDerivation.createMasterPrivateKey(getHDSeed(Utils.HexStringToByteArray(entropy)));
        return new DeterministicHierarchy(masterKey).deriveChild(WalletManger.getParentPath(), true, true, new ChildNumber(0));
    }


    /**
     * Is mnemonic words boolean.
     *
     * @param words the words
     * @return the boolean 检测是否是助记词单词
     */
    public static boolean isMnemonicWords(ArrayList<String> words) {
        boolean result = true;
        List<String> mnemonics = MnemonicCode.INSTANCE.getWordList();
        for (String insert : words) {
            if (!mnemonics.contains(insert)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * The constant PRE_PUB_KEY.
     */
    final static String COSMOS_PRE_PUB_KEY = "eb5ae98721";

    /**
     * Gets dp address.
     *
     * @param pubHex the pub hex
     * @return the dp address 通过公钥hash查询地址
     */
    public static String getDpAddress(String pubHex) {
        String result = null;
        MessageDigest digest = Sha256.getSha256Digest();
        byte[] hash = digest.digest(Utils.HexStringToByteArray(pubHex));

        RIPEMD160Digest digest2 = new RIPEMD160Digest();
        digest2.update(hash, 0, hash.length);

        byte[] hash3 = new byte[digest2.getDigestSize()];
        digest2.doFinal(hash3, 0);

        try {
            byte[] converted = convertBits(hash3, 8, 5, true);

            result = bech32Encode("lambda".getBytes(), converted);
        } catch (Exception e) {
            OkLogger.w("Secp256k1 genDPAddress Error");
        }

        return result;
    }

    /**
     * 通过地址获取对应的节点地址
     *
     * @param address
     * @return
     */
    public static String getDevAddress(String address) {
        String result = null;
        HrpAndData hrpAndData = bech32Decode(address);
        result = bech32Encode("lambdavaloper".getBytes(), hrpAndData.getData());
        return result;
    }

    /**
     * Gets cosmos user dp pub key.
     *
     * @param pubHex the pub hex
     * @return the cosmos user dp pub key 通过公钥hash获取钱包显示公钥
     */
    public static String getUserDpPubKey(String pubHex) {
        String result = null;
        String sumHex = COSMOS_PRE_PUB_KEY + pubHex;
        byte[] sumHexByte = Utils.HexStringToByteArray(sumHex);
        try {
            byte[] converted = convertBits(sumHexByte, 8, 5, true);
            result = bech32Encode("lambdapub".getBytes(), converted);
        } catch (Exception e) {
            OkLogger.w("getLambdaUserDpPubKey Error");

        }
        return result;
    }

    /**
     * Gets pub key value.
     * 通过私钥查原始公钥
     *
     * @param privateKey the private key
     * @return the pub key value
     */
    public static String getPubKeyValue(String privateKey) {
        String result = "";
        ECKey ecKey = ECKey.fromPrivate(new BigInteger(privateKey, 16));
        try {
            byte[] data = ecKey.getPubKey();
            result = Base64.encodeToString(data, Base64.DEFAULT).replace("\n", "");
        } catch (Exception e) {

        }
        return result;
    }


    /**
     * Gets private key from mnemonic code.
     *
     * @param words the words
     * @return the private key from mnemonic code 通过助记词获取私钥
     */
    public static String getPrivateKeyFromMnemonicCode(List<String> words) {
        byte[] seed = MnemonicCode.INSTANCE.toSeed(words, "");
        DeterministicKey key = HDKeyDerivation.createMasterPrivateKey(seed);

        List<ChildNumber> childNumbers = HDUtils.parsePath(HD_PATH);
        for (ChildNumber cn : childNumbers) {
            key = HDKeyDerivation.deriveChildKey(key, cn);
        }
        return key.getPrivateKeyAsHex();
    }

    /**
     * Gets address from private key.
     *
     * @param privateKey the private key
     * @return the address from private key 通过私钥获取地址
     */
    public static String getAddressFromPrivateKey(String privateKey) {
        ECKey ecKey = ECKey.fromPrivate(new BigInteger(privateKey, 16));
        return getAddressFromECKey(ecKey);
    }

    /**
     * Gets pub from private key.
     *
     * @param privateKey the private key
     * @return the pub from private key 通过私钥获取钱包公钥
     */
    public static String getPubFromPrivateKey(String privateKey) {
        ECKey ecKey = ECKey.fromPrivate(new BigInteger(privateKey, 16));
        return getUserDpPubKey(ecKey.getPublicKeyAsHex());
    }

    /**
     * Gets address from ec key.
     *
     * @param ecKey the ec key
     * @return the address from ec key 通过钱包key获取钱包地址
     */
    public static String getAddressFromECKey(ECKey ecKey) {
        String result = null;
        String pubHex = ecKey.getPublicKeyAsHex();

        MessageDigest digest = Sha256.getSha256Digest();
        byte[] hash = digest.digest(Utils.HexStringToByteArray(pubHex));

        RIPEMD160Digest digest2 = new RIPEMD160Digest();
        digest2.update(hash, 0, hash.length);
        byte[] hash3 = new byte[digest2.getDigestSize()];
        digest2.doFinal(hash3, 0);
        try {
            byte[] converted = convertBits(hash3, 8, 5, true);
            result = bech32Encode("lambda".getBytes(), converted);
        } catch (Exception e) {
            OkLogger.w("getLambdaUserDpPubKey Error");

        }
        return result;
    }


    /**
     * Gets dp address with path.
     *
     * @param seed the seed
     * @param path the path
     * @return the dp address with path
     */
    public static String getDpAddressWithPath(String seed, int path) {
        String result = "";
        //using Secp256k1
        DeterministicKey childKey = new DeterministicHierarchy(HDKeyDerivation.createMasterPrivateKey(Utils.HexStringToByteArray(seed))).deriveChild(WalletManger.getParentPath(), true, true, new ChildNumber(path));
        result = getDpAddress(childKey.getPublicKeyAsHex());
        return result;
    }


    private static final String CHARSET = "qpzry9x8gf2tvdw0s3jn54khce6mua7l";

    /**
     * Convert bits byte [ ].
     *
     * @param data     the data
     * @param frombits the frombits
     * @param tobits   the tobits
     * @param pad      the pad
     * @return the byte [ ]
     * @throws Exception the exception
     */
    public static byte[] convertBits(byte[] data, int frombits, int tobits, boolean pad) throws Exception {
        int acc = 0;
        int bits = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int maxv = (1 << tobits) - 1;
        for (int i = 0; i < data.length; i++) {
            int value = data[i] & 0xff;
            if ((value >>> frombits) != 0) {
                throw new Exception("invalid data range: data[" + i + "]=" + value + " (frombits=" + frombits + ")");
            }
            acc = (acc << frombits) | value;
            bits += frombits;
            while (bits >= tobits) {
                bits -= tobits;
                baos.write((acc >>> bits) & maxv);
            }
        }
        if (pad) {
            if (bits > 0) {
                baos.write((acc << (tobits - bits)) & maxv);
            }
        } else if (bits >= frombits) {
            throw new Exception("illegal zero padding");
        } else if (((acc << (tobits - bits)) & maxv) != 0) {
            throw new Exception("non-zero padding");
        }
        return baos.toByteArray();
    }

    /**
     * Bech 32 encode string.
     *
     * @param hrp  the hrp
     * @param data the data
     * @return the string
     */
    public static String bech32Encode(byte[] hrp, byte[] data) {
        byte[] chk = createChecksum(hrp, data);
        byte[] combined = new byte[chk.length + data.length];

        System.arraycopy(data, 0, combined, 0, data.length);
        System.arraycopy(chk, 0, combined, data.length, chk.length);

        byte[] xlat = new byte[combined.length];
        for (int i = 0; i < combined.length; i++) {
            xlat[i] = (byte) CHARSET.charAt(combined[i]);
        }

        byte[] ret = new byte[hrp.length + xlat.length + 1];
        System.arraycopy(hrp, 0, ret, 0, hrp.length);
        System.arraycopy(new byte[]{0x31}, 0, ret, hrp.length, 1);
        System.arraycopy(xlat, 0, ret, hrp.length + 1, xlat.length);

        return new String(ret);
    }

    public static HrpAndData bech32Decode(String bech) {

        if (!bech.equals(bech.toLowerCase()) && !bech.equals(bech.toUpperCase())) {
            throw new IllegalArgumentException("bech32 cannot mix upper and lower case");
        }

        byte[] buffer = bech.getBytes();
        for (byte b : buffer) {
            if (b < 0x21 || b > 0x7e)
                throw new IllegalArgumentException("bech32 characters out of range");
        }

        bech = bech.toLowerCase();
        int pos = bech.lastIndexOf("1");
        if (pos < 1) {
            throw new IllegalArgumentException("bech32 missing separator");
        } else if (pos + 7 > bech.length()) {
            throw new IllegalArgumentException("bech32 separator misplaced");
        } else if (bech.length() < 8) {
            throw new IllegalArgumentException("bech32 input too short");
        } else if (bech.length() > 90) {
            throw new IllegalArgumentException("bech32 input too long");
        }

        String s = bech.substring(pos + 1);
        for (int i = 0; i < s.length(); i++) {
            if (CHARSET.indexOf(s.charAt(i)) == -1) {
                throw new IllegalArgumentException("bech32 characters  out of range");
            }
        }

        byte[] hrp = bech.substring(0, pos).getBytes();

        byte[] data = new byte[bech.length() - pos - 1];
        for (int j = 0, i = pos + 1; i < bech.length(); i++, j++) {
            data[j] = (byte) CHARSET.indexOf(bech.charAt(i));
        }

        if (!verifyChecksum(hrp, data)) {
            throw new IllegalArgumentException("invalid bech32 checksum");
        }

        byte[] ret = new byte[data.length - 6];
        System.arraycopy(data, 0, ret, 0, data.length - 6);

        return new HrpAndData(hrp, ret);
    }

    private static boolean verifyChecksum(byte[] hrp, byte[] data) {
        byte[] exp = hrpExpand(hrp);

        byte[] values = new byte[exp.length + data.length];
        System.arraycopy(exp, 0, values, 0, exp.length);
        System.arraycopy(data, 0, values, exp.length, data.length);

        return (1 == polymod(values));
    }

    public static class HrpAndData {

        public byte[] hrp;
        public byte[] data;

        public HrpAndData(byte[] hrp, byte[] data) {
            this.hrp = hrp;
            this.data = data;
        }

        public byte[] getHrp() {
            return this.hrp;
        }

        public byte[] getData() {
            return this.data;
        }

        @Override
        public String toString() {
            return "HrpAndData [hrp=" + Arrays.toString(hrp) + ", data=" + Arrays.toString(data) + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Arrays.hashCode(data);
            result = prime * result + Arrays.hashCode(hrp);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            HrpAndData other = (HrpAndData) obj;
            if (!Arrays.equals(data, other.data))
                return false;
            if (!Arrays.equals(hrp, other.hrp))
                return false;
            return true;
        }
    }

    private static byte[] createChecksum(byte[] hrp, byte[] data) {
        byte[] zeroes = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        byte[] expanded = hrpExpand(hrp);
        byte[] values = new byte[zeroes.length + expanded.length + data.length];

        System.arraycopy(expanded, 0, values, 0, expanded.length);
        System.arraycopy(data, 0, values, expanded.length, data.length);
        System.arraycopy(zeroes, 0, values, expanded.length + data.length, zeroes.length);

        int polymod = polymod(values) ^ 1;
        byte[] ret = new byte[6];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (byte) ((polymod >> 5 * (5 - i)) & 0x1f);
        }

        return ret;
    }

    private static int polymod(byte[] values) {
        final int[] GENERATORS = {0x3b6a57b2, 0x26508e6d, 0x1ea119fa, 0x3d4233dd, 0x2a1462b3};

        int chk = 1;

        for (byte b : values) {
            byte top = (byte) (chk >> 0x19);
            chk = b ^ ((chk & 0x1ffffff) << 5);
            for (int i = 0; i < 5; i++) {
                chk ^= ((top >> i) & 1) == 1 ? GENERATORS[i] : 0;
            }
        }

        return chk;
    }

    private static byte[] hrpExpand(byte[] hrp) {
        byte[] buf1 = new byte[hrp.length];
        byte[] buf2 = new byte[hrp.length];
        byte[] mid = new byte[1];

        for (int i = 0; i < hrp.length; i++) {
            buf1[i] = (byte) (hrp[i] >> 5);
        }
        mid[0] = 0x00;
        for (int i = 0; i < hrp.length; i++) {
            buf2[i] = (byte) (hrp[i] & 0x1f);
        }

        byte[] ret = new byte[(hrp.length * 2) + 1];
        System.arraycopy(buf1, 0, ret, 0, buf1.length);
        System.arraycopy(mid, 0, ret, buf1.length, mid.length);
        System.arraycopy(buf2, 0, ret, buf1.length + mid.length, buf2.length);

        return ret;
    }


}
