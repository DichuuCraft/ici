package com.hadroncfy.ici.mixin;

import com.hadroncfy.ici.util.TextBuilder;
import com.hadroncfy.ici.util.TokenConsumer;
import com.hadroncfy.ici.util.Tokenizer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerPlayNetworkHandler {
    @Shadow
    public ServerPlayerEntity player;

    @Redirect(method = "onChatMessage", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/server/PlayerManager;broadcastChatMessage(Lnet/minecraft/text/Text;Z)V"
    ))
    private void onSendChat(PlayerManager cela, Text text, boolean system){
        if (text instanceof TranslatableText){
            final TranslatableText txt = (TranslatableText) text;
            String msg = (String) txt.getArgs()[1];

            TextBuilder builder = new TextBuilder(player);
            new Tokenizer(builder).scan(msg);
            txt.getArgs()[1] = builder.getResult();
            player.getServer().getPlayerManager().broadcastChatMessage(txt, false);
        }
    }
}