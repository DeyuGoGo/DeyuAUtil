package go.deyu.util;

/**
 * Created by huangeyu on 15/7/19.
 */
public class ClassUtil {

    private static String TAG = "ClassUtil";

    public static void showClassInfo(Class c){
        StringBuilder sb = new StringBuilder();
        sb.append("*****---Class Info---*****");
        sb.append("\n");
        sb.append("Class name :" + c.getName());
        sb.append("\n");
        sb.append("is interface " + c.isInterface());
        sb.append("\n");
        sb.append("Simple name : " + c.getSimpleName());
        sb.append("\n");
        sb.append("Canonical name : " + c.getCanonicalName());
        sb.append("\n");
        sb.append("*****---Class Info---*****");
        LOG.i(TAG, sb.toString());
    }

}
