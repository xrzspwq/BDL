# Variables
URLWIN := https://download2.gluonhq.com/openjfx/21.0.6/openjfx-21.0.6_windows-x64_bin-sdk.zip
URLUNIX := https://download2.gluonhq.com/openjfx/21.0.6/openjfx-21.0.6_linux-x64_bin-sdk.zip
ZIP_FILE := file.zip
DEST_DIR := window/dependencies
EXTRACTED_DIR := extracted_contents
OS := $(shell uname 2>/dev/null || echo Windows)

# Default target
all: download extract move compile run clean

# Download the file
download:
ifeq ($(OS), Windows)
	if not exist "window/dependencies/javafx-sdk-21.0.6" curl -L -o $(ZIP_FILE) $(URLWIN)
else
	if [ ! -e "window/dependencies/javafx-sdk-21.0.6" ]; then wget -c $(URLUNIX) -O $(ZIP_FILE) ;fi
endif

# Extract the file
extract: 
ifeq ($(OS), Windows)
	if not exist "window/dependencies/javafx-sdk-21.0.6" if not exist "$(EXTRACTED_DIR)" mkdir $(EXTRACTED_DIR)
	if not exist "window/dependencies/javafx-sdk-21.0.6" tar -xf $(ZIP_FILE) -C $(EXTRACTED_DIR)
else
	if [ ! -e "window/dependencies/javafx-sdk-21.0.6" ]; then mkdir -p $(EXTRACTED_DIR) ;fi
	if [ ! -e "window/dependencies/javafx-sdk-21.0.6" ]; then unzip -o $(ZIP_FILE) -d $(EXTRACTED_DIR) ;fi
endif

# Move the extracted contents to the destination folder
move: extract
ifeq ($(OS), Windows)
	if not exist "window/dependencies/javafx-sdk-21.0.6" if not exist "$(DEST_DIR)" mkdir $(DEST_DIR)
	if not exist "window/dependencies/javafx-sdk-21.0.6" xcopy /E /I /Y "$(EXTRACTED_DIR)\*" "$(DEST_DIR)\"
else
	if [ ! -e "window/dependencies/javafx-sdk-21.0.6" ]; then mkdir -p $(DEST_DIR) ;fi
	if [ ! -e "window/dependencies/javafx-sdk-21.0.6" ]; then mv $(EXTRACTED_DIR)/* $(DEST_DIR)/ ;fi
endif

# Compile everything 
compile : 
	javac --module-path "window/dependencies/javafx-sdk-21.0.6/lib" --add-modules javafx.controls,javafx.fxml window/*.java

#Run the program
run : 
	java --module-path "window/dependencies/javafx-sdk-21.0.6/lib" --add-modules javafx.controls,javafx.fxml window.App

# Clean up
clean:
ifeq ($(OS), Windows)
	if exist "$(ZIP_FILE)" del /Q $(ZIP_FILE)
	if exist "$(EXTRACTED_DIR)" rmdir /S /Q $(EXTRACTED_DIR)
	for /R %%F in (*.class) do del "%%F"
else
	rm "$(ZIP_FILE)"
	rm -r "$(EXTRACTED_DIR)"
	find . -type f -name "*.class" -delete
endif
