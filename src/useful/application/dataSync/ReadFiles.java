package useful.application.dataSync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class ReadFiles {
	public void dataReader(String nameFile, int start, int finish) {
		if (start > finish) {
			System.out.println("Error start or finish!");
			return;
		}
		InputStream inputStream = null;
		LineNumberReader reader = null;
		try {
			inputStream = new FileInputStream(new File(nameFile));
			reader = new LineNumberReader(
					new InputStreamReader(inputStream,"GBK"));
			int lines = getTotalLines(new File(nameFile));
			if (start < 0 || finish < 0 || finish > lines || start > lines) {
				System.out.println("Line not found!");
				return;
			}
			
			String line = reader.readLine();
	        lines = 0;
	        while (line != null) {
	            lines++;
	            if(lines >= start && lines <= finish){
	            	//处理你读到的内容
	            	System.out.println(line);
	            	System.out.println(line.substring(6, 7));
	            	//记录当前行数
		            System.out.println("读到第:"+reader.getLineNumber()+"行");
	            }
	            line = reader.readLine();
	        }
	        inputStream.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Error");
			System.exit(0);
		}

	}
	
	private int getTotalLines(File file) throws IOException{
		FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        String line = reader.readLine();
        int lines = 0;
        while (line != null) {
            lines++;
            line = reader.readLine();
        }
        reader.close();
        in.close();
        return lines;
	}

	public static void main(String[] args) {
		//读第二行和第四行的信息。
		new ReadFiles().dataReader("F:/download/hrInfo/LDAPM_MPW_BD_PSNDOC_#590_M_150430_W_NM_F1.ITF", 2, 4);
		//new Ex7().dataReader("data.txt",3,8);
	}
}

