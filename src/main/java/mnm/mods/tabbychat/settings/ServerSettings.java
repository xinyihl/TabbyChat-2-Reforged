package mnm.mods.tabbychat.settings;

import com.google.common.io.Files;
import com.google.gson.Gson;
import mnm.mods.tabbychat.ChatChannel;
import mnm.mods.tabbychat.Reference;
import mnm.mods.tabbychat.extra.filters.UserFilter;
import mnm.mods.util.IPUtils;
import mnm.mods.util.config.SettingsFile;
import mnm.mods.util.config.ValueList;
import mnm.mods.util.config.ValueMap;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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
            ip = "chatdata/singleplayer";
        } else {
            String url = ((InetSocketAddress) addr).getHostName();
            ip = "chatdata/multiplayer/" + IPUtils.parse(url).getFileSafeAddress();
        }
        return ip;
    }

    @Override
    public void loadConfig() {
        general = super.loadConfig(general,"generalserversettings", GeneralServerSettings.class);
        filters = super.loadConfig(filters,"filters", ValueList.class);
        channels = super.loadConfig(channels,"channels", ValueMap.class);
    }

    @Override
    public void saveConfig() {
        super.saveConfig(general,"generalserversettings");
        super.saveConfig(filters,"filters");
        super.saveConfig(channels,"channels");
    }

    public SocketAddress getIP() {
        return this.ip;
    }

}
