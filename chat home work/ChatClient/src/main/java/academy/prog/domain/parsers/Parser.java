package academy.prog.domain.parsers;

import java.lang.reflect.Type;

public interface Parser {
    String parseTo(Object inputObj);
    Object parseFrom(String text,Class<?> objClass);
    Object parseFrom(String text, Type type);
}
