package portal.education.Monolit.utils;


public class JsonBuilder {

    private StringBuilder builder;

    public JsonBuilder(StringBuilder builder) {
        this.builder = builder;
    }

    public static JsonBuilder build() {
        return new JsonBuilder(new StringBuilder());
    }


    public JsonBuilder buildArray(final String name, final String arrayBody) {
        this.builder.append("\"").append(name)
                .append("\": ")
                .append(arrayBody);
        return this;
    }

    public JsonBuilder append(final String str) {
        this.builder.append(str);
        return this;
    }

    public JsonBuilder wrape() {

        StringBuilder builder = new StringBuilder();

        builder.append("{")
                .append(this.builder)
                .append("}");

        this.builder = builder;

        return this;
    }


    @Override
    public String toString() {
        return this.builder.toString();
    }
}
