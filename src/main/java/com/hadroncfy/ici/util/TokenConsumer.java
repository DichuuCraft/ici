package com.hadroncfy.ici.util;

public interface TokenConsumer {
    void onString(String s);
    void onToken(String s);
}