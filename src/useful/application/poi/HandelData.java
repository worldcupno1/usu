/**
 * 处理excel文件，
 */
package useful.application.poi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 * @author lvm
 *
 */
public class HandelData {
	private static Logger logger = LogManager.getLogger();
	private static int dateColNum = 3;
	private static int writeCol = 5;
	private static int dataStartRowNum = 1;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExcelReader excel = new ExcelReader("E:/工作文档/excel/2.xlsx");
		int sheetIndex = excel.wb.getSheetIndex("排名");
		logger.info("开始处理");
		/*List<String[]> all =  excel.getAllData(excel.wb.getSheetIndex("排名"));
		for (int i = 0; i < all.size() ; i++){
			for (int j = 0; j < all.get(i).length; j++){
				logger.info(all.get(i)[j]);
			}
		}*/
		excel.getAllData(sheetIndex);
		String[] dateCol = excel.getColumnData(sheetIndex, dateColNum);
		int total  = excel.getRowNum(sheetIndex);
		String dateStr = "150801";
		int rank = dataStartRowNum;
		XSSFRow row = null;
		XSSFCell cell = null;
		for (int j = 1; j <= total; j++){
			row = (XSSFRow) excel.wb.getSheetAt(sheetIndex).getRow(j);
			cell = row.createCell(writeCol);
			if (dateStr.equals(dateCol[j])){
				cell.setCellValue(rank++);
			}else {
				dateStr = dateCol[j];
				rank = dataStartRowNum;
				cell.setCellValue(rank++);
			}
		}

		try {
			FileOutputStream fileoutputstream = new FileOutputStream("E:/工作文档/excel/3.xlsx");
			excel.wb.write(fileoutputstream);
			fileoutputstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	    
		
		logger.info("处理完毕");
	}

}
