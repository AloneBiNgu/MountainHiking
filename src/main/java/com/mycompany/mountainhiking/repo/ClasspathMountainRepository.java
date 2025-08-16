package com.mycompany.mountainhiking.repo;

import com.mycompany.mountainhiking.models.Mountain;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * - Đọc CSV từ "resources/data/MountainList.csv"
 * - Thiếu file => nạp danh sách mặc định.
 */
public class ClasspathMountainRepository implements MountainRepository {
    private final Map<String, Mountain> map = new LinkedHashMap<>();

    public ClasspathMountainRepository() {
        try (InputStream is = getClass().getResourceAsStream("/data/MountainList.csv")) {
            if (is == null) { putDefaults(); return; }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line; int ok = 0;
                while ((line = br.readLine()) != null) {
                    line = line.trim(); if (line.isEmpty()) continue;
                    String[] parts = splitCsv(line); if (parts.length < 2) continue;
                    String code = parts[0].trim().toUpperCase(); if (code.equals("CODE")) continue;
                    String name = parts[1].trim();
                    String loc  = parts.length >= 3 ? parts[2].trim() : "";
                    if (!code.isEmpty() && !name.isEmpty()) {
                        map.put(code, new Mountain(code, name, loc)); ok++;
                    }
                }
                if (ok == 0) putDefaults();
            }
        } catch (Exception e) {
            putDefaults();
        }
    }

    @Override public Map<String, Mountain> all() { return map; }
    @Override public Mountain get(String code) { return map.get(code); }

    // Danh sách mặc định để nếu thiếu CSV
    private void putDefaults() {
        map.clear();
        map.put("M001", new Mountain("M001","Fansipan","Lao Cai"));
        map.put("M002", new Mountain("M002","Bach Moc Luong Tu","Lai Chau"));
        map.put("M003", new Mountain("M003","Ta Xua","Son La"));
        map.put("M004", new Mountain("M004","LangBiang","Lam Dong"));
        map.put("M005", new Mountain("M005","Chua Chan","Dong Nai"));
    }

    private static String[] splitCsv(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQ = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') { inQ = !inQ; continue; }
            if (c == ',' && !inQ) { out.add(cur.toString()); cur.setLength(0); continue; }
            cur.append(c);
        }
        out.add(cur.toString());
        return out.toArray(String[]::new);
    }
}
