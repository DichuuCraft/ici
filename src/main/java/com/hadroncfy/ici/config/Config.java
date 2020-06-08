package com.hadroncfy.ici.config;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.LowercaseEnumTypeAdapterFactory;

public class Config {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
        .registerTypeHierarchyAdapter(Text.class, new Text.Serializer())
        .registerTypeHierarchyAdapter(Style.class, new Style.Serializer())
        .registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory()).create();

    public Text ici = new LiteralText("[x:$1, y:$2, z:$3, dim:$4]");
    public Set<String> names = new HashSet<>();

    public Config(){
        names.add("here");
        names.add("ici");
    }
}