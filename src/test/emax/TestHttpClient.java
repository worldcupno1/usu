package test.emax;

import java.io.*;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import test.emax.bean.DeviceMsgJson;
import test.emax.bean.GroupInfo;
import test.emax.bean.StepDataFactory;
import util.http.HttpUtil;

public class TestHttpClient {

	String useHeader = null;
	static final String LOCAL_Header = "http://localhost:8080/phone?";
	static final String LOCAL_SYNC_Header = "http://localhost:8080/sync";
	static final String PRODUCE_Header = "http://121.40.218.161/phone?";
    static final String PRODUCE_HEADER_SYNC = "http://121.40.218.161/sync";
    static final String Weixin_Header = "http://ezontest.sinaapp.com/phone?";
    static final String LOCAL_WEIXIN_HEADER = "http://localhost:8080/device?";
    /*static final String PRODUCE_Header = "http://ezonsport.com/phone?";*/
	static final String SERVICE_EQ = "service=";
	static final String SERVICE_GET_SID = SERVICE_EQ + "common.getSid";
	static final String SERVICE_SEND_SMS = SERVICE_EQ + "common.sendSMS";
	static final String SERVICE_SEND_PASSWORD_EMAIL = SERVICE_EQ + "common.getPassword";
    static final String SERVICE_CHECK_LOGINID =SERVICE_EQ + "common.checkLoginId";
    static final String SERVICE_TEST_UPLOAD =SERVICE_EQ + "common.testUpload";
    static final String SERVICE_RESET_PASSWORD = SERVICE_EQ + "common.resetPassword";
    static final String SERVICE_CHECK_BIND = SERVICE_EQ + "common.checkBind";
    static final String SERVICE_CHECK_SMS = SERVICE_EQ + "common.checkSMS";
    static final String SERVICE_EXEC_TASK = SERVICE_EQ + "common.execTask";
    static final String SERVICE_READ_TEST_LOG = SERVICE_EQ + "common.readTestLog";
    static final String SERVICE_QUERY_STAT_BY_DATE = SERVICE_EQ + "common.queryStatByDate";
    static final String SERVICE_DEDUCT_CREDITS = SERVICE_EQ + "common.deductCredits";
    static final String SERVICE_BATCH_PRODUCE_DEVICE_QCODE = SERVICE_EQ + "common.batchProduceDeviceQcode";
    static final String SERVICE_QUERY_DEVICE_REL_QRCODE = SERVICE_EQ + "common.queryDeviceRelQrcode";
    static final String SERVICE_QUERY_DEVICE_LIST_BY_MAC = SERVICE_EQ + "common.queryDeviceListByMac";
    static final String SERVICE_GET_SYNCINFO = SERVICE_EQ + "common.getSyncinfo";
    static final String SERVICE_ASSIGN_MAC = SERVICE_EQ + "common.assignMac";
    static final String SERVICE_CHECK_PWD = SERVICE_EQ + "common.checkPwd";

	static final String SERVICE_QUERY_NEW_MY_SHARE = SERVICE_EQ + "user.queryNewMyShare";
	static final String SERVICE_QUERY_NEW_SHARE_INFO = SERVICE_EQ + "user.queryNewShareInfo";
	static final String SERVICE_QUERY_SORTED_SHARE_INFO = SERVICE_EQ + "user.querySortedShareInfo";
	static final String SERVICE_SHARE_COMMENT = SERVICE_EQ + "user.shareComment";
	static final String SERVICE_LOGIN =SERVICE_EQ + "user.login";
	static final String SERVICE_SYNCFORM = SERVICE_EQ + "sync.syncFromService";
    static final String SERVICE_SYNC_TABLE = SERVICE_EQ + "sync.syncTable";
	static final String SERVICE_UPDATE_USER = SERVICE_EQ + "user.updateUser";
	static final String SERVICE_QUERY_USER_PHOTO = SERVICE_EQ + "user.queryUserPhoto";
	static final String SERVICE_GET_SHARE_COUNT = SERVICE_EQ + "user.getShareCount";
	static final String SERVICE_QUERY_NEW_MSG_TIP = SERVICE_EQ + "user.queryNewMsgTip";
	static final String SERVICE_UPDATE_VIEWTIME = SERVICE_EQ + "user.updateViewTime";
	static final String SERVICE_QUERY_PIC_INFO = SERVICE_EQ + "user.queryPicInfo";
	static final String SERVICE_QUERY_RANKLING_LIST = SERVICE_EQ + "user.queryRankingList";
    static final String SERVICE_CREATE_GROUP = SERVICE_EQ + "user.createGroup";
    static final String SERVICE_JOIN_GROUP = SERVICE_EQ + "user.joinGroup";
    static final String SERVICE_QUERY_GROUP_LIST = SERVICE_EQ + "user.queryGroupList";
    static final String SERVICE_UPDATE_GROUP_INFO = SERVICE_EQ + "user.updateGroupInfo";
    static final String SERVICE_VERIFY_JOIN = SERVICE_EQ + "user.verifyJoin";
    static final String SERVICE_LEAVE_GROUP = SERVICE_EQ + "user.leaveGroup";
    static final String SERVICE_QUERY_ALL_MSG_NUM = SERVICE_EQ + "user.queryAllMsgNum";
    static final String SERVICE_QUERY_ALL_MSG = SERVICE_EQ + "user.queryAllMsg";
    static final String SERVICE_QUERY_GROUP_MEMBERS = SERVICE_EQ + "user.queryGroupMembers";
    static final String SERVICE_QUERY_GROUP_INFO = SERVICE_EQ + "user.queryGroupInfo";
    static final String SERVICE_QUERY_ZONE = SERVICE_EQ + "user.queryZone";
    static final String SERVICE_QUERY_GROUP_RANKING_LIST = SERVICE_EQ + "user.queryGroupRankingList";
    static final String SERVICE_QUERY_PERSON_STAT_INFO = SERVICE_EQ + "user.queryPersonStatInfo";
    static final String SERVICE_SHARE_BY_CLIENT = SERVICE_EQ + "user.shareByClient";
    static final String SERVICE_QUERY_QRCODE = SERVICE_EQ + "user.queryWeixinQrcode";
    static final String SERVICE_QUERY_TOKEN = SERVICE_EQ + "user.queryToken";
    static final String SERVICE_DEL_PICRELATION = SERVICE_EQ + "user.delPicRelation";
    static final String SERVICE_QUERY_PICRELATIONS = SERVICE_EQ + "user.queryPicRelations";
    static final String SERVICE_QUERY_NEWEST_SHARE = SERVICE_EQ + "user.queryNewestShare";
    static final String SERVICE_QUERY_MY_NEWEST_SHARE = SERVICE_EQ + "user.queryMyNewestShare";
    static final String SERVICE_QUERY_OTHERS_NEWEST_SHARE = SERVICE_EQ +"user.queryOthersNewestShare";
    static final String SERVICE_SHARE_PIC_COMMENT = SERVICE_EQ + "user.sharePicComment";
    static final String SERVICE_SHARE_PIC_PRAISE = SERVICE_EQ + "user.sharePicPraise";
    static final String SERVICE_QUERY_PIC_DETAIL = SERVICE_EQ + "user.queryPicDetail";
    static final String SERVICE_FEEDBACK = SERVICE_EQ + "user.feedback";
    static final String SERVICE_GET_AUTOLOGIN_URL = SERVICE_EQ + "user.getAutoLoginUrl";
    static final String SERVICE_USERWATCH = SERVICE_EQ + "user.userWatch";
    static final String SERVICE_GET_WEATHER = SERVICE_EQ + "user.getWeather";
	
