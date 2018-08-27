/**
 * 职员的比较器
 * 
 */
package useful.bean;

import java.util.Comparator;

/**
 * @author lvm
 *
 */
public class EmployeeComparable implements Comparator<Employee>{
	
	// 对象的排序方式[升、降]   
    public static boolean sortASC = true;   
  
    // 对象的排序属性   
    public static boolean sortByTotalStep = false;   
    public static boolean sortByTotalScore = false;   


	@Override
	public int compare(Employee o1, Employee o2) {
		int result = 0;
		if (sortASC){
			if (sortByTotalStep){
				Integer o1TotalStep = o1.getTotalStep(); 
				Integer o2TotalStep = o2.getTotalStep(); 
				result = o1TotalStep.compareTo(o2TotalStep);
			}
			
			if (sortByTotalScore){
				Integer o1TotalScore = o1.getTotalScore(); 
				Integer o2TotalScore = o2.getTotalScore(); 
				result = o1TotalScore.compareTo(o2TotalScore);
			}
			
		}else {
			if (sortByTotalStep){
				Integer o1TotalStep = o1.getTotalStep(); 
				Integer o2TotalStep = o2.getTotalStep(); 
				result = o2TotalStep.compareTo(o1TotalStep);
			}
			
			if (sortByTotalScore){
				Integer o1TotalScore = o1.getTotalScore(); 
				Integer o2TotalScore = o2.getTotalScore(); 
				result = o2TotalScore.compareTo(o1TotalScore);
			}
		}
		
		return result;
	}

}
