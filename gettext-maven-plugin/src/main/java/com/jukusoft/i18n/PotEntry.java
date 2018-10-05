package com.jukusoft.i18n;

import java.util.Objects;

public class PotEntry {

    private final String msgId;
    private final String pluralMsgId;

    public PotEntry (String msgId, String pluralMsgId) {
        this.msgId = msgId;
        this.pluralMsgId = pluralMsgId;
    }

    public String getMsgId() {
        return msgId;
    }

    public String getPluralMsgId() {
        return pluralMsgId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PotEntry potEntry = (PotEntry) o;
        return Objects.equals(msgId, potEntry.msgId) &&
                Objects.equals(pluralMsgId, potEntry.pluralMsgId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msgId, pluralMsgId);
    }

}