	static final String SID_EQ = "&sid=";
	static final String PHONE_EQ = "&phone=";
	static final String JSON_EQ = "&json=";
	
	//用户数据
	static final String User_LoginId_1 = "peter@ezonwatch.com";
	static final String User_Id_1 = "9c62b1d3-14b1-491b-bb0d-edb68aa42a70";
	static final String User_Password_1 = "e10adc3949ba59abbe56e057f20f883e";
	
	static final String User_LoginId_2 = "111111";
	static final String User_Id_2 = "a3d6fd41-2c7f-4fc9-911f-6adf8815cdc6";
	static final String User_Password_2 = "e10adc3949ba59abbe56e057f20f883e";
	
	static final String User_LoginId_3 = "15759922277";
	static final String User_Id_3 = "beaf339e-f91e-41ff-aa70-e5543a164d19";
	static final String User_Password_3 = "25f9e794323b453885f5181f1b624d0b";

    static final String User_LoginId_4 = "jacky1979888@163.com";
    static final String User_Id_4 = "01128a6e-2e9b-4997-a5b0-8ac39702b857";
    static final String User_Password_4 = "e10adc3949ba59abbe56e057f20f883e";

    static final String User_LoginId_5 = "222222";
    static final String User_Id_5 = "a3e78e23-93d0-4dad-970c-28607e8562e2";
    static final String User_Password_5 = "e10adc3949ba59abbe56e057f20f883e";

    static final String User_LoginId_6 = "333333";
    static final String User_Id_6 = "d00979ee-5d29-4060-a4ac-2908c0d0210b";
    static final String User_Password_6 = "e10adc3949ba59abbe56e057f20f883e";

    static final String User_LoginId_7 = "17090090954";
    static final String User_Id_7 = "c8dfb741-e6b8-41cc-950a-57f23470cf39";
    static final String User_Password_7 = "e10adc3949ba59abbe56e057f20f883e";

    static final String User_LoginId_8 = "71800900@qq.com";
    static final String User_Id_8 = "c161c041-b09c-4f67-a1fe-53d57702135a";
    static final String User_Password_8 = "4c34e3b1ebd3d9aa5078b14bccfd3bdd";

	
	
	private static Logger logger = LogManager.getLogger();
	
	  
	
	/** 模拟用户登录
	 * @param t 
	 * @throws Exception 
	 * 
	 */
	public void login(TestHttpClient t) throws Exception {
		//模拟用户登录
		String sidDes = getSid(t);
		//logger.info("sidDes=" + sidDes);
        
		StringBuilder url2 = new StringBuilder(t.getUseHeader());
		url2.append(SERVICE_LOGIN).append(SID_EQ);
		url2.append(sidDes);

		HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("loginId", User_LoginId_6);
        paraMap.put("password", User_Password_6);

		String paraStr = JSON.toJSONString(paraMap);
        logger.info(url2.toString() + JSON_EQ + paraStr);
		url2.append(JSON_EQ + URLEncoder.encode(paraStr, "UTF-8"));
		String result2 = HttpUtil.httpGet(url2.toString());
		logger.info(result2);
		//模拟用户登录 end ,登录完成
	}

	/** 获取sid
	 * @param t
	 * @return
	 * @throws Exception
	 */
	private String getSid(TestHttpClient t) throws Exception {
		StringBuilder url = new StringBuilder(t.getUseHeader());
		url.append(SERVICE_GET_SID);
		String result = HttpUtil.httpGet(url.toString());
		
		JSONObject jsonObject = JSONObject.parseObject(result);
        String sid = (String) jsonObject.get("data");
        logger.info("原始的：" + sid);
        String sidDes = DecodeUtils.decodeDES(sid);
        logger.info("解密后：" + sidDes);
		return sidDes;
	}
	
	/** 获取登录后的sid
	 * @param t
	 * @return
	 * @throws Exception
	 */
	private String getLoginSid(TestHttpClient t ,String loginId , String password) throws Exception {
		StringBuilder url = new StringBuilder(t.getUseHeader());
		url.append(SERVICE_GET_SID);
		String result = HttpUtil.httpGet(url.toString());
		
		JSONObject jsonObject = JSONObject.parseObject(result);
        String sid = (String) jsonObject.get("data");
        String sidDes = DecodeUtils.decodeDES(sid);
        
        StringBuilder url2 = new StringBuilder(t.getUseHeader());
		url2.append(SERVICE_LOGIN).append(SID_EQ);
		url2.append(sidDes);
		
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("loginId", loginId);
		paraMap.put("password", password);

		String paraStr = JSON.toJSONString(paraMap);
		url2.append(JSON_EQ + URLEncoder.encode(paraStr,"UTF-8"));
		String result2 = HttpUtil.httpGet(url2.toString());
		
		
		return sidDes;
	}
	
	
	
