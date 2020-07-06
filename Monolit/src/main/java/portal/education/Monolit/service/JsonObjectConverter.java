package portal.education.Monolit.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JsonObjectConverter {

    private Gson gson;

    public JsonObjectConverter() {
        this.gson = new Gson();
    }

    public <T> T jsonToObject(String jsonStr, Class<T> clazz) {
        return gson.fromJson(jsonStr, clazz);
    }

    public <T> String objectToJson(T obj) {
        return gson.toJson(obj);
    }

    public <T> String objectToJson(T obj, Class<T> clazz) {
        return gson.toJson(obj, clazz);
    }

}
