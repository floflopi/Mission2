# Makefile for compiling and running a Java program

# Define the Java compiler and flags
JAVAC = javac
JAVA = java
JAVACFLAGS =

# Define the target (the Java program name)
TARGET = Main

# Default target: compile and run the program
run: $(TARGET).class
	@$(JAVA) $(TARGET)

# Compile the Java source file
$(TARGET).class: $(TARGET).java
	$(JAVAC) $(JAVACFLAGS) $(TARGET).java

# Clean up generated files
clean:
	rm -f $(TARGET).class

# This target is not a file
.PHONY: run clean
