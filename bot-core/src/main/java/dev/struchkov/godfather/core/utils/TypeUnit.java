package dev.struchkov.godfather.core.utils;

/**
 * Тип Unit-а. Обределяет способ обработки.
 *
 * @author upagge [11/07/2019]
 */
public class TypeUnit {

    public static final String TEXT = "TEXT";
    public static final String SAVE = "SAVE";
    public static final String PROCESSING = "PROCESSING";
    public static final String TIMER = "TIMER";
    public static final String CHECK = "CHECK";
    public static final String VALIDITY = "VALIDITY";
    public static final String ACCOUNT = "ACCOUNT";

    private TypeUnit() {
        throw new IllegalStateException("Утилитарный класс");
    }


}
