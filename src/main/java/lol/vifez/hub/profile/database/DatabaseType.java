package lol.vifez.hub.profile.database;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lol.vifez.hub.profile.ProfileHandler;
import lol.vifez.hub.profile.impl.FlatFileProfileHandler;
import lol.vifez.hub.util.Provider;

@Getter
@RequiredArgsConstructor
public enum DatabaseType {

    FLAT_FILE(FlatFileProfileHandler::new);

    private final Provider<ProfileHandler> profileHandler;

    public ProfileHandler provideProfileHandler() {
        return profileHandler.provide();
    }

}