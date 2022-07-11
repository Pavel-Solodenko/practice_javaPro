package academy.prog.domain.parsers;

public interface Parser {
    String parseTo(Object inputObj);
    Object parseFrom(String text,Class<?> objClass);
}
