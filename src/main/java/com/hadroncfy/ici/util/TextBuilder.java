package com.hadroncfy.ici.util;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import static com.hadroncfy.ici.Mod.getConfig;

import com.hadroncfy.ici.config.TextRenderer;

public class TextBuilder implements TokenConsumer {
    private Text txt = null;
    private final ServerPlayerEntity player;

    public TextBuilder(ServerPlayerEntity player){
        this.player = player;
    }

    public Text getResult(){
        return txt;
    }

    private void emitRaw(Text t){
        if (txt == null){
            txt = t;
        }
        else {
            txt.append(t);
        }
    }

    private static String f2s(double d){
        return Long.toString(Math.round(d));
    }

    @Override
    public void onString(String s) {
        emitRaw(new LiteralText(s));
    }

    @Override
    public void onToken(String s) {
        if (getConfig().names.contains(s)){
            emitRaw(TextRenderer.render(getConfig().ici, 
                f2s(player.x),
                f2s(player.y),
                f2s(player.z),
                Integer.toString(player.dimension.getRawId())
            ));
        }
        else {
            emitRaw(new LiteralText("!!" + s));
        }
    }
    
}