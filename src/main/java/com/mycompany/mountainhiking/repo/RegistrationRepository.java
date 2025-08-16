package com.mycompany.mountainhiking.repo;

import com.mycompany.mountainhiking.models.Registration;
import java.io.IOException;
import java.util.Collection;

public interface RegistrationRepository {
    boolean deleteStorageFile() throws IOException;
    boolean exists(String id);
    Registration get(String id);
    void put(Registration registration);
    Registration remove(String id);
    Collection<Registration> all();
    void load() throws IOException, ClassNotFoundException;
    void save() throws IOException;
}
