package org.cs;

import java.util.Base64;

public class Base64EncoderDecoder {

    String enCode(String str) {

        byte[] bytesEncoded = Base64.getEncoder().encode(str.getBytes());
        return bytesEncoded.toString();
    }

    String deCode(String encoded) {

        byte[] bytesEncoded = Base64.getDecoder().decode(encoded);
        return bytesEncoded.toString();
    }

}
