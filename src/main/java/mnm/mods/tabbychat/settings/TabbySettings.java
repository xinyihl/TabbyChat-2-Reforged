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
        Gson gson = new Gson();

        File fileGeneral = new File(Reference.MOD_ID + "/config/generalsettings.json");
        File fileAdvanced = new File(Reference.MOD_ID + "/config/advancedsettings.json");

        if (!fileGeneral.exists() || !fileAdvanced.exists()) saveConfig();

        try {
            general = gson.fromJson(FileUtils.readFileToString(fileGeneral, "UTF-8"), GeneralSettings.class);
            advanced = gson.fromJson(FileUtils.readFileToString(fileAdvanced, "UTF-8"), AdvancedSettings.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveConfig() {
        Gson gson = new Gson();

        String generalString = gson.toJson(general);
        String advancedString = gson.toJson(advanced);

        File fileGeneral = new File(Reference.MOD_ID + "/config/generalsettings.json");
        File fileAdvanced = new File(Reference.MOD_ID + "/config/advancedsettings.json");

        try {
            if (!fileGeneral.exists()) Files.createParentDirs(fileGeneral);
            if (!fileAdvanced.exists()) Files.createParentDirs(fileAdvanced);
            FileUtils.writeStringToFile(fileGeneral, generalString, "UTF-8");
            FileUtils.writeStringToFile(fileAdvanced, advancedString, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
