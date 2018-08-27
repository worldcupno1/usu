package test.weixin;

/**
 * 复合类型的按钮
 * Created by lvm on 2015/10/26.
 */
public class ComplexButton extends Button {
    private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