	/**
	 * 从服务器上，下载数据
	 * @param t
	 * @throws Exception
	 */
	public void syncformServiceZip(TestHttpClient t) throws Exception {
        StringBuilder url = new StringBuilder();

        String sidDes = getLoginSid(t, User_LoginId_2, User_Password_2);
        url.append(PRODUCE_HEADER_SYNC);
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("pageSize", "10");
        paraMap.put("syncTime", "1459302646263");
        paraMap.put("page", "0");
        paraMap.put("tableName", "bpm_count");

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("service", "sync.syncFromService");
        jsonObj.put("sid", sidDes);
        jsonObj.put("json", paraMap);

        logger.info(jsonObj.toString());

        HttpUtil.httpGzipPost(url.toString(), jsonObj.toString());
	}
	
	/**
	 * 评论
	 * @param t
	 * @throws Exception
	 */
	public void shareComment(TestHttpClient t) throws Exception {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("loginId", User_LoginId_7);
//		paraMap.put("nickName", "peter");
		paraMap.put("action", 1);
		paraMap.put("content", "第一代领导人快开始的减肥");
		paraMap.put("shareId", 33266);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_7, User_Password_7, SERVICE_SHARE_COMMENT, paraMap));
		System.out.println(result);
		
	}

	/**
	 * 查询自己的分享
	 * @param t
	 * @throws Exception
	 */
	public void queryNewMyShare(TestHttpClient t) throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        /*paraMap.put("loginId", User_LoginId_2);
        paraMap.put("userid", User_Id_2);*/
        paraMap.put("page", 0);
        paraMap.put("pageSize", 10);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_5, User_Password_5, SERVICE_QUERY_MY_NEWEST_SHARE, paraMap));
        logger.info(result);
	}
	
	/**
	 * 查询所有新的帖子
	 * @param t
	 * @throws Exception
	 */
	public void queryNewShareInfo(TestHttpClient t) throws Exception {
	    HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("page", 0);
        paraMap.put("pageSize", 10);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_3, User_Password_3, SERVICE_QUERY_NEW_SHARE_INFO, paraMap));
