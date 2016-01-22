package jacketjie.common.libray.custom.view.animatedlayout;

/**
 * Created by wujie on 2016/test_1/15.
 */
public interface AnimatedLayoutListener {
    int vertical = 0;
    int horizontal = 1;

    enum Direction{
        Left,Top,Right
    }

    void animationCreated();
    void animationEnded();
}
