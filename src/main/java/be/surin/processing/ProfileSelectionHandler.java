package be.surin.processing;

import be.surin.engine.Profile;

public class ProfileSelectionHandler {

    // TODO Remove this when the save/load are implemented
    public static Profile[] createTestProfiles() {
        return new Profile[] {new Profile("Rayghun"), new Profile("Return print(v)")};
    }

    public static Profile[] getProfiles() {
        return createTestProfiles();
    }
}
