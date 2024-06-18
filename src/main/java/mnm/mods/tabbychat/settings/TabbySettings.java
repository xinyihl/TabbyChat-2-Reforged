package mnm.mods.tabbychat.settings;

import com.google.gson.reflect.TypeToken;
import mnm.mods.tabbychat.Reference;
import mnm.mods.util.config.SettingsFile;

import java.io.File;

public class TabbySettings extends SettingsFile {

    public GeneralSettings general = new GeneralSettings();
    public AdvancedSettings advanced = new AdvancedSettings();
    private final File generalFile = new File(Reference.MOD_ID + "/config/generalsettings.json");
    private final File advancedFile = new File(Reference.MOD_ID + "/config/advancedsettings.json");

    public TabbySettings() {
        super(Reference.MOD_ID, "tabbychat");
        if (!generalFile.exists() || !advancedFile.exists()) saveConfig();
    }

    @Override
    public void loadConfig() {
        general = loadFromJson(generalFile, (new TypeToken<GeneralSettings>(){}.getType()));
        advanced = loadFromJson(advancedFile, (new TypeToken<AdvancedSettings>(){}.getType()));
    }

    @Override
    public void saveConfig() {
        saveToJson(generalFile, general);
        saveToJson(advancedFile, advanced);
    }
}
