package com.jukusoft.i18n.loader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DomainBundle {

    protected Map<String,String> cache = new ConcurrentHashMap<>();

    //save last access, so memory manager can unload this bundle, if it isn't used anymore (for a while)
    protected long lastAccess = 0;
    protected int useCounter = 0;

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

        //save current (last access) timestamp
        this.lastAccess = System.currentTimeMillis();

        //increment counter (to check, if bundle was used in-game - for optimization issues)
        this.useCounter++;

        return translation;
    }

    public long getLastAccessTimestamp () {
        return this.lastAccess;
    }

    public int getUseCounter() {
        return this.useCounter;
    }

}