//        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_3, User_Password_3, SERVICE_QUERY_NEWEST_SHARE, paraMap));
        logger.info(result);

	}
	
	/**
	 * 更新用户信息，包括头像photo
	 * @param t
	 * @throws Exception
	 */
	public void updateUser(TestHttpClient t) throws Exception {
		StringBuilder url = new StringBuilder();
		String sidDes = getLoginSid(t, User_LoginId_2, User_Password_2);
		url.append(t.getUseHeader());
		url.append(SERVICE_UPDATE_USER).append(SID_EQ).append(sidDes);
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		HashMap<String, Object> fieldMap1 = new HashMap<String, Object>();
		fieldMap1.put("fieldValue", "sdfsfd");
		fieldMap1.put("fieldName", "photo");
		ArrayList<HashMap<String, Object>> a = new ArrayList<HashMap<String, Object>>();
		a.add(fieldMap1);
		paraMap.put("field", a);

		String paraStr = JSON.toJSONString(paraMap);
		url.append(JSON_EQ + URLEncoder.encode(paraStr, "UTF-8"));
		System.out.println(JSON_EQ + paraStr);
		String result = HttpUtil.httpGet(url.toString());
		System.out.println(result);
	}
	
	/**
	 * 查询用户头像
	 * @param t
	 * @throws Exception
	 */
	public void queryUserPhoto(TestHttpClient t) throws Exception {
		StringBuilder url = new StringBuilder();
		String sidDes = getLoginSid(t, User_LoginId_2, User_Password_2);

		url.append(t.getUseHeader());
		url.append(SERVICE_QUERY_USER_PHOTO).append(SID_EQ).append(sidDes);
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		ArrayList<String> ids = new ArrayList<String>();
		//ids.add("444444");
		ids.add("123456");
		ids.add("jayweison@126.com");
		ids.add("ez@163.com");
		paraMap.put("loginIds", ids);


		String paraStr = JSON.toJSONString(paraMap);
		url.append(JSON_EQ + URLEncoder.encode(paraStr, "UTF-8"));
		String result = HttpUtil.httpGet(url.toString());
		System.out.println(result);
	}
	
	/**
	 * 用户手机重置密码
	 * @param t
	 * @throws Exception 
	 */
	public void resetPassword(TestHttpClient t) throws Exception {
		//step1
		StringBuilder url = new StringBuilder();
		url.append(t.getUseHeader()).append(SERVICE_RESET_PASSWORD);
		
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("step", 1001);
		paraMap.put("mobileNum", "18905454189");

		String paraStr = JSON.toJSONString(paraMap);
		logger.info(url.toString() + JSON_EQ + paraStr);
		url.append(JSON_EQ + URLEncoder.encode(paraStr,"UTF-8"));
		String result = HttpUtil.httpGet(url.toString());
		logger.info(result);
		
		//step2
		url.setLength(0);
		url.append(t.getUseHeader()).append(SERVICE_RESET_PASSWORD);
		paraMap.clear();
		JSONObject jsonObject = JSONObject.parseObject(result);
        String vid = (String) jsonObject.get("msg");
		paraMap.put("step", 1002);
		paraMap.put("vcode", "");
		paraMap.put("vid", vid);
		
		paraStr = JSON.toJSONString(paraMap);
		logger.info(url.toString() + JSON_EQ + paraStr);
		url.append(JSON_EQ).append(URLEncoder.encode(paraStr,"UTF-8"));
		result = HttpUtil.httpGet(url.toString());
		logger.info(result);
		
		//step3
		url.setLength(0);
		url.append(t.getUseHeader()).append(SERVICE_RESET_PASSWORD);
		paraMap.clear();
		paraMap.put("step", 1003);
		paraMap.put("vcode", "");
		paraMap.put("mobileNum", "18905454189");
		paraMap.put("vid", vid);
		paraMap.put("newPassword", "123456");
		
		paraStr = JSON.toJSONString(paraMap);
		logger.info(url.toString() + JSON_EQ + paraStr);
		url.append(JSON_EQ).append(URLEncoder.encode(paraStr,"UTF-8"));
		result = HttpUtil.httpGet(url.toString());
		logger.info(result);
	}
	
	/**
	 * 获取跑友圈新消息数量
	 * @param t
	 * @throws Exception
	 */
	public void getShareCount(TestHttpClient t) throws Exception {
		StringBuilder url = new StringBuilder();
		String sidDes = getLoginSid(t, User_LoginId_3, User_Password_3);

		url.append(t.getUseHeader());
		url.append(SERVICE_GET_SHARE_COUNT).append(SID_EQ).append(sidDes);
	
		logger.info(url.toString());
		String result = HttpUtil.httpGet(url.toString());
		logger.info(result);
	}
	
	/**
	 * 获取跑友圈点赞user_share_praise和评论user_comment的消息提示
	 * @param t
	 * @throws Exception
	 */
	public void queryNewMsgTip(TestHttpClient t) throws Exception {
		StringBuilder url = new StringBuilder();
		String sidDes = getLoginSid(t, User_LoginId_3, User_Password_3);
		url.append(t.getUseHeader());
		url.append(SERVICE_QUERY_NEW_MSG_TIP).append(SID_EQ).append(sidDes);

		logger.info(url.toString());
		String result = HttpUtil.httpGet(url.toString());
		logger.info(result);
	}
	
	/**
	 * 更新查询时间
	 * @param t
	 * @throws Exception
	 */
	public void updateViewTime(TestHttpClient t) throws Exception {
		StringBuilder url = new StringBuilder();
		String sidDes = getLoginSid(t, User_LoginId_1, User_Password_1);
		url.append(t.getUseHeader());
		url.append(SERVICE_UPDATE_VIEWTIME).append(SID_EQ).append(sidDes);
	
		logger.info(url.toString());
		String result = HttpUtil.httpGet(url.toString());
		logger.info(result);
	}
	
	/**
	 * 查询排序过的新的帖子
	 * @param t
	 * @throws Exception
	 */
	public void querySortedShareInfo(TestHttpClient t) throws Exception {
		StringBuilder url = new StringBuilder();
		String sidDes = getLoginSid(t, User_LoginId_2, User_Password_2);
		url.append(t.getUseHeader());
		url.append(SERVICE_QUERY_SORTED_SHARE_INFO).append(SID_EQ).append(sidDes);
		
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("page", 0);
		paraMap.put("pageSize", 10);
		String paraStr = JSON.toJSONString(paraMap);
		logger.info(url.toString() + JSON_EQ + paraStr);
		url.append(JSON_EQ).append(URLEncoder.encode(paraStr, "UTF-8"));
		String result = HttpUtil.httpGet(url.toString());
		logger.info(result);
	}
	
	/**
	 * 查询图片信息，修改时间，大小
	 * @param t
	 * @throws Exception
	 */
	public void queryPicInfo(TestHttpClient t) throws Exception {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		ArrayList<String> types = new ArrayList<String>();
		types.add("userIcon");
		types.add("friends");
		types.add("pcIcon");
        types.add("group");
		paraMap.put("picType", types);
        paraMap.put("groupId",23);
        paraMap.put("loginId","333333");

        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_1, User_Password_1, SERVICE_QUERY_PIC_INFO, paraMap));
        logger.info(result);
	}
	
	/**
	 * 上传同步数据到服务器（Gzip方式）
	 * @param t
	 * @throws Exception
	 */
	public void syncTableGzip(TestHttpClient t) throws Exception {
		StringBuilder url = new StringBuilder();
		String sidDes = getLoginSid(t, User_LoginId_7, User_Password_7);
//		url.append(LOCAL_SYNC_Header);
        url.append(PRODUCE_HEADER_SYNC);
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
        List l = new ArrayList();
        l.add(StepDataFactory.produceOneRecord("160205",null,null,null,
                StepDataFactory.produceDefaultStepDetail(),null,"333","2130","253","157","69104"));
        String param = StepDataFactory.produceStepData(sidDes,StepDataFactory.produceJsonParam(l));

		logger.info(param);
		//url.append(JSON_EQ).append(URLEncoder.encode(paraStr,"UTF-8"));

        HttpUtil.httpGzipPost(url.toString(), param);

	}

    /**
     * 查询图片信息，修改时间，大小（Gzip方式）
     * @param t
     * @throws Exception
     */
    public void queryPicInfoGzip(TestHttpClient t) throws Exception {
        StringBuilder url = new StringBuilder();
        String sidDes = getLoginSid(t, User_LoginId_1, User_Password_1);
        url.append(LOCAL_SYNC_Header);

        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        ArrayList<String> types = new ArrayList<String>();
        types.add("userIcon");
        types.add("friends");
        types.add("pcIcon");
        paraMap.put("picType", types);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("service", "user.queryPicInfo");
        jsonObj.put("sid", sidDes);
        jsonObj.put("json", paraMap);

        //String paraStr = JSON.toJSONString(paraMap);
        logger.info(jsonObj.toString());
        //url.append(JSON_EQ).append(URLEncoder.encode(paraStr,"UTF-8"));

        HttpUtil.httpGzipPost(url.toString(), jsonObj.toString());

    }

	/**
	 * 发送重置密码邮件
	 * @param t
	 */
	public void sendPasswordEmail(TestHttpClient t) throws UnsupportedEncodingException {
		StringBuilder url = new StringBuilder();
		url.append(t.getUseHeader());
		url.append(SERVICE_SEND_PASSWORD_EMAIL);

		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("loginId", User_LoginId_2);

		String paraStr = JSON.toJSONString(paraMap);
		logger.info(url.toString() + JSON_EQ + paraStr);
		url.append(JSON_EQ).append(URLEncoder.encode(paraStr, "UTF-8"));
		String result = HttpUtil.httpGet(url.toString());
		logger.info(result);

	}

	/**
	 * 查询排行榜
	 * @param t
	 * @throws Exception
	 */
	public void queryRankingList(TestHttpClient t) throws Exception {
		StringBuilder url = new StringBuilder();
		String sidDes = getLoginSid(t, "13599097803", "5e337b952084e4f3bad41fe41c780a9c");
		url.append(t.getUseHeader());
		url.append(SERVICE_QUERY_RANKLING_LIST).append(SID_EQ).append(sidDes);

		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		ArrayList<String> types = new ArrayList<String>();
//		types.add("G_SERIES_TODAY");
//		types.add("G_SERIES_THIS_WEEK");
		types.add("G_SERIES_THIS_MONTH");
//		types.add("S_SERIES_TODAY");
//		types.add("S_SERIES_THIS_WEEK");
		types.add("S_SERIES_THIS_MONTH");
		paraMap.put("queryTypeList", types);
//        paraMap.put("page", "0");
//        paraMap.put("pageSize", "500");


		String paraStr = JSON.toJSONString(paraMap);
		logger.info(url.toString() + JSON_EQ + paraStr);
		url.append(JSON_EQ).append(URLEncoder.encode(paraStr, "UTF-8"));
		String result = HttpUtil.httpGet(url.toString());
		logger.info(result);
	}


    /**
     * 检查账户是否存在
     * @param t
     * @throws Exception
     */
    private void hasUser(TestHttpClient t) throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(t.getUseHeader());
        url.append(SERVICE_CHECK_LOGINID);

        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("loginId", "qqspace_c7fd2396aaea4c7b63bdfde661123455");
        String paraStr = JSON.toJSONString(paraMap);
        logger.info(url.toString() + JSON_EQ + paraStr);
        url.append(JSON_EQ).append(URLEncoder.encode(paraStr, "UTF-8"));
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
    }

    /**
     * testWeixin
     * @param t
     * @throws Exception
     */
    private void testWeixin(TestHttpClient t) throws Exception {
        String server = "http://ezontest.sinaapp.com/test?requestUrl=";
        String request = null;
        /*request = "https://api.weixin.qq.com/device/getqrcode?access_token=";
        request += "U_I1dnYsCAQzFZm9qOfLWc7d5jwUmeq-T7VmqvWeYCcZG6cm3dhgHWOBw5xyGfNjN-PLfjabdI-F-AUK3fftyr_arryp9-ed3gIvOeOrs-Q";*/
        request = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxa108b6a713cf30c7" +
                "&secret=246a27116c11ba275952e36f7d58193b";
        StringBuilder url = new StringBuilder();
        url.append(server);
        url.append(URLEncoder.encode(request, "UTF-8"));
        //url.append();

        logger.info(server + request );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
    }

    /**
     * 获取设备二维码
     * @param t
     */
    private void getQrcode(TestHttpClient t) throws Exception {
        StringBuilder url = new StringBuilder();
        String sidDes = getLoginSid(t, User_LoginId_1, User_Password_1);
        url.append(t.getUseHeader());
        url.append(SERVICE_QUERY_QRCODE).append(SID_EQ).append(sidDes);

        logger.info(url.toString() );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
    }

    /**
     * 获取accessToken
     * @param t
     * @throws Exception
     */
    public String queryToken(TestHttpClient t) throws Exception {
        StringBuilder url = new StringBuilder();
        String sidDes = getLoginSid(t, User_LoginId_5, User_Password_5);
        url.append(t.getUseHeader());
        url.append(SERVICE_QUERY_TOKEN).append(SID_EQ).append(sidDes);

        logger.info(url.toString() );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
        return result;
    }

    /**
     * 获取正式服务器的accessToken
     * @param t
     * @return
     */
    String getServerToken(TestHttpClient t){
        StringBuilder url = new StringBuilder();
        url.append("http://121.40.218.161/phone?service=common.tto");
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
        return result;
    }

    /**
     * 测试数据上传
     * @param t
     */
    public void testUpload(TestHttpClient t) throws UnsupportedEncodingException {
        StringBuilder url = new StringBuilder();
        url.append(t.getUseHeader());
        url.append(SERVICE_TEST_UPLOAD);

        logger.info(url.toString() );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);

    }

    /**
     * 创建群组
     * @param t
     * @throws Exception
     */
    public void createGroup(TestHttpClient t) throws Exception {
        GroupInfo g = new GroupInfo();
        g.setMaster(User_LoginId_1);
        g.setGroupDescription("这是32群组");
        g.setProvince("北京市");
        g.setCity("北京");
        g.setGroupName("跑步群21143412");
        g.setJoinType(0);
        g.setWatchType(0);
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("groupInfo",g);

        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_1, User_Password_1, SERVICE_CREATE_GROUP, paraMap));
        logger.info(result);
    }

    /**
     * 更新群组
     * @param t
     * @throws Exception
     */
    public void updateGroup(TestHttpClient t) throws Exception {
        GroupInfo g = new GroupInfo();
        g.setId(18);
        g.setGroupDescription("增加注释啊11");
        g.setProvince("北京市");
        g.setCity("北京");
        g.setGroupName("跑步群2133");
        g.setJoinType(1);
        g.setWatchType(1);
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("groupInfo",g);

        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_1, User_Password_1, SERVICE_UPDATE_GROUP_INFO, paraMap));
        logger.info(result);
    }

    /**
     * 获取指定服务的请求地址url
     * @param t
     * @param loginId 用户登录id
     * @param password 用户密码
     * @param serviceName 服务名称
     * @param obj 参数对象
     * @return
     * @throws Exception
     */
    public String getUrl(TestHttpClient t,String loginId,
                         String password,String serviceName,Object obj) throws Exception {
        StringBuilder url = new StringBuilder();
        String sidDes = getLoginSid(t, loginId, password);
        url.append(t.getUseHeader());
        url.append(serviceName).append(SID_EQ).append(sidDes);
        String paraStr = JSON.toJSONString(obj);
        logger.info(url.toString() + JSON_EQ + paraStr);
        url.append(JSON_EQ).append(URLEncoder.encode(paraStr,"UTF-8"));
        return url.toString();
    }

    /**
     * 用于common的请求url
     * @param t
     * @param serviceName 服务名称
     * @param obj 参数对象
     * @return
     */
    public String getUrlWithoutSid(TestHttpClient t,String serviceName,Object obj){
        StringBuilder url = new StringBuilder();
        url.append(t.getUseHeader()).append(serviceName);
        String paraStr = JSON.toJSONString(obj);
        logger.info(url.toString() + JSON_EQ + paraStr);
        try {
            url.append(JSON_EQ).append(URLEncoder.encode(paraStr,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        return url.toString();
    }

    public String getUseHeader() {
		return useHeader;
	}


	public void setUseHeader(String useHeader) {
		this.useHeader = useHeader;
	}

    /**
     * 加入群组申请
     * @param t
     * @throws Exception
     */
    public void joinGroup(TestHttpClient t) throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("groupId",43);
        paraMap.put("applicationReason","我要测试1");

        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_3, User_Password_3, SERVICE_JOIN_GROUP, paraMap));
        logger.info(result);
    }

    /**
     * 查询群组列表
     * @param t
     * @throws Exception
     */
    public void queryGroupList(TestHttpClient t) throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("queryType",3);
        paraMap.put("page",0);
        paraMap.put("pageSize",10);
        paraMap.put("province","福建");
        paraMap.put("city","厦门");
