package test.weixin;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * view类型的按钮
 * Created by lvm on 2015/10/26.
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ViewButton extends Button {
    private String type;
    private String url;

}
