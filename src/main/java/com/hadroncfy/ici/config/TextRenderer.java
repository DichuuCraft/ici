package com.hadroncfy.ici.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class TextRenderer extends AbstractTextRenderer<TextRenderer> implements Function<String, String> {
    private List<String> vars = new ArrayList<>();
    private static final Pattern VAL_EXP = Pattern.compile("\\$[0-9]");
    
    public TextRenderer var(String ...vars){
        for (String v: vars){
            this.vars.add(v);
        }
        return this;
    }

    public Text render0(Text t){
        return render(this, t);
    }

    @Override
    protected Text renderString(String s) {
        return new LiteralText(replaceAll(VAL_EXP, s, this));
    }

    @Override
    public String apply(String a) {
        try {
            int i = Integer.parseInt(a.substring(1));
            if (i > 0 && i <= vars.size()){
                return vars.get(i - 1);
            }
        }
        catch(NumberFormatException e){}
        return a;
    }

    public static Text render(Text template, String ...vars){
        return new TextRenderer().var(vars).render0(template);
    }

    private static String replaceAll(Pattern pattern, String s, Function<String, String> func){
        StringBuilder sb = new StringBuilder();
        int lastIndex = 0;
        Matcher m = pattern.matcher(s);
        while (m.find()){
            if (lastIndex != m.start()){
                sb.append(s.substring(lastIndex, m.start()));
            }
            String name = m.group();
            lastIndex = m.start() + name.length();
            sb.append(func.apply(name));
        }
        if (lastIndex < s.length()){
            sb.append(s.substring(lastIndex));
        }
        return sb.toString();
    }
}