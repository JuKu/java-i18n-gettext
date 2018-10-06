package com.jukusoft.i18n.loader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DomainBundle {

    protected Map<String,String> cache = new ConcurrentHashMap<>();

    public DomainBundle () {
        //
    }

    public void addTranslation (String key, String value) {
        this.cache.put(key, value);
    }

    public String tr (String msgId) {
        String translation = cache.get(msgId);

        if (translation == null) {
            return msgId;
        }

        return translation;
    }

}
