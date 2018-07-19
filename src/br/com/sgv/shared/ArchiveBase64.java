package br.com.sgv.shared;

import java.util.Base64;

/**
 * @author Anderson Junior Rodrigues
 */

public class ArchiveBase64 {
    
    public static String encode(String text) {
        byte[] encodeBytes = text.getBytes();
        String encode = Base64.getEncoder().encodeToString(encodeBytes);
        
        return encode;
    }
    
    public static String decode(String text) {
        byte[] decodeBytes = Base64.getMimeDecoder().decode(text);
        String decode = new String(decodeBytes);
        
        return decode;
    }
}
