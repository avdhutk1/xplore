package com.audhut.spring5ex.impl;

import java.security.MessageDigest;

/**
 * Created by avdhut on 30/1/19.
 */
public class MessageDigestFactory {

    private String algorithmName = "MD5";

    public MessageDigest getInstance() throws Exception {
        return MessageDigest.getInstance(algorithmName);
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }


}
