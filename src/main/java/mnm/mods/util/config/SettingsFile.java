package mnm.mods.util.config;

import com.google.common.io.Files;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

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
    private static final Gson gson = new Gson();

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

    protected <T> void saveToJson(File file, T object) {
        try (Writer writer = new OutputStreamWriter(java.nio.file.Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected <T> T loadFromJson(File file, Type typeOfT) {
        try (InputStreamReader reader = new InputStreamReader(java.nio.file.Files.newInputStream(file.toPath()), StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, typeOfT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
