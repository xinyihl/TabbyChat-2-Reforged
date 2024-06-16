package mnm.mods.tabbychat.settings;

import mnm.mods.tabbychat.ChatChannel;
import mnm.mods.tabbychat.Reference;
import mnm.mods.tabbychat.extra.filters.UserFilter;
import mnm.mods.util.IPUtils;
import mnm.mods.util.config.SettingsFile;
import mnm.mods.util.config.ValueList;
import mnm.mods.util.config.ValueMap;
import net.minecraft.client.Minecraft;

import java.net.InetSocketAddress;
import java.net.SocketAddress;


public class ServerSettings extends SettingsFile {

    public GeneralServerSettings general = new GeneralServerSettings();
    public ValueList<UserFilter> filters = list();
    public ValueMap<ChatChannel> channels = map();
    public ValueMap<ChatChannel> pms = map();

    private transient final SocketAddress ip;

    public ServerSettings(SocketAddress url) {
        super(Reference.MOD_ID + "/" + getIPForFileName(url), "server");
        this.ip = url;
    }

    private static String getIPForFileName(SocketAddress addr) {
        String ip;
        if (Minecraft.getMinecraft().isSingleplayer()) {
            ip = "singleplayer";
        } else {
            String url = ((InetSocketAddress) addr).getHostName();
            ip = "multiplayer/" + IPUtils.parse(url).getFileSafeAddress();
        }
        return ip;
    }

    public SocketAddress getIP() {
        return this.ip;
    }

}
