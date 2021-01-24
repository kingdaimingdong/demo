package com.example.demo;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MapTest {

    @Test
    public void myMapTest(){

        MyMap  myMap = new MyMap();

        myMap.put("2",1);

        System.out.println(myMap.get("2"));

    }

    @Data
    class MyMap{

        private List<MyEntry>[] arr = new ArrayList[8];

        public MyMap(){

            arr = new ArrayList[8];
            for(int i =0 ;i<8;i++){
                arr[i] = new ArrayList<>();
            }
        }


        public void put(String key,Integer value){

            Integer a = Integer.valueOf(key);
            int index = hashKey(a);
            MyEntry myEntry = new MyEntry();
            myEntry.setKey(key);
            myEntry.setValue(value);

            for(int i = 0 ;i<arr[index].size();i++){
                MyEntry item = arr[index].get(0);
                if(item.getKey().equals(key)){
                    item.setValue(value);
                    return;
                }
            }

            arr[index].add(myEntry);

        }

        public int hashKey(Integer key){
            return key%8;
        }

        public Integer get(String key){

            Integer a = Integer.valueOf(key);
            int index = hashKey(a);

            for(int i = 0 ;i<arr[index].size();i++){
                MyEntry item = arr[index].get(0);
                if(item.getKey().equals(key)){
                    return (Integer) item.getValue();
                }
            }


            return null;
        }


    }

    @Data
    class MyEntry{
        private String key;
        private Object value;
    }
}
