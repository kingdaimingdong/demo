package com.example.demo;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.junit.jupiter.api.Test;

class DemoApplicationTests {

    @Test
    public void toGsonTest() {

        User user = new User("daimingdong","33","100","98");

        GsonBuilder builder = new GsonBuilder();
        //设置expose注解，格式输出null，容错机制
        builder.excludeFieldsWithoutExposeAnnotation().serializeNulls().setLenient();
        Gson gson = builder.create();
        System.out.println(gson.toJson(user));
    }

    @Test
    public void exclusionTest(){
        User user = new User("daimingdong","33","100","98");

        Gson mGson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                //过滤掉字段名包含"id","address"的字段
                return f.getName().contains("name")|f.getName().contains("address");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                //过滤掉 类名包含 Bean的类
                return clazz.getName().contains("Bean");
            }
        }).create();

        System.out.println(mGson.toJson(user));
    }

    public void toGsonExclusion(Class<?> clazz,String [] params){

        User user = new User("daimingdong","33","100","98");

        Gson mGson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                //过滤掉字段名包含"id","address"的字段
                if(params != null) {
                    for (String key : params) {
                        if (f.getName().contains(key)) {
                            return true;
                        }
                    }
                }
                //return f.getName().contains("name")|f.getName().contains("address");
                return true;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                //过滤掉 类名包含 Bean的类
                return clazz.getName().contains("Bean");
            }
        }).create();

        System.out.println(mGson.toJson(user));
    }

    @Test
    public void fromGsonTest(){
        String gsonStr = "{\"name\":\"daimingdong\",\"age\":\"33\",\"mathScore\":\"100\",\"englishScore\":\"98\"}";
        Gson gson = new Gson();
        System.out.println(gson.fromJson(gsonStr,User.class));
    }

    class Teacher{
        @Expose
        private String name;

        public Teacher(String a){
            this.name = a;
        }
    }

    class User{
        @SerializedName(value = "fullName")
        @Expose
        private String name;
        @Expose(serialize = false, deserialize = false)
        private String age;
        @Expose
        private String mathScore;
        @Expose
        private transient String englishScore;

        private Teacher teacher;

        public User(String a,String b,String c,String d){
            this.name = a;
            this.age = b;
            this.mathScore = c ;
            this.englishScore = d;
            this.teacher = new Teacher("daijie");
        }
    }
}
