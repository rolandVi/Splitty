While the project can be run out-of-the-box with Gradle, running it from within your IDE (Eclipse/IntelliJ) requires setting up OpenJFX.

First download (and unzip) an OpenJFX SDK from https://openjfx.io that matches your Java JDK, then add the following *VM* commands to your run configurations:

    --module-path="/path/to/javafx-sdk/lib" --add-modules=javafx.controls,javafx.fxml

Tip: Make sure you adapt the path to the lib(!) directory (not just the directory that you unzipped).
Tip: Double check that the path is correct. You will receive abstract error messages otherwise.

# Adding a new language
1. After selecting the _Add language_ option a file gets created in the **language resources folder**.
2. The file is called `lang_language_country.properties`.
3. Open that file.
4. Each word ends with a ' = ', after each ' = ' write your desired the translation
5. Make sure there is something is written after the ' = '
6. Change the file name into this format: `lang_[abbreviation of the language]_[abbreviation of the country].properties`

(Optional) If desired, a flag can be added. Drag and move the image to the **flags resource folder**.
Make sure it is a png image and change the file name into this format: `flag_[abbreviation of the language]_[abbreviation of the country].png`

NOTE: The application requires a restart after adding a new language
**resources folder**: client/src/main/resources/languages
**flag resources folder**: client/src/main/resources/images/flags