//        paraMap.put("likeText","11");

        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_5, User_Password_5, SERVICE_QUERY_GROUP_LIST, paraMap));
        logger.info(result);
    }

    /**
     * 群主审批入群请求
     * @param t
     * @throws Exception
     */
    public void verifyJoin(TestHttpClient t) throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("id",9);
        paraMap.put("result",1);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_1, User_Password_1, SERVICE_VERIFY_JOIN, paraMap));
        logger.info(result);
    }

    /**
     * 解散或退出群组
     * @param t
     * @throws Exception
     */
    public void leaveGroup(TestHttpClient t) throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("groupId",17);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_5, User_Password_5, SERVICE_LEAVE_GROUP, paraMap));
        logger.info(result);
    }

    /**
     *查询各种类型消息的数量
     * @param t
     * @throws Exception
     */
    public void queryAllMsgNum(TestHttpClient t) throws Exception {
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_5, User_Password_5, SERVICE_QUERY_ALL_MSG_NUM, null));
        logger.info(result);
    }

    /**
     * 查询各种类型消息
     * @param t
     * @throws Exception
     */
    public void queryAllMsg(TestHttpClient t) throws Exception {
        ArrayList<String> msgType = new ArrayList<String>();
        msgType.add("praiseAndcomments");
        msgType.add("joinMsg");
        msgType.add("sysNotice");
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("msgType",msgType);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_8, User_Password_8, SERVICE_QUERY_ALL_MSG, paraMap));
        logger.info(result);
    }

    /**
     * 查询群组成员信息
     * @param t
     * @throws Exception
     */
    public void queryGroupMembers(TestHttpClient t)throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("groupId",27);
        paraMap.put("pageSize",50);
        paraMap.put("page",0);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_1, User_Password_1, SERVICE_QUERY_GROUP_MEMBERS, paraMap));
        logger.info(result);
    }

    /**
     *查询具体群组信息
     * @param t
     * @throws Exception
     */
    public void queryGroupInfo(TestHttpClient t)throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("groupId",18);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_3, User_Password_3, SERVICE_QUERY_GROUP_INFO, paraMap));
        logger.info(result);
    }

    /**
     * 查询他人的动态信息
     * @param t
     * @throws Exception
     */
    public void queryZone(TestHttpClient t)throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("loginId","15112588036");
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_3, User_Password_3, SERVICE_QUERY_ZONE, paraMap));
        logger.info(result);
    }

    /**
     * 查询他人的动态信息（带图片）
     * @param t
     * @throws Exception
     */
    public void queryOthersNewestShare(TestHttpClient t)throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("loginId",User_LoginId_5);
        paraMap.put("pageSize",5);
        paraMap.put("page",0);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_3, User_Password_3, SERVICE_QUERY_OTHERS_NEWEST_SHARE, paraMap));
        logger.info(result);
    }

    /**
     * 查询群组排行榜
     * @param t
     * @throws Exception
     */
    public void queryGroupRankingList(TestHttpClient t)throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        ArrayList<String> types = new ArrayList<String>();
        types.add("G_SERIES_TODAY");
        types.add("G_SERIES_THIS_WEEK");
        types.add("G_SERIES_THIS_MONTH");
        /*types.add("S_SERIES_TODAY");
        types.add("S_SERIES_THIS_WEEK");
        types.add("S_SERIES_THIS_MONTH");*/
        paraMap.put("queryTypeList", types);
        paraMap.put("groupId",38);
        paraMap.put("page",0);
        paraMap.put("pageSize",50);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_6, User_Password_6, SERVICE_QUERY_GROUP_RANKING_LIST, paraMap));
        logger.info(result);
    }

    /**
     * 查询他人的历史统计数据
     * @param t
     * @throws Exception
     */
    public void queryPersonStatInfo(TestHttpClient t)throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("loginId","13991220556");
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_3, User_Password_3, SERVICE_QUERY_PERSON_STAT_INFO, paraMap));
        logger.info(result);
    }

    /**
     * 向sms-sdk服务端验证短信
     * @param t
     */
    public void checkSMS(TestHttpClient t) throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("phone","13991220556");
        paraMap.put("zone","86");
        paraMap.put("code","6475");
        String result = HttpUtil.httpGet(getUrlWithoutSid(t,SERVICE_CHECK_SMS,paraMap));
        logger.info(result);
    }

    /**
     * 查询指定loginId的相关微信绑定情况
     * @param t
     * @throws Exception
     */
    public void checkBind(TestHttpClient t)throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("loginId","13991220556");
        String result = HttpUtil.httpGet(getUrlWithoutSid(t,SERVICE_CHECK_BIND,paraMap));
        logger.info(result);
    }

    private void testScheduler(TestHttpClient t) throws Exception {
        StringBuilder url = new StringBuilder();
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("run","12");

        String result = HttpUtil.httpGet(getUrlWithoutSid(t,SERVICE_EXEC_TASK,paraMap));
        logger.info(result);

    }

    /**
     * 发朋友圈帖子
     * @param t
     * @throws Exception
     */
    public void shareByClient(TestHttpClient t) throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("remark","艹我就是要发帖子test");
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_5, User_Password_5, SERVICE_SHARE_BY_CLIENT, paraMap));
        logger.info(result);
    }


    public void delPicRelation(TestHttpClient t) throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("id",2);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_5, User_Password_5, SERVICE_DEL_PICRELATION, paraMap));
        logger.info(result);
    }

    public void queryPicRelations(TestHttpClient t) throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("watchId",189);
        paraMap.put("relId",35);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_5, User_Password_5, SERVICE_QUERY_PICRELATIONS, paraMap));
        logger.info(result);
    }

    /**
     * 对水印图片的评论的增加、删除
     * @param t
     * @throws Exception
     */
    public void sharePicComment(TestHttpClient t) throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("picId",21);
        paraMap.put("loginId",User_LoginId_5);
        paraMap.put("content","我就说嘛");
        paraMap.put("action",1);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_5, User_Password_5, SERVICE_SHARE_PIC_COMMENT, paraMap));
        logger.info(result);
    }

    /**
     * 查询水印图片详情，包括点赞和评论
     * @param t
     * @throws Exception
     */
    public void queryPicDetail(TestHttpClient t) throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("picId",1);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_5, User_Password_5, SERVICE_QUERY_PIC_DETAIL, paraMap));
        logger.info(result);
    }

    /**
     * 对水印图片的点赞的增加、删除
     * @param t
     * @throws Exception
     */
    public void sharePicPraise(TestHttpClient t) throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("picId",3);
        paraMap.put("loginId",User_LoginId_5);
        paraMap.put("action",0);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_5, User_Password_5, SERVICE_SHARE_PIC_PRAISE, paraMap));
        logger.info(result);
    }

    public void feedback(TestHttpClient t) throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("type","用户问题");
        paraMap.put("content","卡得不行呀");
        paraMap.put("action",1);
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_5, User_Password_5, SERVICE_FEEDBACK, paraMap));
        logger.info(result);
    }


    /**
     * 获取日志
     * @param t
     * @throws Exception
     */
    public void printLog(TestHttpClient t)throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(t.getUseHeader());
        url.append(SERVICE_READ_TEST_LOG);
        logger.info(url.toString() );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
    }

    public void queryStatByDate(TestHttpClient t) throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(t.getUseHeader()).append(SERVICE_QUERY_STAT_BY_DATE);
        url.append("&mode=").append("周");
        url.append("&openid=").append("o3OHZwooFReQFiFYS8rKsXIthIg4");
        url.append("&queryDate=").append("160529");
