package com.jukusoft.i18n;

/**
* test class which is used to check, if gettext-maven-plugin finds language string literals
*/
public class TestClass {

    public void test () {
        I.tr("my-string");
        I.tr("duplicate-string");
        I.tr("duplicate-string");

        System.out.println(I.tr("my-domain", "my-string"));

        String str = I.ntr("sg1", "pl1", 2);
        String str1 = I.ntr("my-test-domain", "sg2", "pl2", 2);
    }

}
