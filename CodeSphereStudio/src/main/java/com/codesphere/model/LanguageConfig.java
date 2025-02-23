package com.codesphere.model;

public class LanguageConfig {
    private String image;
    private String command;
    private String fileExtension;
    private String compileCommand;

    public LanguageConfig() {
    }

    public LanguageConfig(String image, String command, String fileExtension, String compileCommand) {
        this.image = image;
        this.command = command;
        this.fileExtension = fileExtension;
        this.compileCommand = compileCommand;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getCompileCommand() {
        return compileCommand;
    }

    public void setCompileCommand(String compileCommand) {
        this.compileCommand = compileCommand;
    }

    @Override
    public String toString() {
        return "LanguageConfig{" +
                "image='" + image + '\'' +
                ", command='" + command + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                ", compileCommand='" + compileCommand + '\'' +
                '}';
    }

    // Static Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String image;
        private String command;
        private String fileExtension;
        private String compileCommand;

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Builder command(String command) {
            this.command = command;
            return this;
        }

        public Builder fileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
            return this;
        }

        public Builder compileCommand(String compileCommand) {
            this.compileCommand = compileCommand;
            return this;
        }

        public LanguageConfig build() {
            return new LanguageConfig(image, command, fileExtension, compileCommand);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LanguageConfig that = (LanguageConfig) o;

        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (command != null ? !command.equals(that.command) : that.command != null) return false;
        if (fileExtension != null ? !fileExtension.equals(that.fileExtension) : that.fileExtension != null) return false;
        return compileCommand != null ? compileCommand.equals(that.compileCommand) : that.compileCommand == null;
    }

    @Override
    public int hashCode() {
        int result = image != null ? image.hashCode() : 0;
        result = 31 * result + (command != null ? command.hashCode() : 0);
        result = 31 * result + (fileExtension != null ? fileExtension.hashCode() : 0);
        result = 31 * result + (compileCommand != null ? compileCommand.hashCode() : 0);
        return result;
    }
}
