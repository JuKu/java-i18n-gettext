package com.jukusoft.i18n;

public class ParserEntry {

    private final String domainName;
    private final String msgId;

    public ParserEntry (String domainName, String msgId) {
        this.domainName = domainName;
        this.msgId = msgId;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getMsgId() {
        return msgId;
    }

}
