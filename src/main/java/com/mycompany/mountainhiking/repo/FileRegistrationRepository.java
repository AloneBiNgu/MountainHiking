package com.mycompany.mountainhiking.repo;

import com.mycompany.mountainhiking.models.Registration;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collection;
import java.nio.file.Files;

public class FileRegistrationRepository implements RegistrationRepository, Serializable {

    private final File saveFile = new File("data" + File.separator + "registrations.dat");
    private Map<String, Registration> map = new LinkedHashMap<>(); // key = studentId

    private void ensureDataDir() {
        File dir = saveFile.getParentFile();
        if (dir != null && !dir.exists()) dir.mkdirs();
    }
    
    @Override public boolean deleteStorageFile() throws IOException {
        map.clear();  // Dọn ram

        if (!saveFile.exists()) return true;

        try {
            return Files.deleteIfExists(saveFile.toPath());
        } catch (IOException e) {
            throw new IOException("Failed to delete: " + saveFile.getAbsolutePath(), e);
        }
    }

    @Override public boolean exists(String id) { return map.containsKey(id); }
    @Override public Registration get(String id) { return map.get(id); }
    @Override public void put(Registration r) { map.put(r.student.getId(), r); }
    @Override public Registration remove(String id) { return map.remove(id); }
    @Override public Collection<Registration> all() { return map.values(); }
    @Override public void load() throws IOException, ClassNotFoundException {
        if (!saveFile.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
            FileRegistrationRepository other = (FileRegistrationRepository) ois.readObject();
            this.map = other.map != null ? other.map : new LinkedHashMap<>();
        }
    }
    @Override public void save() throws IOException {
        ensureDataDir();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
            oos.writeObject(this);
        }
    }
}
