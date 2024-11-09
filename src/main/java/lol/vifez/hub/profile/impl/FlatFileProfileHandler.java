package lol.vifez.hub.profile.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lol.vifez.hub.profile.HubProfile;
import lol.vifez.hub.profile.ProfileHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

public class FlatFileProfileHandler extends ProfileHandler {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private final File directory;

    public FlatFileProfileHandler() {
        this.directory = new File(plugin.getDataFolder(), "profiles");

        if (!directory.exists() && directory.mkdirs())
            plugin.getLogger().info("Created profiles directory.");
    }

    @Override
    public HubProfile loadProfile(UUID uuid) {
        File file = new File(directory, uuid + ".json");

        if (!file.exists()) {
            HubProfile profile = new HubProfile(uuid);
            save(profile);

            profileMap.put(uuid, profile);
            return profile;
        }

        try (Reader reader = new InputStreamReader(
                Files.newInputStream(file.toPath()),
                StandardCharsets.UTF_8)) {
            HubProfile profile = GSON.fromJson(reader, HubProfile.class);
            profileMap.put(uuid, profile);

            return profile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(HubProfile profile) {
        File file = new File(directory, profile.getUuid() + ".json");

        try (Writer writer = new FileWriter(file)) {
            GSON.toJson(profile, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}