//        url.append("&preDay=").append(true);
//        url.append("&nextDay=").append(true);

        logger.info(url.toString() );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);

    }

    /**
     * 扣除积分接口
     * @param t
     */
    public void deductCredits(TestHttpClient t) throws Exception {
        StringBuilder url = new StringBuilder();
        url.append("http://localhost:8080/credit/deductCredits?");
        String uid = User_Id_7;
        String credits = "10";
        String appKey = "credit_app_key";
        Long timestamp = System.currentTimeMillis();
        String orderNum = "bz12341";
        String type = "turntable";
        String actualPrice = "2";
        String sign = DigestUtils.md5Hex(uid + credits + "credit_app_secret" + appKey  + timestamp +
                orderNum + type + actualPrice);

        logger.info("待加密字符串=" + uid + credits + appKey + "credit_app_secret" + timestamp +
                orderNum + type + actualPrice);

        url.append("uid=").append(uid);
        url.append("&credits=").append(credits);
        url.append("&appKey=").append(appKey);
        url.append("&timestamp=").append(timestamp);
        url.append("&orderNum=").append(orderNum);
        url.append("&type=").append(type);
        url.append("&actualPrice=").append(actualPrice);
        url.append("&sign=").append(sign);

        logger.info(url.toString() );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
    }

    /**
     * 接收兑换结果通知
     * @param t
     */
    public void exchangeResult(TestHttpClient t) throws Exception {
        StringBuilder url = new StringBuilder();
        url.append("http://localhost:8080/credit/exchangeResult?");

        String appKey = "credit_app_key";
        Long timestamp = System.currentTimeMillis();
        String orderNum = "bz12341";
        String success = "false";

        String sign = DigestUtils.md5Hex(appKey +  "credit_app_secret"   + timestamp +
                orderNum + success );

        logger.info("待加密字符串=" + appKey + "credit_app_secret" + timestamp +
                orderNum + success );


        url.append("appKey=").append(appKey);
        url.append("&timestamp=").append(timestamp);
        url.append("&orderNum=").append(orderNum);
        url.append("&success=").append(success);
        url.append("&sign=").append(sign);

        logger.info(url.toString() );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
    }

    /**
     * 生成免登录url
     * @param t
     * @throws Exception
     */
    public void getAutoLoginUrl(TestHttpClient t)throws Exception {
        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_7, User_Password_7,
                SERVICE_GET_AUTOLOGIN_URL, null));
        logger.info(result);
    }

    /**
     * 批量授权并生成二维码图像
     * @param t
     * @throws Exception
     */
    public void batchProduceDeviceQcode(TestHttpClient t)throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(t.getUseHeader());
        url.append(SERVICE_BATCH_PRODUCE_DEVICE_QCODE);
        url.append("&num=2000");
        url.append("&type=F1");
        url.append("&pwd=ezonqazwsx");
        logger.info(url.toString() );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
    }

    //查询设备二维码表属性
    public void queryDeviceRelQrcode(TestHttpClient t)throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(t.getUseHeader());
        url.append(SERVICE_QUERY_DEVICE_REL_QRCODE);
