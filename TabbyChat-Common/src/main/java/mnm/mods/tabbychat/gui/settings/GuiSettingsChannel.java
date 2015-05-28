package mnm.mods.tabbychat.gui.settings;

import mnm.mods.tabbychat.TabbyChat;
import mnm.mods.tabbychat.api.Channel;
import mnm.mods.tabbychat.settings.ChannelSettings;
import mnm.mods.tabbychat.util.Translation;
import mnm.mods.util.Color;
import mnm.mods.util.gui.BorderLayout;
import mnm.mods.util.gui.GuiButton;
import mnm.mods.util.gui.GuiCheckbox;
import mnm.mods.util.gui.GuiComponent;
import mnm.mods.util.gui.GuiGridLayout;
import mnm.mods.util.gui.GuiLabel;
import mnm.mods.util.gui.GuiPanel;
import mnm.mods.util.gui.GuiText;
import mnm.mods.util.gui.SettingPanel;
import mnm.mods.util.gui.VerticalLayout;
import mnm.mods.util.gui.events.ActionPerformed;
import mnm.mods.util.gui.events.GuiEvent;
import net.minecraft.client.resources.I18n;

public class GuiSettingsChannel extends SettingPanel<ChannelSettings> {

    private Channel channel;

    private GuiPanel channels;
    private GuiPanel panel;

    private GuiText alias;
    private GuiText prefix;
    private GuiCheckbox hidePrefix;

    public GuiSettingsChannel() {
        this.setLayout(new BorderLayout());
        this.setDisplayString(Translation.CHANNEL_TITLE.toString());
        this.setBackColor(Color.getColor(0, 15, 100, 65));

        channels = new GuiPanel();
        channels.setLayout(new VerticalLayout());
        for (Channel channel : getSettings().channels.getValue().values()) {
            channels.addComponent(new ChannelButton(channel));
        }
        this.addComponent(channels, BorderLayout.Position.WEST);
        panel = new GuiPanel();
        panel.setLayout(new GuiGridLayout(8, 15));
        this.addComponent(panel, BorderLayout.Position.CENTER);

        this.select(null);
    }

    public GuiSettingsChannel(Channel channel) {
        this();
        this.select(channel);
    }

    private void select(Channel channel) {

        for (GuiComponent comp : channels) {
            if (((ChannelButton) comp).channel == channel) {
                comp.setEnabled(false);
            } else {
                comp.setEnabled(true);
            }
        }

        this.channel = channel;
        this.panel.clearComponents();
        if (channel == null) {
            if (channels.getComponentCount() > 0) {
                this.panel.addComponent(new GuiLabel(Translation.CHANNEL_SELECT.toString()),
                        new int[] { 1, 1 });
            } else {
                this.panel.addComponent(new GuiLabel(Translation.CHANNEL_NONE.toString()),
                        new int[] { 1, 1 });
            }
            return;
        }
        this.panel.addComponent(
                new GuiLabel(Translation.CHANNEL_LABEL.translate(channel.getName())),
                new int[] { 1, 1 });
        this.panel.addComponent(new GuiLabel(Translation.CHANNEL_ALIAS.toString()),
                new int[] { 1, 4 });
        this.panel.addComponent(alias = new GuiText(), new int[] { 3, 4, 4, 1 });
        alias.setValue(channel.getAlias());
        this.panel.addComponent(new GuiLabel(Translation.CHANNEL_PREFIX.toString()), new int[] { 1, 6 });
        this.panel.addComponent(prefix = new GuiText(), new int[] { 3, 6, 4, 1 });
        prefix.setValue(channel.getPrefix());
        this.panel.addComponent(hidePrefix = new GuiCheckbox(), new int[] { 1, 8 });
        hidePrefix.setValue(channel.isPrefixHidden());
        this.panel.addComponent(new GuiLabel(Translation.CHANNEL_HIDE_PREFIX.toString()),
                new int[] { 2, 8 });

        GuiButton accept = new GuiButton(I18n.format("gui.done"));
        accept.addActionListener(new ActionPerformed() {
            @Override
            public void action(GuiEvent event) {
                save();
            }
        });
        this.panel.addComponent(accept, new int[] { 1, 10, 3, 2 });

        GuiButton forget = new GuiButton(Translation.CHANNEL_FORGET.toString());
        forget.addActionListener(new ActionPerformed() {
            @Override
            public void action(GuiEvent event) {
                getSettings().channels.getValue().remove(GuiSettingsChannel.this.channel.getName());
                for (GuiComponent comp : channels) {
                    if (comp instanceof ChannelButton
                            && ((ChannelButton) comp).channel == GuiSettingsChannel.this.channel) {
                        channels.removeComponent(comp);
                        break;
                    }

                }
                select(null);
            }
        });
        this.panel.addComponent(forget, new int[] { 4, 10, 3, 2 });
    }

    private void save() {
        channel.setAlias(alias.getValue());
        channel.setPrefix(prefix.getValue());
        channel.setPrefixHidden(hidePrefix.getValue());
        getSettings().saveSettingsFile();
    }

    @Override
    public ChannelSettings getSettings() {
        return TabbyChat.getInstance().channelSettings;
    }

    public class ChannelButton extends GuiButton implements ActionPerformed {

        private Channel channel;

        public ChannelButton(Channel channel) {
            super(channel.getName());
            this.channel = channel;
            setSize(60, 15);
        }

        @Override
        public void action(GuiEvent event) {
            select(channel);
        }
    }
}
