package ru.myitschool.work;

public class RequestBodyModel {
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String value;

    public RequestBodyModel(String value) {
        this.value = value;
    }
}
