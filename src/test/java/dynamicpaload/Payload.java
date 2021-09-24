package dynamicpaload;

class Payload {
    public static String addBook(String isbn, String aisle) {
        String raw = "{\n" +
                "\n" +
                "\"name\":\"Learn Appium Automation with Java\",\n" +
                "\"isbn\":\"%s\",\n" +
                "\"aisle\":\"%s\",\n" +
                "\"author\":\"John foe\"\n" +
                "}";
        return String.format(raw, isbn, aisle);
    }
}