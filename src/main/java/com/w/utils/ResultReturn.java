package com.w.utils;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ResultReturn {
    private static List<ResultBean> beanList = new ArrayList<>();

    static {
        beanList.add(new ResultBean(0, 100, 1));
        beanList.add(new ResultBean(100, 200, 2));
        beanList.add(new ResultBean(200, 300, 3));
        beanList.add(new ResultBean(300, 400, 4));
        beanList.add(new ResultBean(400, 500, 5));
        beanList.add(new ResultBean(500, 600, 6));
        beanList.add(new ResultBean(600, 700, 7));
        beanList.add(new ResultBean(700, 800, 8));
        beanList.add(new ResultBean(800, 900, 9));
        beanList.add(new ResultBean(900, 1000, 10));
        beanList.add(new ResultBean(1000, 1100, 11));
        beanList.add(new ResultBean(1100, 1200, 12));
        beanList.add(new ResultBean(1200, 1300, 13));
        beanList.add(new ResultBean(1300, 1400, 14));
        beanList.add(new ResultBean(1400, 1500, 15));
        beanList.add(new ResultBean(1500, 1600, 16));
        beanList.add(new ResultBean(1600, 1700, 17));
        beanList.add(new ResultBean(1700, 1800, 18));
        beanList.add(new ResultBean(1800, 1900, 19));
        beanList.add(new ResultBean(1900, 2000, 20));
    }

    public  int getResult(String num) {
        if (!StringUtils.isEmpty(num)) {
            int i;
            if (num.contains(".")) {
                i = Double.valueOf(num).intValue();
            } else {
                i = Integer.parseInt(num);
            }
            for (ResultBean resultBean : beanList) {
                if (Math.max(resultBean.getMin(), i) == Math.min(i, resultBean.getMax())) {
                    return resultBean.getCurr();
                }
            }
        }
        return 21;
    }

  /*  public static void main(String[] args) {
        System.out.println(getResult("899.5"));
    }*/
}
