package mnm.mods.tabbychat.settings;

import com.google.common.io.Files;
import com.google.gson.Gson;
import mnm.mods.tabbychat.Reference;
import mnm.mods.util.config.SettingsFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class TabbySettings extends SettingsFile {

    public GeneralSettings general = new GeneralSettings();
    public AdvancedSettings advanced = new AdvancedSettings();

    public TabbySettings() {
        super(Reference.MOD_ID, "tabbychat");
    }

    @Override
    public void loadConfig() {
        general = super.loadConfig(general,"generalsettings", GeneralSettings.class);
        advanced = super.loadConfig(advanced,"advancedsettings", AdvancedSettings.class);
    }

    @Override
    public void saveConfig() {
        super.saveConfig(general,"generalsettings");
        super.saveConfig(advanced,"advancedsettings");
    }
}
