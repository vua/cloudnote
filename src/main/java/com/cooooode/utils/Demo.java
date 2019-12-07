package com.cooooode.utils;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

/**
 * @Author: vua(杨晓迪)
 * @Date: Created on 2019-10-31
 * @Description:
 **/
public class Demo {
    public static BigInteger A(int n, int m) {
        BigInteger result = new BigInteger("1");
        // 循环m次,如A(6,2)需要循环2次，6*5
        for (int i = m; i > 0; i--) {
            result=result.multiply(new BigInteger(n+""));

            //System.out.println(result);
            n--;// 下一次减一
        }
        return result;
    }



    public static BigInteger C(int n, int m)// 应用组合数的互补率简化计算量
    {
        int helf = n / 2;
        if (m > helf) {
            m = n - m;
        }
        // 分子的排列数
        BigInteger numerator = A(n, m);
        // 分母的排列数
        BigInteger denominator = A(m, m);

        return numerator .divide(denominator);
    }
    @Test
    public void test(){
        BigInteger count=new BigInteger("0");
        for (int i=1;i<50;i++
             ) {
            count=count.add(C(100,i).multiply(C(100,100-2*i)));

        }
        count=count.add(C(100,50)).add(new BigInteger("1"));
        System.out.println(count);
        count=count.mod(new BigInteger("256"));

        System.out.println(count);
    }
}
