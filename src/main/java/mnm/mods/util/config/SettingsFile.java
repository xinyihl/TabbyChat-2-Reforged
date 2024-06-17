package mnm.mods.util.config;

import com.google.common.io.Files;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;

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

    public void loadConfig() {
    }

    public void saveConfig() {
    }
}
