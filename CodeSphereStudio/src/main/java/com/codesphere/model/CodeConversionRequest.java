package com.codesphere.model;

public class CodeConversionRequest {
    private String sourceCode;
    private String sourceLanguage;
    private String targetLanguage;

    public CodeConversionRequest() {
    }

    public CodeConversionRequest(String sourceCode, String sourceLanguage, String targetLanguage) {
        this.sourceCode = sourceCode;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    @Override
    public String toString() {
        return "CodeConversionRequest{" +
                "sourceCode='" + sourceCode + '\'' +
                ", sourceLanguage='" + sourceLanguage + '\'' +
                ", targetLanguage='" + targetLanguage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeConversionRequest that = (CodeConversionRequest) o;

        if (sourceCode != null ? !sourceCode.equals(that.sourceCode) : that.sourceCode != null) return false;
        if (sourceLanguage != null ? !sourceLanguage.equals(that.sourceLanguage) : that.sourceLanguage != null)
            return false;
        return targetLanguage != null ? targetLanguage.equals(that.targetLanguage) : that.targetLanguage == null;
    }

    @Override
    public int hashCode() {
        int result = sourceCode != null ? sourceCode.hashCode() : 0;
        result = 31 * result + (sourceLanguage != null ? sourceLanguage.hashCode() : 0);
        result = 31 * result + (targetLanguage != null ? targetLanguage.hashCode() : 0);
        return result;
    }
}
