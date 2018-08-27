package learn;

/**
 * Created by lvm on 2016/7/7.
 */
public class Generic {
    public <T> void f(T x){
        System.out.println(x.getClass().getName());
    }
    public static void main(String[] args) {
        Generic g = new Generic();
        g.f("");
        g.f("c");
        g.f(1);
        g.f(1.0f);
        g.f(g);
        g.f('c');
    }
}
