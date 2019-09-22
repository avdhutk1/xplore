package com.audhut.spring5ex.impl;

import java.security.MessageDigest;

/**
 * Created by avdhut on 31/1/19.
 */

/**
 * used to demonstrate factory method implementation in Spring
 */
public class MessageDigester {

    private MessageDigest digest1;
    private MessageDigest digest2;

    public MessageDigest getDigest1() {
        return digest1;
    }

    public void setDigest1(MessageDigest digest1) {
        this.digest1 = digest1;
    }

    public MessageDigest getDigest2() {
        return digest2;
    }

    public void setDigest2(MessageDigest digest2) {
        this.digest2 = digest2;
    }

   public void digest(String msg){
       System.out.println("Using digest 1");
       digest(msg, digest1);

       System.out.println("Using digest 2");
       digest(msg, digest2);
   }

   private void digest(String msg, MessageDigest md){
       System.out.println("Using algorithm  " + md.getAlgorithm());
       md.reset();
       byte[] bytes = msg.getBytes();
       byte[] out = md.digest(bytes);
       System.out.println(out);

   }

}
