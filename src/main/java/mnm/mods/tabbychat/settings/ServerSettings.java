package mnm.mods.tabbychat.settings;

import com.google.gson.reflect.TypeToken;
import mnm.mods.tabbychat.ChatChannel;
import mnm.mods.tabbychat.Reference;
import mnm.mods.tabbychat.extra.filters.UserFilter;
import mnm.mods.util.IPUtils;
import mnm.mods.util.config.SettingsFile;
import mnm.mods.util.config.ValueList;
import mnm.mods.util.config.ValueMap;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;


public class ServerSettings extends SettingsFile {

    public GeneralServerSettings general = new GeneralServerSettings();
    public ValueList<UserFilter> filters = list();
    public ValueMap<ChatChannel> channels = map();
    public ValueMap<ChatChannel> pms = map();
    private final File generalFile = new File(Reference.MOD_ID + "/config/generalserversettings.json");
    private final File filtersFile = new File(Reference.MOD_ID + "/config/filters.json");
    private final File channelsFile = new File(Reference.MOD_ID + "/config/channels.json");

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
        if (!generalFile.exists() && !filtersFile.exists() && !channelsFile.exists()) saveConfig();
        general = loadFromJson(generalFile, (new TypeToken<GeneralServerSettings>(){}.getType()));
        filters = loadFromJson(filtersFile, (new TypeToken<ValueList<UserFilter>>(){}.getType()));
        channels = loadFromJson(channelsFile, (new TypeToken<ValueMap<ChatChannel>>(){}.getType()));
    }

    @Override
    public void saveConfig() {
        saveToJson(generalFile, general);
        saveToJson(filtersFile, filters);
        saveToJson(channelsFile, channels);
    }

    public SocketAddress getIP() {
        return this.ip;
    }

}
