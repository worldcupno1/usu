package test.emax.bean;

import java.util.Date;

/**
 * 群组信息类
 * Created by lvm on 2015/11/10.
 */
public class GroupInfo implements java.io.Serializable {
    //群组id
    private Integer id;
    //群主loginId
    private String master;
    //地点--省
    private String province;
    //地点--市
    private String city;
    //群名称
    private String groupName;
    //绑定手表类型，0表示G系列，1表示S系列
    private Integer watchType;
    //是否已删除
    private Integer isDelete;
    //成员人数
    private Integer memberNum;
    //加入类型。0表示允许任何人加入，1表示需要验证
    private Integer joinType;
    //创建时间
    private Date createTime;
    //最后更新时间
    private Date lastUpdateTime;
    //群主简介
    private String groupDescription;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getWatchType() {
        return watchType;
    }

    public void setWatchType(Integer watchType) {
        this.watchType = watchType;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    public Integer getJoinType() {
        return joinType;
    }

    public void setJoinType(Integer joinType) {
        this.joinType = joinType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
}