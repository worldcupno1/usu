package test.weixin;

/**
 * click类型的按钮
 * Created by lvm on 2015/10/26.
 */
public class ClickButton extends Button {
    private String type;
    private String key;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}