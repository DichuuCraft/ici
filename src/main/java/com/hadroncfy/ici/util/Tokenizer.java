package com.hadroncfy.ici.util;

public class Tokenizer {
    private final TokenConsumer consumer;
    private StringBuilder sb;

    private String input;
    private int ptr;

    public Tokenizer(TokenConsumer c){
        consumer = c;
    }

    private static boolean isNameStart(int c){
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'A' || c == '_' || c == '$';
    }

    private static boolean isNamePart(int c){
        return isNameStart(c) || c >= '0' && c <= '9';
    }

    private boolean eof(){
        return ptr >= input.length();
    }

    private int next(){
        ptr++;
        return peek();
    }

    private int peek(){
        return eof() ? -1 : input.charAt(ptr);
    }

    private void emitString(String s){
        if (sb == null){
            sb = new StringBuilder();
        }
        sb.append(s);
    }

    private void emitString(char s){
        if (sb == null){
            sb = new StringBuilder();
        }
        sb.append(s);
    }

    private void emitToken(String s){
        if (sb != null){
            consumer.onString(sb.toString());
            sb = null;
        }
        consumer.onToken(s);
    }

    public void scan(String s){
        input = s;

        while (true){
            int c = peek();
            if (c == -1){
                break;
            }

            if (c == '!'){
                if ((c = next()) == '!'){
                    if (isNameStart(c = next())){
                        StringBuilder sb2 = new StringBuilder().append((char)c);
                        while (isNamePart(c = next())){
                            sb2.append((char)c);
                        }
                        emitToken(sb2.toString());
                    }
                    else if (c == '!'){
                        next();
                        emitString("!!");
                    }
                    else {
                        emitString("!!");
                    }
                }
                else {
                    emitString("!");
                }
            }
            else {
                emitString((char)c);
                next();
            }
        }

        if (sb != null){
            consumer.onString(sb.toString());
        }
    }
}