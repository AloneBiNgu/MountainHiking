package com.mycompany.mountainhiking.repo;

import com.mycompany.mountainhiking.models.Mountain;
import java.util.Map;

public interface MountainRepository {
    Map<String, Mountain> all();
    Mountain get(String code);
}
