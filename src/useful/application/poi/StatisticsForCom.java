/**
 * 统计，排序
 */
package useful.application.poi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import useful.bean.Employee;
import useful.bean.EmployeeComparable;

/**
 * @author lvm
 *
 */
public class StatisticsForCom {
	private static Logger log = LogManager.getLogger();

	SimpleDateFormat f2 = new SimpleDateFormat("MM");
	private static String dataPath = "E:/工作文档/excel/每月数据.xlsx";
	
	private static int SCORE_3_MIN_STEP = 20000;
	private static int SCORE_2_MIN_STEP = 10000;
	private static int SCORE_1_MIN_STEP = 1;
	
	private static int SCORE_3 = 3;
	private static int SCORE_2 = 2;
	private static int SCORE_1 = 1;
	private static int SCORE_0 = 0;
	
	private int allSteps = 0;
	private int allScores = 0;
	
	/**
	 * 计算步数得分
	 * @param stepNum
	 * @return
	 */
	public  Integer getScore(int stepNum){
		if (SCORE_3_MIN_STEP <= stepNum){
			return SCORE_3;
		}else if (SCORE_3_MIN_STEP > stepNum && SCORE_2_MIN_STEP <= stepNum){
			return SCORE_2;
		}else if (SCORE_2_MIN_STEP > stepNum && SCORE_1_MIN_STEP <= stepNum){
			return SCORE_1;
		}else {
			return SCORE_0;
		}
	}
	
