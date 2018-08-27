/**
 * 职员
 */
package useful.bean;

import com.google.common.collect.ComparisonChain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author lvm
 *
 */
public class Employee implements Comparable<Employee> {
	// 部门
	private String dept;
	// 姓名
	private String fullname;
	// 手机号
	private String phone;
	// 登录名
	private String loginId;
	// 昵称
	private String nickname;

	// 走路日期
	private ArrayList<String> walkDate = new ArrayList<String>();
	// 步数
	private ArrayList<Integer> setpCount = new ArrayList<Integer>();
	// 步数积分
	private ArrayList<Integer> setpScore = new ArrayList<Integer>();
	
	//当月总步数
	private int totalStep = 0;
	//当月总积分
	private int totalScore = 0;

    /**
     * 规则是，右边比左边大，返回1 ，即为真
     * @param e
     * @return
     */
    @Override
    public int compareTo(Employee e) {
        return ComparisonChain.start()
                .compare(totalScore, e.totalScore)
                .compare(e.totalStep, totalStep)
                .result();
    }

    @Override
    public String toString() {
        return "fullname=" + fullname + ",totalStep=" + totalStep
                + "totalScore=" + totalScore;
    }

    public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public ArrayList<String> getWalkDate() {
		return walkDate;
	}

	public void setWalkDate(ArrayList<String> walkDate) {
		this.walkDate = walkDate;
	}

	public ArrayList<Integer> getSetpCount() {
		return setpCount;
	}

	public void setSetpCount(ArrayList<Integer> setpCount) {
		this.setpCount = setpCount;
	}

	public ArrayList<Integer> getSetpScore() {
		return setpScore;
	}

	public void setSetpScore(ArrayList<Integer> setpScore) {
		this.setpScore = setpScore;
	}

	public Integer getTotalStep() {
		return totalStep;
	}

	public void setTotalStep(Integer totalStep) {
		this.totalStep = totalStep;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
}
