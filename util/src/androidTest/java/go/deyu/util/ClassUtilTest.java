package go.deyu.util;

import org.junit.Test;

/**
 * Created by huangeyu on 15/7/19.
 */
public class ClassUtilTest extends BaseAndroidTestCase{
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TAG = getClass().getSimpleName();
    }

//    LOG Info like this
//    I/UtilClassUtil(12096): *****---Class Info---*****
//    I/UtilClassUtil(12096): Class name :go.deyu.util.ClassUtilTest
//    I/UtilClassUtil(12096): is interface false
//    I/UtilClassUtil(12096): Simple name : ClassUtilTest
//    I/UtilClassUtil(12096): Canonical name : go.deyu.util.ClassUtilTest
//    I/UtilClassUtil(12096): *****---Class Info---*****
    @Test
    public void testshowClassInfo(){
        ClassUtil.showClassInfo(getClass());
    }

}
