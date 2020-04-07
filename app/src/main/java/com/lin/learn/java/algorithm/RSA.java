package com.lin.learn.java.algorithm;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class RSA {
    /**
     * 指定加密算法
     */
    public static final String RSA = "RSA";

    /**
     * 指定key的位数
     */
    public static int KEY_SIZE = 512;

    /**
     * 公钥存放的文件
     */
    public static String PUBLIC_KEY_FILE = "rsa_test.pub";

    /**
     * 私钥存放的文件
     */
    public static String PRIVATE_KEY_FILE = "rsa_test";


    public static void generateKeyPair() throws Throwable {
        //需要一个安全的随机数源
        SecureRandom secureRandom = new SecureRandom();
        //需要一个KeyPairGenerate
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        //开始产生1和2步中用到的所有数据
        //位数必须是64的倍数，在512-65536之间，默认1024
        keyPairGenerator.initialize(KEY_SIZE);

        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //生成公钥
        Key publicKey = keyPair.getPublic();
        //生成私钥
        Key privateKey = keyPair.getPrivate();

        //写入本地
        writeObject(publicKey, PUBLIC_KEY_FILE);
        writeObject(privateKey, PRIVATE_KEY_FILE);
    }

    /**
     * 加密（客户端使用）
     *
     * @return
     * @throws Throwable
     */
    public static String encrypt(String src) throws Throwable {
        //取出公钥
        Key key = (Key) readObject(PUBLIC_KEY_FILE);
        //用公钥加密
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = src.getBytes();
        //加密
        byte[] buf = cipher.doFinal(bytes);
        //对加密结果进行base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(buf);
    }

    public static String decrypt(String src) throws Throwable {
        //取出私钥
        Key key = (Key) readObject(PRIVATE_KEY_FILE);
        //用私钥解密
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, key);
        BASE64Decoder decoder = new BASE64Decoder();
        //先对密文进行base解码
        byte[] bytes = decoder.decodeBuffer(src);
        //解密
        byte[] buf = cipher.doFinal(bytes);
        return new String(buf);
    }

    public static Object readObject(String path) throws Throwable {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(path));
        return is.readObject();
    }

    public static void writeObject(Object object, String path) throws Throwable {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path));
        os.writeObject(object);
        os.close();
    }
}
