package hr.vbestak.authclient.util;

public class ThreadUtil {
    public static void pauseRandom(long maxTime) {
        try {
            Long time = (long) Math.floor(Math.random()*maxTime+1);
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.print(e);
        }
    }
}
