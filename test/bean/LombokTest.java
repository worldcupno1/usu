package bean;

import lombok.extern.log4j.Log4j2;
import test.weixin.ViewButton;

/**
 * Title:  <br>
 * Description: <br>
 * Date: 2018\8\6 0006 15:08<br>
 * Copyright (c)   <br>
 *
 * @author lvm
 */

@Log4j2
public class LombokTest {
    public static void main(String[] args) {
        ViewButton viewButton = new ViewButton();
        viewButton.setType("12");
        viewButton.setUrl("http://baidu.com");
        viewButton.setName("视图按钮");
        log.info(viewButton.toString());

        ViewButton viewButton2 = new ViewButton();
        viewButton2.setType("12");
        viewButton2.setUrl("http://baidu.com");
        viewButton2.setName("视图按钮");

        System.out.println(viewButton2.equals(viewButton));
        viewButton2.setType("69");
        System.out.println(viewButton2.equals(viewButton));
        log.info("lombok test");
    }
}
