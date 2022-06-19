package games.moegirl.sinocraft.sinodivination.util;

public class StringUtils {

    public static String to_snake_name(String camelName) {
        StringBuilder name = new StringBuilder().append(Character.toLowerCase(camelName.charAt(0)));
        for (int i = 1; i < camelName.length(); i++) {
            char c = camelName.charAt(i);
            if (Character.isUpperCase(c)) {
                name.append('_').append(Character.toLowerCase(c));
            } else {
                name.append(c);
            }
        }
        return name.toString();
    }

}
