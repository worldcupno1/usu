package learn;

/**
 * Created by lvm on 2016/7/6.
 */
public class ExceptionExample {

    static class SimpleException extends Exception{}

    public void f() throws SimpleException{
        System.out.println("f()中抛出异常");
        throw new SimpleException();
    }

    public static void main(String[] args){
        SimpleException e = new SimpleException();
        System.out.println(e.getClass().isInstance(new SimpleException()));

        ExceptionExample t = new ExceptionExample();
        try {
            t.f();
        } catch (Exception ea) {
            System.out.println("捕获到了异常");
            System.out.println(ea.getClass());
        }
    }
}
