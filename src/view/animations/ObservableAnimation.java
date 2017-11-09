package view.animations;

public interface ObservableAnimation {
    void setAnimationObserver(AnimationObserver animationObserver);
    AnimationObserver getAnimationObserver();
}
