package bean;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import test.emax.bean.GroupInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**关于java bean
 * Created by lvm on 2016/4/6.
 */
public class Example {
    public static Logger log = LogManager.getLogger(Example.class);

    /**
     * object转化map
     */
    @Test
    public void testBeanMap(){
        GroupInfo g = new GroupInfo();
        g.setCity("福州");
        g.setGroupName("群组");
        g.setId(1423);
        Map<?,?> map = null;
        map =  new BeanMap(g);

        log.info(map.get("id"));
    }

    /**
     * map转bean
     */
    @Test
    public void map2Bean(){
        HashMap<String,Object> m = new HashMap<String,Object>();
        m.put("city","三明");
        m.put("id","984");
        m.put("groupName",123);

        GroupInfo g = new GroupInfo();
        try {
            BeanUtils.populate(g,m);
            log.info(g.getCity());
            log.info(g.getId());
            log.info(g.getGroupName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
