package ru.sbrf.test;

import org.apache.commons.lang3.StringUtils;

public class Main {
    public static final String MAIN_TEXT = "MAIN";
    public static void main(String[] args) {
        SomeClass sc = new SomeClass("bar");
        System.out.println(StringUtils.join("Hello","World",","));
    }
}
