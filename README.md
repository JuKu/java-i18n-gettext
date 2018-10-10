# java-i18n-gettext

A small library to use GNU gettext() functionalities in Java.
Inspired by [easy-i18n](https://github.com/awkay/easy-i18n).

[![Build Status](https://travis-ci.org/JuKu/java-i18n-gettext.svg?branch=master)](https://travis-ci.org/JuKu/java-i18n-gettext)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aeasy-i18n-gettext-parent&metric=ncloc)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aeasy-i18n-gettext-parent) 
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aeasy-i18n-gettext-parent&metric=alert_status)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aeasy-i18n-gettext-parent) 
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aeasy-i18n-gettext-parent&metric=coverage)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aeasy-i18n-gettext-parent) 
[![Technical Debt Rating](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aeasy-i18n-gettext-parent&metric=sqale_index)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aeasy-i18n-gettext-parent) 
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aeasy-i18n-gettext-parent&metric=code_smells)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aeasy-i18n-gettext-parent) 
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aeasy-i18n-gettext-parent&metric=bugs)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aeasy-i18n-gettext-parent) 
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aeasy-i18n-gettext-parent&metric=vulnerabilities)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aeasy-i18n-gettext-parent) 
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aeasy-i18n-gettext-parent&metric=security_rating)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aeasy-i18n-gettext-parent) 
[![Maven Central](https://img.shields.io/maven-central/v/com.jukusoft/easy-i18n-gettext.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.jukusoft%22%20AND%20a:%22easy-i18n-gettext%22)

[![Sonarcloud](https://sonarcloud.io/api/project_badges/quality_gate?project=com.jukusoft%3Aeasy-i18n-gettext-parent)](https://sonarcloud.io/dashboard?id=com.jukusoft%3Aeasy-i18n-gettext-parent)

See also:

  - [gettext Tutorial](http://www.labri.fr/perso/fleury/posts/programming/a-quick-gettext-tutorial.html)
  - [MO File Encoding](https://www.gnu.org/software/gettext/manual/html_node/MO-Files.html)

## Maven Coordinates

```xml
<dependency>
  <groupId>com.jukusoft</groupId>
  <artifactId>easy-i18n-gettext</artifactId>
  <version>1.0.3</version>
</dependency>
```

## How To

This library is very smiliar to [awkay/easy-i18n](https://github.com/awkay/easy-i18n), but without date & time formatting and a little bit easier to use.\
Also this library doesn't read .mo files, instead it reads .po files directly.\
\
First, you have to initialize this library in your source code, once, with users prefered language (you can change this language later):
```java
I.init(new File("./path/to/po/files/"), Locale.ENGLISH, "messages");
```

You can change the language everytime with this command:
```java
I.setLanguage("de");

//or use Locale directly:
I.setLanguage(Locale.ENGLISH);
```

Translation:
```java
//translate single string, without specified domain (will used default domain, by default "messages")
I.tr("my string to translate");

//translate string with domain
I.tr("messages", "strings to translate");
I.tr("menu", "Options");//this will read string "Options" from file menu.po

//translate singular / plural
I.ntr("my-domain", "my-singular-msgId", "my-plural-msgId", 1);//this will translate "my-singular-msgId" in domain "my-domain" (my-domain.po)

//this will translate "my-plural-msgId" in domain "my-domain" (my-domain.po)
I.ntr("my-domain", "my-singular-msgId", "my-plural-msgId", 2);
I.ntr("my-domain", "my-singular-msgId", "my-plural-msgId", 3);

//and so on

//this works also without to specify a domain, then default domain will be used:
I.ntr("my-singular-msgId", "my-plural-msgId", 1);
```

**Notice**: You don't have to load domains manually with `I.loadDomain()`, this will be done automatically, if you use this domain and it doesn't exists in memory cache.\
If a translation doesn't exists in a .po file, it will return the given msgId, e.q.:
```java
I.tr("not-existent-string");
```

Also every domain requires it's own file with filename **domain-name.po**.

## HowTo: extract translation strings from code

I have written a maven plugin for this:
```xml
<plugin>
	<groupId>com.jukusoft</groupId>
	<artifactId>gettext-maven-plugin</artifactId>
	<version>1.0.3</version>
	<configuration>
        <!-- output directory, where .pot files should be saved-->
        <outputDir>lang/</outputDir>
        
        <!-- default domain and also the default pot file name (messages --> messages.pot) -->
        <defaultDomain>messages</defaultDomain>
    
        <!-- information for header in .pot files -->
        <header>
            <!-- your project title, which is written into every generated .pot file -->
            <title>My test project</title>
            
            <!-- Your copyright notice, which is shown in every generated .pot file -->
            <copyright>Copyright (c) 2018 Your Name</copyright>
            
            <!-- Your project license, which is written to every generated .pot file -->
            <license>This file is distributed under the same license as the ${project.name} package.</license>
            <author>Your name my-mail@example.com, 2018.</author>
            
            <!-- pot file version, which is saved into .pot files, e.q. ${project.version} to use same version as the maven version for this project -->
            <version>1.2.3</version>
        </header>
    
        <!-- set this to true, if you also want to create an additionally .pot file which contains ALL strings - independent from domain. Else you can set this to false. -->
        <createCompletePot>true</createCompletePot>
    </configuration>
	<executions>
		<execution>
			<phase>package</phase>
			    <goals>
					<goal>generatepot</goal>
			    </goals>
		</execution>
    </executions>
s</plugin>
```

This plugin creates a .pot file for every used domain in your source code.\
For example, if you have such a class:
```java
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
```

This will generate such a messages.pot file:
```text
# My test project
#
# Attention! Don't edit this file manually! This file is auto generated and will be overriden!
#
# Copyright (c) 2018 JuKuSoft.com
# This file is distributed under the same license as the easy-i18n-gettext package.
# JuKu my-mail@example.com, 2018.
#
#, fuzzy
msgid ""
msgstr ""
"Project-Id-Version: PACKAGE VERSION\n"
"POT-Creation-Date: 62018-10-06 19:18:140+0100\n"
"PO-Revision-Date: YEAR-MO-DA HO:MI+ZONE\n"
"Last-Translator: Auto generated file\n"
"Language: en\n"
"MIME-Version: 1.0\n"
"Content-Type: text/plain; charset=UTF-8\n"
"Content-Transfer-Encoding: 8bit\n"

#: file TestClass.java line 9
msgid "my-string"
msgstr "my-string"

#: file TestClass.java line 11
#: file TestClass.java line 10
msgid "duplicate-string"
msgstr "duplicate-string"

#: file TestClass.java line 15
msgid "sg1"
msgid_plural "pl1"
msgstr[0] "sg1"
msgstr[1] "pl1"

```

**Attention**!: It's important, that this file ends with a blank line, else parser doesn't load last string.


## HowTo: extract translation strings from code with multi-module projects

See above, but with a little difference, there is a new tag `<srcDirs>...</srcDirs>`:

```xml
<plugin>
	<groupId>com.jukusoft</groupId>
	<artifactId>gettext-maven-plugin</artifactId>
	<version>1.0.3</version>
	<configuration>
		<outputDir>lang/</outputDir>
		<defaultDomain>messages</defaultDomain>

		<header>
			<title>My test project</title>
			<copyright>Copyright (c) 2018 JuKuSoft.com</copyright>
			<license>This file is distributed under the same license as the ${project.name} package.</license>
			<author>JuKu my-mail@example.com, 2018.</author>
			<version>${project.version}</version>
		</header>

		<srcDirs>
			<dir>${project.build.sourceDirectory}</dir>
			<dir>srcDir1</dir>
			<dir>srcDir2</dir>
		</srcDirs>
	</configuration>
	<executions>
		<execution>
			<phase>package</phase>
			<goals>
				<goal>generatepot</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```

**NOTICE**: If you want to execute this plugin only in parent project and not in modules, you can use the plugin skip parameter:
```xml
<plugin>
	<groupId>com.jukusoft</groupId>
	<artifactId>gettext-maven-plugin</artifactId>
	<version>1.0.3</version>
	<configuration>
		<!-- [...] -->
		
		<skip>true</skip>
	</configuration>
	
	<!-- [...] -->
</plugin>
```

## Additional notes

```java
//this will not work for maven plugin (maven plugin cannot extract such strings into .pot file):
String str = "test-string";
System.out.println(I.tr(str));

//this will work:
System.out.println(I.tr("test-string"));

//this will also work:
String translatedString = I.tr("test-string");
System.out.println(translatedString);
```

## Directory structure

  - your language directory, e.q. "i18n" or "lang"
      * en
          * messages.po
          * menu.po
          * other-domain.po
      * de
          * messages.po
          * menu.po
          * other-domain.po

## Changelog

### 1.0.4 (WIP)

  - added support for `skip` parameter in configuration

### 1.0.3

  - removed "Po-Creation-Date" from pot header, so versioning is easier. If you want to add this, there is a new optional option for this, write this between header tags `<option.addPoCreationDate>true</option.addPoCreationDate>`. Then it will added again

### 1.0.2 (Hotfix)

  - BUGFIX: if deprecated srcDir wasn't set and a maven multi-module project was used, an exception was thrown if the project source directory was empty

### 1.0.1

  - added support for maven multi-module projects

### 1.0.0

  - translate single strings
  - translate single strings with given domain
  - translate singular / plural (with given number)
  - translate singular / plural with specified domain
  - use more than one .po files (i think this isn't possible in [awkay/easy-i18n](https://github.com/awkay/easy-i18n))
  - memory management (if a domain bundle isn't used a longer time, you can remove them automatically, e.q. if you call `I.optimizeMemory(300000)` it will remove all domain bundles which wasn't used for 300000ms, this means for 5 minutes)
  - automatically loading of domain bundles, if domain bundle doesn't exists in memory cache

## Features

  - translate single strings
  - translate single strings with given domain
  - translate singular / plural (with given number)
  - translate singular / plural with specified domain
  - use more than one .po files (i think this isn't possible in [awkay/easy-i18n](https://github.com/awkay/easy-i18n))
  - memory management (if a domain bundle isn't used a longer time, you can remove them automatically, e.q. if you call `I.optimizeMemory(300000)` it will remove all domain bundles which wasn't used for 300000ms, this means for 5 minutes)
  - support for maven multi-module projects
  - automatically loading of domain bundles, if domain bundle doesn't exists in memory cache