package mnm.mods.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

/**
 * Used for creating settings and saving/loading them in the JSON format. Start
 * by creating fields. Mark anything you don't wish to be serialized with {@code transient}.
 * If your setting requires special handling for serialization, override
 * {@link #setupGsonSerialiser(GsonBuilder)} and use it to customize the {@link Gson}
 * object to your liking.
 *
 * @author Matthew Messinger
 */
public abstract class SettingsFile extends ValueObject {

    private transient final String path;
    private transient File file;

    public SettingsFile(String path, String name) {
        this.path = String.format("%s/%s.json", path, name);
    }



    public File getFile() {
        return file;
    }
}
