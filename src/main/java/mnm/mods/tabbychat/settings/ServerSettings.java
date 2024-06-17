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
        Gson gson = new Gson();

        File fileGeneral = new File(Reference.MOD_ID + "/config/generalserversettings.json");
        File fileFilters = new File(Reference.MOD_ID + "/config/filters.json");
        File fileChannels = new File(Reference.MOD_ID + "/config/channels.json");

        if (!fileGeneral.exists() || !fileFilters.exists() || !fileChannels.exists()) saveConfig();

        try {
            general = gson.fromJson(FileUtils.readFileToString(fileGeneral, "UTF-8"), GeneralServerSettings.class);
            filters = gson.fromJson(FileUtils.readFileToString(fileFilters, "UTF-8"), ValueList.class);
            channels = gson.fromJson(FileUtils.readFileToString(fileChannels, "UTF-8"), ValueMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveConfig() {
        Gson gson = new Gson();

        String generalString = gson.toJson(general);
        String filtersString = gson.toJson(filters);
        String channelsString = gson.toJson(channels);

        File fileGeneral = new File(Reference.MOD_ID + "/config/generalserversettings.json");
        File fileFilters = new File(Reference.MOD_ID + "/config/filters.json");
        File fileChannels = new File(Reference.MOD_ID + "/config/channels.json");

        try {
            if (!fileGeneral.exists()) Files.createParentDirs(fileGeneral);
            if (!fileFilters.exists()) Files.createParentDirs(fileFilters);
            if (!fileChannels.exists()) Files.createParentDirs(fileChannels);
            FileUtils.writeStringToFile(fileGeneral, generalString, "UTF-8");
            FileUtils.writeStringToFile(fileFilters, filtersString, "UTF-8");
            FileUtils.writeStringToFile(fileChannels, channelsString, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SocketAddress getIP() {
        return this.ip;
    }

}
