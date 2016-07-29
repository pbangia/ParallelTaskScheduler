package app.input;


import app.exceptions.input.InvalidFileContentsException;

class DigraphFileParser {

    private String fileText;

    DigraphFileParser(String fileText){
        this.fileText = fileText;
    }

    String parse() throws InvalidFileContentsException {
        if (!validFileText()){
            throw new InvalidFileContentsException("Invalid text found in file.");
        }

        return parse(fileText);
    }

    private String parse(String fileText) {
        return fileText.substring(fileText.indexOf("{") + 1, fileText.indexOf("}"));
    }

    private boolean validFileText() {
        // TODO more guard clauses required
        if (!fileText.contains("{")){
            return false;
        }
        if (!fileText.contains("}")){
            return false;
        }

        return true;
    }

}
