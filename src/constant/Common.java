/**
 * 常量
 */
package constant;

import java.util.Map;

/**
 * 常量
 * @author lvm
 *
 */
public class Common {
	/** 日期加24小时时间格式 */
	public static final String DATE_24_HOURS_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** 笔记本的临时目录 */
	public static final String NOTEBOOK_TMP_PATH ="G:/logs/";
	/** 笔记本的计算机名 */
	public static final String NOTEBOOK_NAME ="LVM-NOTEBOOK";

	/** 家里台式机的临时目录 */
	public static final String PC_HOME_TMP_PATH ="w:/logs/";
	/** 家里台式机的计算机名 */
	public static final String PC_HOME_NAME ="LVM-NOTEBOOK";
	/** 默认临时目录 */
	public static final String DEFAULT_TMP_PATH ="c:/usu/";

	/**
	 * 获取当前机器的临时文件路径
	 * @return 当前机器的临时文件路径
	 */
	public static String getCurrentTempPath() {
		Map<String, String> map = System.getenv();
		// 获取计算机名
		String computerName = map.get("COMPUTERNAME");
		if (computerName.equals(NOTEBOOK_NAME)){
			return NOTEBOOK_TMP_PATH;
		}
		if (computerName.equals(PC_HOME_NAME)){
			return PC_HOME_TMP_PATH;
		}
		return DEFAULT_TMP_PATH;
	}
}
