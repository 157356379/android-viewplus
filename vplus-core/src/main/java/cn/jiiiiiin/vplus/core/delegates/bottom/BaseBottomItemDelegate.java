package cn.jiiiiiin.vplus.core.delegates.bottom;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.Objects;

import cn.jiiiiiin.vplus.core.R;
import cn.jiiiiiin.vplus.core.app.ConfigKeys;
import cn.jiiiiiin.vplus.core.app.ViewPlus;
import cn.jiiiiiin.vplus.core.delegates.AbstractViewPlusDelegate;
import cn.jiiiiiin.vplus.core.util.Intent.RouterUtil;
import cn.jiiiiiin.vplus.core.util.log.LoggerProxy;


/**
 * 通用tab的基类
 *
 * @author jiiiiiin
 */

public abstract class BaseBottomItemDelegate extends AbstractViewPlusDelegate {

    private static long WAIT_TIME = ViewPlus.getConfiguration(ConfigKeys.EXIT_APP_WAIT_TIME);
    private static long TOUCH_TIME = 0;

    public interface OnFinishAppListener {
        void onFinishApp();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);
    }

    @Override
    public boolean onBackPressedSupport() {
        finishApp(BaseBottomItemDelegate.this::onFinishApp);
        // return true，标识应用受理该事件，无需系统在进行传递
        return true;
    }

    public synchronized static void finishApp(OnFinishAppListener onFinishAppListener) {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            onFinishAppListener.onFinishApp();
            ActivityUtils.finishAllActivities();
            ToastUtils.showLong(R.string.exit_app);
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            ToastUtils.showLong(R.string.press_again_exit);
        }
    }

    private void onFinishApp() {
        // !子类暂时不需要做什么事情
    }

    public BaseBottomDelegate getBottomDelegate() {
        return getParentDelegate();
    }
}
