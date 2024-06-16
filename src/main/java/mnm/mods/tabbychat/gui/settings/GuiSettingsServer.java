package mnm.mods.tabbychat.gui.settings;

import static mnm.mods.tabbychat.util.Translation.*;

import com.google.common.eventbus.Subscribe;
import mnm.mods.tabbychat.TabbyChat;
import mnm.mods.tabbychat.extra.filters.GuiFilterEditor;
import mnm.mods.tabbychat.extra.filters.UserFilter;
import mnm.mods.tabbychat.settings.GeneralServerSettings;
import mnm.mods.tabbychat.settings.ServerSettings;
import mnm.mods.tabbychat.util.ChannelPatterns;
import mnm.mods.tabbychat.util.MessagePatterns;
import mnm.mods.util.Color;
import mnm.mods.util.gui.GuiButton;
import mnm.mods.util.gui.GuiGridLayout;
import mnm.mods.util.gui.GuiLabel;
import mnm.mods.util.gui.config.GuiSettingBoolean;
import mnm.mods.util.gui.config.GuiSettingEnum;
import mnm.mods.util.gui.config.GuiSettingString;
import mnm.mods.util.gui.config.GuiSettingStringList;
import mnm.mods.util.gui.config.SettingPanel;
import mnm.mods.util.gui.events.ActionPerformedEvent;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiSettingsServer extends SettingPanel<ServerSettings> {

    private int index = 0;

    private GuiButton prev;
    private GuiButton edit;
    private GuiButton next;
    private GuiButton delete;
    private GuiLabel lblFilter;
    private GuiLabel lblPattern;

    GuiSettingsServer() {
        this.setLayout(new GuiGridLayout(10, 20));
        this.setDisplayString(I18n.format(SETTINGS_SERVER));
        this.setSecondaryColor(Color.of(255, 215, 0, 64));
    }

    @Override
    public void initGUI() {
        GeneralServerSettings sett = getSettings().general;
        index = getSettings().filters.get().size() - 1;

        int pos = 1;
        this.addComponent(new GuiLabel(new TextComponentTranslation(CHANNELS_ENABLED)), new int[]{2, pos});
        GuiSettingBoolean chkChannels = new GuiSettingBoolean(sett.channelsEnabled);
        chkChannels.setCaption(new TextComponentTranslation(CHANNELS_ENABLED_DESC));
        this.addComponent(chkChannels, new int[]{1, pos});

        pos += 1;
        this.addComponent(new GuiLabel(new TextComponentTranslation(PM_ENABLED)), new int[]{2, pos});
        GuiSettingBoolean chkPM = new GuiSettingBoolean(sett.pmEnabled);
        chkPM.setCaption(new TextComponentTranslation(PM_ENABLED_DESC));
        this.addComponent(chkPM, new int[]{1, pos});

        pos += 1;
        addComponent(new GuiLabel(new TextComponentTranslation(USE_DEFAULT)), new int[]{2, pos});
        addComponent(new GuiSettingBoolean(sett.useDefaultTab), new int[]{1, pos});

        pos += 2;
        this.addComponent(new GuiLabel(new TextComponentTranslation(CHANNEL_PATTERN)), new int[]{1, pos});
        GuiSettingEnum<ChannelPatterns> enmChanPat = new GuiSettingEnum<>(sett.channelPattern,
                ChannelPatterns.values());
        enmChanPat.setCaption(new TextComponentTranslation(CHANNEL_PATTERN_DESC));
        this.addComponent(enmChanPat, new int[]{5, pos, 4, 1});

        pos += 2;
        this.addComponent(new GuiLabel(new TextComponentTranslation(MESSAGE_PATTERN)), new int[]{1, pos});
        if (sett.messegePattern.get() == null) {
            sett.messegePattern.set(MessagePatterns.WHISPERS);
        }
        GuiSettingEnum<MessagePatterns> enmMsg = new GuiSettingEnum<>(sett.messegePattern, MessagePatterns.values());
        enmMsg.setCaption(new TextComponentTranslation(MESSAGE_PATTERN_DESC));
        this.addComponent(enmMsg, new int[]{5, pos, 4, 1});

        pos += 2;
        this.addComponent(new GuiLabel(new TextComponentTranslation(IGNORED_CHANNELS)), new int[]{0, pos});
        GuiSettingStringList strIgnored = new GuiSettingStringList(sett.ignoredChannels);
        strIgnored.setCaption(new TextComponentTranslation(IGNORED_CHANNELS_DESC));
        this.addComponent(strIgnored, new int[]{5, pos, 5, 1});

        pos += 2;
        this.addComponent(new GuiLabel(new TextComponentTranslation(DEFAULT_CHANNEL_COMMAND)), new int[]{0, pos});
        GuiSettingString strChannels = new GuiSettingString(sett.channelCommand);
        strChannels.setCaption(new TextComponentTranslation(DEFAULT_CHANNEL_COMMAND_DESC));
        this.addComponent(strChannels, new int[]{5, pos, 5, 1});

        pos += 2;
        this.addComponent(new GuiLabel(new TextComponentTranslation(DEFAULT_CHANNEL)), new int[]{0, pos});
        GuiSettingString strMessages = new GuiSettingString(sett.defaultChannel);
        strMessages.setCaption(new TextComponentTranslation(DEFAULT_CHANNEL_DESC));
        this.addComponent(strMessages, new int[]{5, pos, 5, 1});

        // Filters
        pos += 2;
        this.addComponent(new GuiLabel(new TextComponentTranslation(FILTERS)), new int[]{4, pos, 1, 2});

        pos += 2;
        prev = new GuiButton("<");
        prev.getBus().register(new Object() {
            @Subscribe
            public void goBackwards(ActionPerformedEvent event) {
                select(index - 1);
            }
        });
        this.addComponent(prev, new int[]{0, pos, 1, 2});

        edit = new GuiButton(I18n.format("selectServer.edit"));
        edit.getBus().register(new Object() {
            @Subscribe
            public void goEditwords(ActionPerformedEvent event) {
                edit(index);
            }
        });
        this.addComponent(edit, new int[]{1, pos, 2, 2});

        next = new GuiButton(">");
        next.getBus().register(new Object() {
            @Subscribe
            public void goForwards(ActionPerformedEvent event) {
                select(index + 1);
            }
        });
        this.addComponent(next, new int[]{3, pos, 1, 2});

        this.addComponent(lblFilter = new GuiLabel(), new int[]{5, pos+1});
        this.addComponent(lblPattern = new GuiLabel(), new int[]{5, pos + 2});
        GuiButton _new = new GuiButton(I18n.format(FILTERS_NEW));
        _new.getBus().register(new Object() {
            @Subscribe
            public void goAddwords(ActionPerformedEvent event) {
                add();
            }
        });

        pos += 2;
        this.addComponent(_new, new int[]{0, pos, 2, 2});
        delete = new GuiButton(I18n.format("selectServer.delete"));
        delete.getBus().register(new Object() {
            @Subscribe
            public void goDelwords(ActionPerformedEvent event) {
                delete(index);
            }
        });
        this.addComponent(delete, new int[]{2, pos, 2, 2});
        prev.setEnabled(false);
        if (index == -1) {
            delete.setEnabled(false);
            edit.setEnabled(false);
            next.setEnabled(false);
        }

        update();
    }

    @Override
    public ServerSettings getSettings() {
        return TabbyChat.getInstance().serverSettings;
    }

    // Filters

    private void select(int i) {
        this.index = i;
        update();
    }

    private void delete(int i) {
        // deletes a filter
        getSettings().filters.remove(i);
        update();
    }

    private void add() {
        // creates a new filter, adds it to the list, and selects it.
        getSettings().filters.add(new UserFilter());
        select(getSettings().filters.get().size() - 1);
        update();
    }

    private void update() {
        this.next.setEnabled(true);
        this.prev.setEnabled(true);
        this.edit.setEnabled(true);
        this.delete.setEnabled(true);

        int size = getSettings().filters.get().size();

        if (index >= size - 1) {
            this.next.setEnabled(false);
            index = size - 1;
        }
        if (index < 1) {
            this.prev.setEnabled(false);
            index = 0;
        }
        if (size < 1) {
            this.edit.setEnabled(false);
            this.delete.setEnabled(false);
            this.index = 0;
        } else {
            UserFilter filter = getSettings().filters.get(index);
            this.lblFilter.setText(new TextComponentString(filter.getName()));
            this.lblPattern.setText(new TextComponentString(filter.getRawPattern()));
        }
    }

    private void edit(int i) {
        UserFilter filter = getSettings().filters.get(i);
        setOverlay(new GuiFilterEditor(filter, f -> {
            this.lblFilter.setText(new TextComponentString(f.getName()));
            this.lblPattern.setText(new TextComponentString(f.getRawPattern()));
        }));

    }
}
