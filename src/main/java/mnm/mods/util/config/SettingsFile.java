package mnm.mods.util.config;

import com.google.common.io.Files;
import com.google.gson.Gson;
import mnm.mods.tabbychat.Reference;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Used for creating settings and saving/loading them in the JSON format. Start
 * by creating fields. Mark anything you don't wish to be serialized with {@code transient}.
 * If your setting requires special handling for serialization, override
 *  and use it to customize the {@link Gson}
 * object to your liking.
 *
 * @author Matthew Messinger
 */
public abstract class SettingsFile extends ValueObject {

    private transient final String path;
    private transient File file;
    private Gson gson = new Gson();

    public SettingsFile(String path, String name) {
        this.path = String.format("%s/%s.json", path, name);
        try {
            chackFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void chackFile() throws IOException {
        file = new File(Minecraft.getMinecraft().gameDir, path);
        if (!file.exists()) Files.createParentDirs(file);
    }

    public File getFile() {
        return file;
    }

    public abstract void loadConfig();

    public abstract void saveConfig();

    public <T> void saveConfig(T setting, String filename) {
        String generalString = gson.toJson(setting);
        File fileGeneral = new File(Reference.MOD_ID + "/config/"+ filename +".json");
        try {
            FileUtils.writeStringToFile(fileGeneral, generalString, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T loadConfig(T setting, String filename, Class<T> classOfT) {
        File fileGeneral = new File(Reference.MOD_ID + "/config/" + filename + ".json");
        if (!fileGeneral.exists()) saveConfig(setting, filename);
        try {
            return gson.fromJson(FileUtils.readFileToString(fileGeneral, "UTF-8"), classOfT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
