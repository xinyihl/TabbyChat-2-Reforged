package mnm.mods.tabbychat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerLoginHandler {
    @SubscribeEvent
    public void onJoin(PlayerEvent.PlayerLoggedInEvent event) {
        TabbyChat.getInstance().onJoin();
    }
}
