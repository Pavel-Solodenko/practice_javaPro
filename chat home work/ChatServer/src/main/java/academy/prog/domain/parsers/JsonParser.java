package academy.prog.domain.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParser implements Parser {

    private String inputText;
    private Gson gson;

    public JsonParser(String inputText) {
        this.inputText = inputText;
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();
    }

    public JsonParser() {
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    @Override
    public String parseTo(Object inputObj) {
        return gson.toJson(inputObj);
    }

    @Override
    public Object parseFrom(String inputText,Class objClass) {
        return gson.fromJson(inputText,objClass);
    }
}