	public void doWork(){
		//获取上个月的月份
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		String monthStr = f2.format(c.getTime());
		//log.debug("月份是" + f2.format(c.getTime()));
		log.info("开始初始化当月数据。");
		//初始化当月数据
		ExcelReader excel = new ExcelReader(dataPath);
		int sheetIndex = excel.wb.getSheetIndex("人员");
		ArrayList<String[]> personData =
				(ArrayList<String[]>) excel.getAllData(sheetIndex);
		log.info("初始化人员数据。");
		ArrayList<Employee> eList = new ArrayList<Employee>();
		ArrayList<String> deptList = new ArrayList<String>();
		String[] tmp = null;
		int stepTmp = 0;
		int scoreTmp = 0;
		String phone = null;
		Employee e = null;
		for (int i = 1; i < personData.size() ;i++){
			tmp = personData.get(i);
			if (StringUtils.isNotBlank(tmp[0]) && tmp[0].length() > 6){
				e  = new Employee();
				e.setPhone(tmp[0].trim());
				e.setDept(tmp[1].trim());
				e.setFullname(tmp[2].trim());
				eList.add(e);
				if (!deptList.contains(e.getDept())){
					deptList.add(e.getDept());
				}
			}
		}
		
		log.info("初始化跑步数据。");
		sheetIndex = excel.wb.getSheetIndex("排名");
		excel.setDataList(new ArrayList<String[]>(3000));
		ArrayList<String[]> stepData =  (ArrayList<String[]>) excel.getAllData(sheetIndex);
		int[] socreArray = new int[stepData.size()];
		for (int i = 1; i < stepData.size() ;i++){
			tmp = stepData.get(i);
			if (StringUtils.isNotBlank(tmp[0])){
				for (int j = 0; j < eList.size(); j++){
					e = eList.get(j);
					phone = e.getPhone();
					if (phone.equals(tmp[2]) || phone.equals(tmp[3])){
						e.setLoginId(tmp[2].trim());
						e.setNickname(tmp[1].trim());
						e.getWalkDate().add(tmp[4].trim());
						stepTmp = Integer.parseInt(tmp[0].trim());
						scoreTmp = getScore(stepTmp);
						socreArray[i] = scoreTmp;
						e.getSetpCount().add(stepTmp);
						e.getSetpScore().add(scoreTmp);
						e.setTotalScore(e.getTotalScore() + scoreTmp);
						e.setTotalStep(e.getTotalStep() + stepTmp);
						break;
					}
				}
			}
		}
		log.info("初始化完毕，开始生成统计数据");
		log.info("生成单日积分排行统计。");
		int total  = socreArray.length;
		int writeCol = 5;
		XSSFRow row = null;
		XSSFCell cell = null;
		//加标题
		row = (XSSFRow) excel.wb.getSheetAt(sheetIndex).getRow(0);
		cell = row.createCell(writeCol);
		cell.setCellValue("积分");
		for (int i = 1; i < total; i++){
			if (StringUtils.isNotBlank(stepData.get(i)[0])){
				row = (XSSFRow) excel.wb.getSheetAt(sheetIndex).getRow(i);
				cell = row.createCell(writeCol);
				cell.setCellValue(socreArray[i]);
			}
			
		}
		
		log.info("生成单月总积分排行统计。");
		EmployeeComparable cp = new EmployeeComparable();
		//降序
		EmployeeComparable.sortASC = false;
		//设置排序属性生效 
		EmployeeComparable.sortByTotalScore = true;
		//进行排序
		Collections.sort(eList , cp); 
		
		//加标题
		row = (XSSFRow) excel.wb.getSheetAt(sheetIndex).getRow(0);
		cell = row.createCell(writeCol + 2);
		cell.setCellValue(monthStr + "月总积分排行");
		row = (XSSFRow) excel.wb.getSheetAt(sheetIndex).getRow(1);
        cell = row.createCell(writeCol + 2);
        cell.setCellValue("部门");
		cell = row.createCell(writeCol + 3);
		cell.setCellValue("姓名");
		cell = row.createCell(writeCol + 4);
		cell.setCellValue("积分排行");
		
		int i = 2;
		for(Iterator<Employee>  iter = eList.iterator(); iter.hasNext();){ 
			Employee emp = (Employee)iter.next();
			row = (XSSFRow) excel.wb.getSheetAt(sheetIndex).getRow(i++);
            cell = row.createCell(writeCol + 2);
            cell.setCellValue(emp.getDept());
			cell = row.createCell(writeCol + 3);
			cell.setCellValue(emp.getFullname());
			cell = row.createCell(writeCol + 4);
			cell.setCellValue(emp.getTotalScore());
		}
		
		
		log.info("生成单月部门积分排行。");
		//加标题
		row = (XSSFRow) excel.wb.getSheetAt(sheetIndex).getRow(0);
		cell = row.createCell(writeCol + 6);
		cell.setCellValue(monthStr + "月部门积分排行");
		row = (XSSFRow) excel.wb.getSheetAt(sheetIndex).getRow(1);
		cell = row.createCell(writeCol + 6);
		cell.setCellValue("部门");
		cell = row.createCell(writeCol + 7);
		cell.setCellValue("姓名");
		cell = row.createCell(writeCol + 8);
		cell.setCellValue("积分排行");
		ArrayList<Employee> deptTemp = null;
		int u = 2;
		for (i = 0; i < deptList.size() ;i++){
			deptTemp = new ArrayList<Employee>();
			for (int k = 0; k < eList.size() ;k++){
				if (eList.get(k).getDept().equals(deptList.get(i))){
					deptTemp.add(eList.get(k));
				}
			}
			//部门内排序
			Collections.sort(deptTemp , cp);
			
			for(Iterator<Employee>  iter = deptTemp.iterator(); iter.hasNext();){ 
				Employee emp = (Employee)iter.next();
				row = (XSSFRow) excel.wb.getSheetAt(sheetIndex).getRow(u++);
				cell = row.createCell(writeCol + 6);
				cell.setCellValue(emp.getDept());
				cell = row.createCell(writeCol + 7);
				cell.setCellValue(emp.getFullname());
				cell = row.createCell(writeCol + 8);
				cell.setCellValue(emp.getTotalScore());
			}
		}
		
		
		
		log.info("生成单月总步数排行统计。");
		//设置排序属性生效 
		EmployeeComparable.sortByTotalScore = false;
		EmployeeComparable.sortByTotalStep = true;
		//进行排序
		Collections.sort(eList , cp); 
		
		//加标题
		row = (XSSFRow) excel.wb.getSheetAt(sheetIndex).getRow(0);
		cell = row.createCell(writeCol + 10);
		cell.setCellValue(monthStr + "月总步数排行");
		row = (XSSFRow) excel.wb.getSheetAt(sheetIndex).getRow(1);
        cell = row.createCell(writeCol + 10);
        cell.setCellValue("部门");
		cell = row.createCell(writeCol + 11);
		cell.setCellValue("姓名");
		cell = row.createCell(writeCol + 12);
		cell.setCellValue("总步数");
		i = 2;
		for(Iterator<Employee>  iter = eList.iterator(); iter.hasNext();){ 
			Employee emp = (Employee)iter.next();
			row = (XSSFRow) excel.wb.getSheetAt(sheetIndex).getRow(i++);
            cell = row.createCell(writeCol + 10);
            cell.setCellValue(emp.getDept());
			cell = row.createCell(writeCol + 11);
			cell.setCellValue(emp.getFullname());
			cell = row.createCell(writeCol + 12);
			cell.setCellValue(emp.getTotalStep());
		}

		
		try {
			FileOutputStream fileoutputstream =
					new FileOutputStream("E:/工作文档/excel/" + monthStr + "月统计.xlsx");
			excel.wb.write(fileoutputstream);
			fileoutputstream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		log.info("完成统计，生成文件。");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StatisticsForCom stat = new StatisticsForCom();
		stat.doWork();
	}

}
