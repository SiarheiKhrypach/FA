package by.tut.ssmt.controller.util;

import static java.util.Objects.isNull;

public class Util {

    public static boolean isNullOrEmpty(final String s) {
        return isNull(s) || "".equals(s);
    }

}
