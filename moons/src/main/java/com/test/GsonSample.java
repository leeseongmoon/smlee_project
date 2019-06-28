package com.test;

import com.google.gson.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class GsonSample {

    public static void main(String[] args) {
        //Gson gson = new GsonBuilder().serializeNulls().create();

        Company company = Company.getCompanyDummy();

        System.out.println("========= Object => Json ==========");
        String company2Json = new Gson().toJson(company);
        System.out.println(company2Json);

        System.out.println("");
        System.out.println("========= Json => Object =========");
        Company json2Company = new Gson().fromJson(company2Json, Company.class);
        printCompanyObject(json2Company);

        System.out.println("");
        System.out.println("========= Object => Json =========");
        String company2JsonIsNull = new GsonBuilder().serializeNulls().create().toJson(company);
        System.out.println(company2JsonIsNull);

        System.out.println("");
        System.out.println("========= Json => Object =========");
        Company json2CompanyIsNull = new Gson().fromJson(company2JsonIsNull, Company.class);
        printCompanyObject(json2CompanyIsNull);

        System.out.println("");
        System.out.println("========= the other =========");

        String companyJson= "{\"a\": \"aa\", \"b\": \"123\", \"c\": \"12.3\", \"d\": {}, " +
                            "\"e\": [" +
                                        "{\"bb1\": \"55555\",\"bb2\": \"12345\"}," +
                                        "{\"bb1\": \"99999\",\"bb2\": \"54321\"}" +
                                    "]}";

        JsonObject jsonObject = new JsonParser().parse(companyJson).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("e");
        System.out.println("jsonArray ====> "+jsonArray);
        System.out.println("jsonArray.size() ====> "+jsonArray.size());
        JsonObject jsonObject1 = jsonArray.get(1).getAsJsonObject();

        for(JsonElement el : jsonArray){
            JsonObject obj = new JsonObject();
            System.out.println("@@@@@@@@@@@@@@@@@@ element ::  "+el.getAsJsonObject());

            obj = el.getAsJsonObject();

            //System.out.println("@@@@@@@@@@@@@@@@@@ object :: "+obj.get("bb1"));
            //System.out.println("@@@@@@@@@@@@@@@@@@ object :: "+obj.get("bb2"));


            JsonPrimitive pri = obj.getAsJsonPrimitive("bb1");
            String str = pri.getAsString();
            int in = pri.getAsInt();
            System.out.println("@@@@@@@@@@@@@@@@@@ "+str + " / " + in);

        }

        System.out.println("========> jsonObject1 :: "+jsonObject1);

        JsonPrimitive jsonPrimitive = jsonObject1.getAsJsonPrimitive("bb1");
        int value = jsonPrimitive.getAsInt();
        System.out.println("value == 55555 is : " + (value == 55555));
        System.out.println("value == 12345 is : " + (value == 12345));



        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        object.addProperty("name", "park");
        object.addProperty("age", 22);
        object.addProperty("success", true);
        String json = gson.toJson(object);
        System.out.println(json);

        // json을 파싱
        String jsonm = "{\"name\":\"kim\",\"age\":20,\"gender\":\"M\"}";
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonm);
        String name = element.getAsJsonObject().get("name").getAsString();
        System.out.println("name = "+name);
        int age = element.getAsJsonObject().get("age").getAsInt();
        System.out.println("age = "+age);

        // json을 객체로 변환
        String jsonO = "{\"name\":\"kim\",\"age\":20,\"gender\":\"M\"}";
        Gson gsonO = new Gson();
        Person person = gsonO.fromJson(jsonO, Person.class);

        System.out.println("nameo = " + person.getName());
        System.out.println("ageo = " + person.getAge());
        System.out.println("gendero = " + person.getGender());

    }

    private static void printCompanyObject(Company company){
        List<Company.Person> personList = company.getEmployees();
        System.out.println("userName : " + company.getName());

        for(Company.Person person : personList){
            System.out.println(person);
        }
    }


}

class Person{
    private String name;
    private int age;
    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}