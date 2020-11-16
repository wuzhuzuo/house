package com.w.utils;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

@Slf4j
public class WXBizDataCrypt {

    public static String decryptNew(String encryptedData, String sessionKey, String iv) throws Exception {

        String result = "";
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                result = new String(resultByte, "UTF-8");
            }
        } catch (NoSuchAlgorithmException e) {
            log.info(e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            log.info(e.getMessage(), e);
        } catch (InvalidParameterSpecException e) {
            log.info(e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            log.info(e.getMessage(), e);
        } catch (BadPaddingException e) {
            log.info(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage(), e);
        } catch (InvalidKeyException e) {
            log.info(e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException e) {
            log.info(e.getMessage(), e);
        } catch (NoSuchProviderException e) {
            log.info(e.getMessage(), e);
        }
        return result;
    }






    /*public static JSONObject getPhoneN0(String encryptedData,String session_key,  String iv) {
        String session_key1=session_key.replace(" ","+");
        String encryptedData1=encryptedData.replace(" ","+");
        String iv1=iv.replace(" ","+");
        byte[] dataByte = Base64.decodeBase64(encryptedData1);
        byte[] keyByte = Base64.decodeBase64(session_key1);
        byte[] ivByte = Base64.decodeBase64(iv1);
        try {
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
*/



    /**
     * AES解密
     *
     * @param data //密文，被加密的数据
     * @param key  //秘钥
     * @param iv   //偏移量
     * @return
     * @throws Exception
     */
   /* public static String decrypt1(String data, String key, String iv) {

        log.info ("调用解密方法开始解密");
        String session_key1=key.replace(" ","+");
        String encryptedData1=data.replace(" ","+");
        String iv1=iv.replace(" ","+");
        //被加密的数据
        byte[] dataByte = Base64.decodeBase64(encryptedData1);
        log.info("被加密的数据",dataByte.toString());
        //加密秘钥
        byte[] keyByte = Base64.decodeBase64(session_key1);
        log.info("加密秘钥",keyByte.toString());
        //偏移量
        byte[] ivByte = Base64.decodeBase64(iv1);
        log.info("偏移量",ivByte.toString());
        try {
            log.info("抛出异常后的解析");
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivByte);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            log.info("解密完成返回");
            return new String(cipher.doFinal(dataByte), "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

*/


}
