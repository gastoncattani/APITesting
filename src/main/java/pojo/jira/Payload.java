package pojo.jira;

public class Payload {

    public static String createIssue(String key, String summary, String description, String name) {
        String json = "{\n" +
                "    \"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"key\": \"" + key + "\"\n" +
                "        },\n" +
                "        \"summary\": \"" + summary + "\",\n" +
                "        \"description\": \"" + description + "\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \"" + name + "\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        return json;
    }
}
