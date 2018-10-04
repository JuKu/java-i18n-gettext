package com.jukusoft.i18n;

import java.util.Locale;
import java.util.Objects;

public class CacheKey {

    protected final String domain;
    protected final Locale locale;

    protected CacheKey (String domain, Locale locale) {
        this.domain = domain;
        this.locale = locale;
    }

    public String getDomain() {
        return domain;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheKey cacheKey = (CacheKey) o;
        return Objects.equals(domain, cacheKey.domain) &&
                Objects.equals(locale, cacheKey.locale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domain, locale);
    }

}