//        url.append("&deviceId=S1_000003");
//        url.append("&mac=");
        url.append("&qrcode=http://we.qq.com/d/AQDc4dnCTJHTr1UMhnQ-06vzCauooSpLR23pyTus");
        logger.info(url.toString() );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
    }

    /**
     * 查询S1同步信息
     * @param t
     * @throws Exception
     */
    public void getSyncinfo(TestHttpClient t)throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(t.getUseHeader());
        url.append(SERVICE_GET_SYNCINFO);
        url.append("&openid=o3OHZwnTR9Ak-dgv1dmr5dfSCrfM");
        logger.info(url.toString() );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
    }

    /**
     * 分配mac
     * @param t
     * @throws Exception
     */
    public void assignMac(TestHttpClient t)throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(t.getUseHeader());
        url.append(SERVICE_ASSIGN_MAC);
        url.append("&mac=f0c77f159e4f");
        url.append("&deviceId=S1_000010");
        url.append("&cover=0");
        url.append("&pwd=ezonqazwsx");
        logger.info(url.toString() );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
    }

    public void checkPwd(TestHttpClient t)throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(t.getUseHeader());
        url.append(SERVICE_CHECK_PWD);
        url.append("&pwd=ezonqazwsx1");
        logger.info(url.toString() );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
    }

    //模拟微信消息，绑定手表
    public void bindWatch(TestHttpClient t) {
        StringBuilder paramJson = new StringBuilder();
        paramJson.append("{\"device_id\":\"112FFFFEC\",\"device_type\":\"gh_7ea73f1265ff\"," +
                "\"msg_id\":74398619,\"msg_type\":\"bind\",\"create_time\":1467614000," +
                "\"open_id\":\"o3OHZwhdtAqOHg2CqkSEZpESEGj8\",\"session_id\":0," +
                "\"content\":\"\",\"qrcode_suffix_data\":\"\"}");
        logger.info(paramJson.toString() );
        String result = HttpUtil.executePost(LOCAL_WEIXIN_HEADER,paramJson.toString());
        logger.info(result);
    }

    //更新微信手表类型
    public void udpateWeixinWatchType(TestHttpClient t) {
        byte[] command = new byte[]{0x45,0x5A,0x4F,0x4e,0x00,0x00,0x00,0x09,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};

        DeviceMsgJson json = new DeviceMsgJson("F2S_FFFFEC","gh_7ea73f1265ff",74398619,
                "device_text",1468200051,"o3OHZwhdtAqOHg2CqkSEZpESEGj8",0,
                org.apache.commons.codec.binary.Base64.encodeBase64String(command),"");
        logger.info(JSONObject.toJSONString(json));
        String result = HttpUtil.executePost(LOCAL_WEIXIN_HEADER,JSONObject.toJSONString(json));
        logger.info(result);
    }

    //根据mac查询设备列表
    public void queryDeviceListByMac(TestHttpClient t)throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(t.getUseHeader());
        url.append(SERVICE_QUERY_DEVICE_LIST_BY_MAC);
        url.append("&mac=7cec795761e1");
        logger.info(url.toString() );
        String result = HttpUtil.httpGet(url.toString());
        logger.info(result);
    }

    //添加，删除绑定关系
    public void userWatch(TestHttpClient t)throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("userId","aff7fbe0-36c4-417e-a2fa-8970a9f50644");
        paraMap.put("uuid", "AGFFHBCBMI1KCPBI");
        paraMap.put("name", "测试1125");
        paraMap.put("action","del");
        paraMap.put("id","110702");

        String result = HttpUtil.httpGet(getUrl(t, "13489992052", "c33367701511b4f6020ec61ded352059", SERVICE_USERWATCH, paraMap));
        logger.info(result);
    }

    //查询天气
    public void getWeather(TestHttpClient t)throws Exception {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("cityId","CN101310214");

        String result = HttpUtil.httpGet(getUrl(t, User_LoginId_5, User_Password_5, SERVICE_GET_WEATHER, paraMap));
        logger.info(result);
    }

	
	public static void main(String[] args) {
		TestHttpClient t = new TestHttpClient();
		//本地调试路径
//        t.setUseHeader(LOCAL_Header);
//        t.setUseHeader(LOCAL_SYNC_Header);
		//服务器正式路径
        t.setUseHeader(PRODUCE_Header);
        //微信测试服务器路径
        //t.setUseHeader(Weixin_Header);
		try {
			//模拟登录
//            t.login(t);
			
			//调用服务
//            t.userWatch(t);
//            t.queryDeviceListByMac(t);
//            t.udpateWeixinWatchType(t);
//            t.checkPwd(t);
//            t.bindWatch(t);
//            t.deductCredits(t);
//            t.exchangeResult(t);
//            t.getAutoLoginUrl(t);
//            t.assignMac(t);
//            t.getSyncinfo(t);
//            t.batchProduceDeviceQcode(t);
//            t.queryDeviceRelQrcode(t);
//            t.queryToken(t);
//              t.queryStatByDate(t);
//            t.testScheduler(t);
//            t.loginByDeviceUuid(t);
//            t.checkSMS(t);
//            t.checkBind(t);
//            t.queryPersonStatInfo(t);
//            t.queryGroupRankingList(t);
//            t.queryZone(t);
//            t.queryGroupInfo(t);
//            t.queryGroupMembers(t);
//            t.queryAllMsg(t);
//            t.queryAllMsgNum(t);
//            t.leaveGroup(t);
//            t.verifyJoin(t);
            //t.updateGroup(t);
//            t.queryGroupList(t);
//            t.joinGroup(t);
            //t.createGroup(t);

//            t.printLog(t);

//            t.getServerToken(t);
            //t.getQrcode(t);
            //t.testWeixin(t);
//            t.queryRankingList(t);
			//t.sendPasswordEmail(t);
//			t.queryPicInfoGzip(t);
//			t.queryPicInfo(t);
			//t.updateViewTime(t);
			//t.queryNewMsgTip(t);
//			t.getShareCount(t);
			//t.resetPassword(t);
			//t.queryUserPhoto(t);
			//t.updateUser(t);
//			t.syncformServiceZip(t);
//			t.queryNewMyShare(t);
//            t.queryNewShareInfo(t);
			//t.querySortedShareInfo(t);
//			t.shareComment(t);
            //t.hasUser(t);
//            t.login(t);
//            t.testUpload(t);
//			t.queryWeixinMenu(t);
//            t.shareByClient(t);
//            t.delPicRelation(t);
//            t.queryPicRelations(t);
//            t.sharePicComment(t);
//            t.sharePicPraise(t);
//            t.queryPicDetail(t);
//            t.syncTableGzip(t);
//            t.queryOthersNewestShare(t);
//            t.feedback(t);
            t.getWeather(t);



		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}
