package test.emax.bean;

/**json格式的设备消息对象
 * Created by lvm on 2016/7/12.
 */
public class DeviceMsgJson implements java.io.Serializable {

    private static final long serialVersionUID = 3740553219590224528L;

    private String device_id;

    private String device_type;

    private Integer msg_id;

    private String msg_type;

    private Integer create_time;

    private String open_id;

    private Integer session_id;

    private String content;

    private String qrcode_suffix_data;

    public DeviceMsgJson(String device_id, String device_type, Integer msg_id,
                         String msg_type, Integer create_time, String open_id,
                         Integer session_id, String content,
                         String qrcode_suffix_data) {
        this.device_id = device_id;
        this.device_type = device_type;
        this.msg_id = msg_id;
        this.msg_type = msg_type;
        this.create_time = create_time;
        this.open_id = open_id;
        this.session_id = session_id;
        this.content = content;
        this.qrcode_suffix_data = qrcode_suffix_data;
    }

    public DeviceMsgJson() {
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }



    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public Integer getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(Integer msg_id) {
        this.msg_id = msg_id;
    }

    public Integer getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Integer create_time) {
        this.create_time = create_time;
    }

    public Integer getSession_id() {
        return session_id;
    }

    public void setSession_id(Integer session_id) {
        this.session_id = session_id;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQrcode_suffix_data() {
        return qrcode_suffix_data;
    }

    public void setQrcode_suffix_data(String qrcode_suffix_data) {
        this.qrcode_suffix_data = qrcode_suffix_data;
    }
}
