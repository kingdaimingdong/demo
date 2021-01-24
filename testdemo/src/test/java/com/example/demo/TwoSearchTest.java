package com.example.demo;

import org.junit.jupiter.api.Test;

public class TwoSearchTest {



    @Test
    public void twoSearchTest(){

        int [] arr = {1,2,3,4,5,5,6,6,7,7,8,9,10};

        boolean res = binFindKey(9,arr,0,arr.length-1);

        System.out.println(res);
    }


    private boolean binFindKey(int key,int [] arr,int bIndex,int eIndex){


        if(bIndex == eIndex){
            if(arr[bIndex] == key){
                return true;
            }else{
                return false;
            }
        }

        int mid = (eIndex - bIndex)/2 + bIndex;
        System.out.println("mid:"+mid);

        if(arr[mid] == key){
            return true;
        }

        if(arr[mid]> key){
            return binFindKey(key,arr,bIndex,mid-1);
        }else{
            return binFindKey(key,arr,mid+1,eIndex);
        }
    }

    @Test
    public void mergeSortTest(){

        int [] arr = {3,2,1,4,5,7,6,8,10,9};

        mergeSort(arr,0,arr.length-1);

        for(int key :arr){
            System.out.println(key);
        }

    }

    public void merge(int []a,int left,int mid,int right){
        int []tmp=new int[a.length];//辅助数组
        int p1=left,p2=mid+1,k=left;//p1、p2是检测指针，k是存放指针

        while(p1<=mid && p2<=right){
            if(a[p1]<=a[p2])
                tmp[k++]=a[p1++];
            else
                tmp[k++]=a[p2++];
        }

        while(p1<=mid) tmp[k++]=a[p1++];//如果第一个序列未检测完，直接将后面所有元素加到合并的序列中
        while(p2<=right) tmp[k++]=a[p2++];//同上

        //复制回原素组
        for (int i = left; i <=right; i++)
            a[i]=tmp[i];
    }

    public void mergeSort(int [] a,int start,int end){
        if(start<end){//当子序列中只有一个元素时结束递归
            int mid=(start+end)/2;//划分子序列
            mergeSort(a, start, mid);//对左侧子序列进行递归排序
            mergeSort(a, mid+1, end);//对右侧子序列进行递归排序
            merge(a, start, mid, end);//合并
        }
    }


}
