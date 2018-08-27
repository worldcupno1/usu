package collection;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import useful.bean.Employee;

import java.util.*;

/**
 * 集合类的各种方法测试
 * Created by lvm on 2016/3/3.
 */
public class Example {
    public static Logger log = LogManager.getLogger(Example.class);


    /**
     * guava比较器链ComparisonChain的使用
     */
    @Test
    public void testGuavaOrdering(){
        Employee e1 = new Employee();
        e1.setFullname("雇员1");
        e1.setTotalScore(100);
        e1.setTotalStep(6000);
        Employee e2 = new Employee();
        e2.setFullname("雇员2");
        e2.setTotalScore(600);
        e2.setTotalStep(1000);
        Employee e3 = new Employee();
        e3.setFullname("雇员3");
        e3.setTotalScore(600);
        e3.setTotalStep(5000);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(e1);
        employeeList.add(e2);
        employeeList.add(e3);
        Collections.sort(employeeList);
        log.info(e1.compareTo(e2));
        log.info("list: " + employeeList);
    }

    /**
     * 字符串list排序
     */
    @Test
    public void testStaticOrdering(){
        List<String> list = Lists.newArrayList();
        list.add("peida");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("jhon");
        list.add("neron");
        log.info("list:"+ list);

        //使用Comparable类型的自然顺序， 例如：整数从小到大，字符串是按字典顺序
        Ordering<String> naturalOrdering = Ordering.natural();
        //使用toString()返回的字符串按字典顺序进行排序
        Ordering<Object> usingToStringOrdering = Ordering.usingToString();
        //返回一个所有对象的任意顺序， 即compare(a, b) == 0 就是 a == b (identity equality)。
        // 本身的排序是没有任何含义， 但是在VM的生命周期是一个常量。
        Ordering<Object> arbitraryOrdering = Ordering.arbitrary();

        log.info("naturalOrdering:"+ naturalOrdering.sortedCopy(list));
        log.info("usingToStringOrdering:"+ usingToStringOrdering.sortedCopy(list));
        log.info("arbitraryOrdering:"+ arbitraryOrdering.sortedCopy(list));
    }

    /**
     * 集合的交集,并集,差集
     */
    @Test
    public void intersect(){
        List l1 = new ArrayList();
        l1.add("160102");
        l1.add("160103");
        l1.add("160104");
        l1.add("160105");
        List l2 = new ArrayList();
        l2.add("160104");
        l2.add("160105");
        l2.add("160106");
        l2.add("160107");
        List intersectList = intersect(l1, l2);
        log.info("交集：");
        for (int i = 0; i < intersectList.size(); i++) {
            log.info(intersectList.get(i) + " ");
        }

        List unionList = union(l1, l2);
        log.info("并集：");
        for (int i = 0; i < unionList.size(); i++) {
            log.info(unionList.get(i) + " ");
        }
        System.out.println();
        List diffList = diff(l1, l2);
        log.info("差集：");
        for (int i = 0; i < diffList.size(); i++) {
            log.info(diffList.get(i) + " ");
        }
    }

    /**
     * 交集
     * @param ls
     * @param ls2
     * @return
     */
    public List intersect(List ls, List ls2) {
        List list = new ArrayList(Arrays.asList(new Object[ls.size()]));
        Collections.copy(list, ls);
        list.retainAll(ls2);
        return list;
    }

    /**
     * 并集
     * @param ls
     * @param ls2
     * @return
     */
    public List union(List ls, List ls2) {
        List list = new ArrayList(Arrays.asList(new Object[ls.size()]));
        Collections.copy(list, ls);
        list.addAll(ls2);
        return list;
    }

    /**
     * 差集
     * @param ls
     * @param ls2
     * @return
     */
    public List diff(List ls, List ls2) {
        List list = new ArrayList(Arrays.asList(new Object[ls.size()]));
        Collections.copy(list, ls);
        list.removeAll(ls2);
        return list;
    }


    /**
     * aslist方法
     */
    @Test
    public void testAslist(){
        String[] dateList = new String[10];
        dateList[0] = "160122";
        dateList[1] = "160124";

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(dateList));
        log.info(Arrays.asList(dateList).size());
        log.info(Arrays.asList(dateList).get(0));
        assert "160124".equals(arrayList.get(1));
    }

    /**
     * ToArray方法
     */
    @Test
    public void testToArray(){
        ArrayList<Integer> arrlist = new ArrayList<Integer>();
        arrlist.add(0);
        arrlist.add(56);
        arrlist.add(94);
        Integer list2[] = new Integer[arrlist.size()];
        list2 = arrlist.toArray(list2);

        log.info(list2.length);
        assert list2[2] == 94;
    }
}
