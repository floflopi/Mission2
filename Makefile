# Makefile for compiling and running a Java program

# Define the Java compiler and flags
JAVAC = javac
JAVA = java
JAVACFLAGS =

# Define the target (the Java program name)
TARGET = Main

# Define the source files
SOURCES = $(wildcard *.java) $(wildcard */*.java)

# Define the class files
CLASSES = $(SOURCES:.java=.class)

# Default target: compile and run the program
run: $(CLASSES)
	@$(JAVA) $(TARGET)

# Compile the Java source files
%.class: %.java
	$(JAVAC) $(JAVACFLAGS) $<

# Clean up generated files
clean:
	find . -name '*.class' -exec rm -f {} +

# This target is not a file
.PHONY: run clean