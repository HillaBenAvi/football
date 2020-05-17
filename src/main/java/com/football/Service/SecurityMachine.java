package com.football.Service;

import org.springframework.stereotype.Repository;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Repository
public class SecurityMachine {
    public static final String DEFAULT_ENCODING = "UTF-8";
    BASE64Encoder enc = new BASE64Encoder();
    BASE64Decoder dec = new BASE64Decoder();
    private String key ;

    public SecurityMachine(){
        key = readFromDisc();
    }

    private String readFromDisc() {
        byte[] keyInBytes = getbythes("key_short");
        String keyInString = "";
        for (byte b : keyInBytes) {
            keyInString += b;
        }
        return keyInString;
    }

    /**
     * this function read a byte[] from a absolute path
     * @param pathToRead
     * @return
     */
    public static byte[] getbythes(String pathToRead) {
        try {
            byte[] result = Files.readAllBytes(Paths.get(pathToRead));
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String encrypt(String password) {
        password = xorMessage(password, key);
        String encoded = base64encode(password);
        return encoded;
    }

    public String decrypt(String encodedPassword) {
        String password;
        password = base64decode(encodedPassword);
        String theRealOne = xorMessage(password, key);
        return theRealOne;
    }

    public String base64encode(String text) {
        try {
            return enc.encode(text.getBytes(DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public String base64decode(String text) {
        try {
            return new String(dec.decodeBuffer(text), DEFAULT_ENCODING);
        } catch (IOException e) {
            return null;
        }
    }

    public String xorMessage(String message, String key) {
        try {
            if (message == null || key == null) return null;

            char[] keys = key.toCharArray();
            char[] mesg = message.toCharArray();

            int ml = mesg.length;
            int kl = keys.length;
            char[] newmsg = new char[ml];

            for (int i = 0; i < ml; i++) {
                newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
            }//for i

            return new String(newmsg);
        } catch (Exception e) {
            return null;
        }
    }
}

