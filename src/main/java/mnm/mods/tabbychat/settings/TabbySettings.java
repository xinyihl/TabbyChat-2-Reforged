package mnm.mods.tabbychat.settings;

import mnm.mods.tabbychat.Reference;
import mnm.mods.util.config.SettingsFile;

public class TabbySettings extends SettingsFile {

    public GeneralSettings general = new GeneralSettings();
    public AdvancedSettings advanced = new AdvancedSettings();

    public TabbySettings() {
        super(Reference.MOD_ID, "tabbychat");
    }
/*
    @Override
    public void setupGsonSerialiser(GsonBuilder gsonBuilder) {
        super.setupGsonSerialiser(gsonBuilder);
        gsonBuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
    }*/
}
