# Ensure JAVA_HOME is set and points to Java 22 or later

ifndef JAVA_HOME
$(error JAVA_HOME is not set)
endif

JAVA=$(JAVA_HOME)/bin/java
JAVAC=$(JAVA_HOME)/bin/javac
JAR=$(JAVA_HOME)/bin/jar

JAVA_VERSION_CHECK=$(shell $(JAVA) -version 2>&1 | grep 'openjdk version "22' || echo "not found")
ifeq ($(JAVA_VERSION_CHECK), not found)
$(error JAVA_HOME does not point to Java 22 or later)
endif

JAVA_OPTS=--enable-native-access=ALL-UNNAMED
JAVAC_OPTS=--enable-preview --source 22

TARGET_JAR=main.jar

JAVA_FILES=Runner.java LibC.java

MAIN_CLASS=Runner
PACKAGE_NAME=main

BUILD_DIR=build

.PHONY: all clean build run

all: build

build: $(TARGET_JAR)

$(TARGET_JAR): $(JAVA_FILES)
	@mkdir -p $(BUILD_DIR)/META-INF
	@$(JAVAC) $(JAVAC_OPTS) -d $(BUILD_DIR) $(JAVA_FILES)
	@MAIN_CLASS_FULL=$(PACKAGE_NAME).$(MAIN_CLASS); \
	  echo "Main-Class: $$MAIN_CLASS_FULL" > $(BUILD_DIR)/META-INF/MANIFEST.MF; \
	  (cd $(BUILD_DIR) && $(JAR) cmf META-INF/MANIFEST.MF ../$(TARGET_JAR) $(PACKAGE_NAME)/*.class)

run:
	@$(JAVA) $(JAVA_OPTS) -jar $(TARGET_JAR)

clean:
	@rm -rf $(TARGET_JAR) $(BUILD_DIR)
