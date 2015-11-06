package go.deyu.util;

/**
 * Created by huangeyu on 15/11/6.
 */
public class TimeUtil {
    /**
     *
     * @param second
     * @return HH:MM:SS format string
     */
    public static String secondToHMS(long second){
        long hours = (int)second / 3600;
        long minutes = (second % 3600) / 60;
        long seconds = second % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
