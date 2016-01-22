package jacketjie.common.libray.custom.view.animatedlayout;

import jacketjie.common.libray.custom.view.expandablelayout.Utils;

/**
 * Created by wujie on 2016/test_1/15.
 */
public interface AnimatedLayout {
    int DEFAULT_DURATION = 200;

    boolean DEFAULT_EXPANDABLE = false;

    int DIRECTION_TOP = 0x123;
    int DIRECTION_LEFT = 0x456;
    int DIRECTION_RIGHT = 0x789;

    int DEFAULT_DIRECTION = DIRECTION_TOP;

    int DEFAULT_INTERPOLATOR = Utils.LINEAR_INTERPOLATOR;


}
