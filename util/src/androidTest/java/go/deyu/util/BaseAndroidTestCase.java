package go.deyu.util;

import android.test.AndroidTestCase;

/**
 * Created by huangeyu on 15/7/15.
 */
public abstract class BaseAndroidTestCase extends AndroidTestCase {
    protected String TAG ;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        LOG.LOGTAG = getContext().getString(getContext().getApplicationInfo().labelRes);
    }
    protected void l(String message){
        LOG.d(TAG, message);
    }
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
