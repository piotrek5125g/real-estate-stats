package pl.pni.realestatestats.util;

import pl.pni.realestatestats.constants.Size;

public class SizeUtil {

    public static String convertArea(double area) {
        for (Size size : Size.values()) {
            if (area >= size.getFromArea() && area <= size.getToArea()) {
                return size.toString();
            }
        }

        return "NON_STANDARD";
    }
}
