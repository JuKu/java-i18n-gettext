package com.jukusoft.i18n;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PotEntry {

    private final String domainName;
    private final String msgId;
    private final String pluralMsgId;
    private final Set<String> filesLinesList = new HashSet<>();

    public PotEntry (String domainName, String msgId, String pluralMsgId) {
        this.domainName = domainName;
        this.msgId = msgId;
        this.pluralMsgId = pluralMsgId;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getMsgId() {
        return msgId;
    }

    public String getPluralMsgId() {
        return pluralMsgId;
    }

    public boolean hasPlural () {
        return !getPluralMsgId().isEmpty();
    }

    public void addFile (String file, int line) {
        this.filesLinesList.add(file + " line " + line);
    }

    public Set<String> listOccurrences () {
        return this.filesLinesList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PotEntry potEntry = (PotEntry) o;
        return Objects.equals(domainName, potEntry.domainName) &&
                Objects.equals(msgId, potEntry.msgId) &&
                Objects.equals(pluralMsgId, potEntry.pluralMsgId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domainName, msgId, pluralMsgId);
    }

}
