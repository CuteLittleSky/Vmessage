package me.feusalamander.vmessage;

import com.moandjiezana.toml.Toml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class Configuration {
    private String messageFormat;
    private String joinFormat;
    private String leaveFormat;
    private String changeFormat;
    private boolean messageEnabled;
    private boolean joinEnabled;
    private boolean leaveEnabled;
    private boolean changeEnabled;
    private final Toml config;

    Configuration(Toml config) {
        messageFormat = config.getString("Message.format", "");
        joinFormat = config.getString("Join.format", "");
        leaveFormat = config.getString("Leave.format", "");
        changeFormat = config.getString("Server-change.format", "");

        messageEnabled = config.getBoolean("Message.enabled", false);
        joinEnabled = config.getBoolean("Join.enabled", false);
        leaveEnabled = config.getBoolean("Leave.enabled", false);
        changeEnabled = config.getBoolean("Server-change.enabled", false);
        this.config = config;

    }

    static Configuration load(Path dataDirectory) {
        Path f = createConfig(dataDirectory);
        if (f != null) {
            Toml config = new Toml().read(f.toFile());
            return new Configuration(config);
        }
        return null;
    }

    private static Path createConfig(Path dataDirectory){
        try {
            if (Files.notExists(dataDirectory)){
                Files.createDirectory(dataDirectory);
            }
            Path f = dataDirectory.resolve("config.toml");
            if (Files.notExists(f)){
                try (InputStream stream = Configuration.class.getResourceAsStream("/config.toml")) {
                    Files.copy(Objects.requireNonNull(stream), f);
                }
            }
            return f;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMessageFormat() {
        return this.messageFormat;
    }

    public String getJoinFormat() {
        return this.joinFormat;
    }

    public String getLeaveFormat() {
        return this.leaveFormat;
    }

    public String getChangeFormat() {
        return this.changeFormat;
    }

    public boolean isMessageEnabled() {
        return this.messageEnabled;
    }

    public boolean isJoinEnabled() {
        return this.joinEnabled;
    }

    public boolean isLeaveEnabled() {
        return this.leaveEnabled;
    }

    public boolean isChangeEnabled() {
        return this.changeEnabled;
    }
    void reload(){
        messageFormat = config.getString("Message.format", "");
        joinFormat = config.getString("Join.format", "");
        leaveFormat = config.getString("Leave.format", "");
        changeFormat = config.getString("Server-change.format", "");

        messageEnabled = config.getBoolean("Message.enabled", false);
        joinEnabled = config.getBoolean("Join.enabled", false);
        leaveEnabled = config.getBoolean("Leave.enabled", false);
        changeEnabled = config.getBoolean("Server-change.enabled", false);
    }
}
