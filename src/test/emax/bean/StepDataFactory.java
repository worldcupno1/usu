package test.emax.bean;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 记步数据工厂类
 * Created by lvm on 2016/4/12.
 */
public class StepDataFactory {
    private static Logger log = LogManager.getLogger(StepDataFactory.class);

    static String SERVICE_NAME = "sync.syncTable";
    static String TABLE_NAME = "step_count";
    static String[] fields = new String[]{"day","endTime","sportGoal","startTime",
            "stepDetail","timeZone","totalKcal","totalMetre","totalMinute",
            "totalStep","watchId"};

    public static String produceJsonParam(List<List<String>> l){
        Map<String ,Object> map = new HashMap<String,Object>();
        map.put("items",l);
        map.put("tableName",TABLE_NAME);
        map.put("fields",fields);
        //新增needServerId
        map.put("needServerId",1);
        return JSONObject.toJSONString(map);
    }

    public static String produceStepData(String sid ,String jsonStr){
        Map<String ,Object> map = new HashMap<String,Object>();
        map.put("service",SERVICE_NAME);
        map.put("sid",sid);
        map.put("json",jsonStr);
        return JSONObject.toJSONString(map);
    }

    public static String produceDefaultStepDetail(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 1440 ; i++) {
            str.append("0").append(",");
        }
        if (str.toString().endsWith(",")){
            str.setLength(str.length() - 1);
        }
        return str.toString();
    }

    public static List<String> produceOneRecord(String day,String endTime,
            String sportGoal,String startTime,String stepDetail,
            String timeZone,String totalKcal,String totalMetre,
            String totalMinute,String totalStep,String watchId){
        List<String> record = new ArrayList<String>();
        record.add(day);
        if (StringUtils.isBlank(endTime)){
            record.add("0000");
        }else {
            record.add(endTime);
        }
        if (StringUtils.isBlank(sportGoal)){
            record.add("0");
        }else {
            record.add(sportGoal);
        }
        if (StringUtils.isBlank(startTime)){
            record.add("0000");
        }else {
            record.add(startTime);
        }
        if (StringUtils.isBlank(stepDetail)){
            record.add(produceDefaultStepDetail());
        }else {
            record.add(stepDetail);
        }
        if (StringUtils.isBlank(timeZone)){
            record.add("480");
        }else {
            record.add(timeZone);
        }
        if (StringUtils.isBlank(totalKcal)){
            record.add("0");
        }else {
            record.add(totalKcal);
        }
        if (StringUtils.isBlank(totalMetre)){
            record.add("0");
        }else {
            record.add(totalMetre);
        }

        if (StringUtils.isBlank(totalMinute)){
            record.add("0");
        }else {
            record.add(totalMinute);
        }
        if (StringUtils.isBlank(totalStep)){
            record.add("0");
        }else {
            record.add(totalStep);
        }

        if (StringUtils.isBlank(watchId)){
            record.add("没有watchId");
        }else {
            record.add(watchId);
        }

        return record;
    }

    public static void main(String[] args) {
        StepDataFactory factory = new StepDataFactory();
        List l = new ArrayList();
        l.add(produceOneRecord("160304",null,null,null,
                produceDefaultStepDetail(),null,"10","100","1","97","10802"));

//        log.info(JSONObject.toJSONString(fields));
        log.info(produceStepData("sid=",produceJsonParam(l)));
    }
}
