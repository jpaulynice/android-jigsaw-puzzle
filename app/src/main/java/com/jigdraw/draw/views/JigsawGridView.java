package com.jigdraw.draw.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.jigdraw.draw.R;
import com.jigdraw.draw.adapter.GridAdapter;
import com.jigdraw.draw.util.GridUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JigsawGridView extends GridView {
    private static final int INVALID_ID = -1;
    private static final int MOVE_DURATION = 300;
    private static final int SMOOTH_SCROLL_AMOUNT_AT_EDGE = 8;

    private BitmapDrawable mHoverCell;
    private Rect mHoverCellCurrentBounds;
    private Rect mHoverCellOriginalBounds;

    private int mTotalOffsetY = 0;
    private int mTotalOffsetX = 0;

    private int mDownX = -1;
    private int mDownY = -1;
    private int mLastEventY = -1;
    private int mLastEventX = -1;

    // used to distinguish straight line and diagonal switching
    private int mOverlapIfSwitchStraightLine;

    private List<Long> idList = new ArrayList<>();

    private long mMobileItemId = INVALID_ID;

    private boolean mCellIsMobile = false;
    private int mActivePointerId = INVALID_ID;

    private boolean mIsMobileScrolling;
    private int mSmoothScrollAmountAtEdge = 0;
    private boolean mIsWaitingForScrollFinish = false;
    private int mScrollState = OnScrollListener.SCROLL_STATE_IDLE;

    private boolean mIsEditMode = false;
    private boolean mHoverAnimation;
    private boolean mReorderAnimation;

    private OnScrollListener mUserScrollListener;
    private OnDropListener mDropListener;
    private OnDragListener mDragListener;

    private OnItemClickListener mUserItemClickListener;
    private OnItemClickListener mLocalItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if (!isEditMode() && isEnabled() && mUserItemClickListener != null) {
                mUserItemClickListener.onItemClick(parent, view, position, id);
            }
        }
    };

    private View mMobileView;
    private OnScrollListener mScrollListener = new OnScrollListener() {

        private int mPreviousFirstVisibleItem = -1;
        private int mPreviousVisibleItemCount = -1;
        private int mCurrentFirstVisibleItem;
        private int mCurrentVisibleItemCount;
        private int mCurrentScrollState;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            mCurrentScrollState = scrollState;
            mScrollState = scrollState;
            isScrollCompleted();
            if (mUserScrollListener != null) {
                mUserScrollListener.onScrollStateChanged(view, scrollState);
            }
        }

        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            mCurrentFirstVisibleItem = firstVisibleItem;
            mCurrentVisibleItemCount = visibleItemCount;

            mPreviousFirstVisibleItem = (mPreviousFirstVisibleItem == -1) ? mCurrentFirstVisibleItem
                    : mPreviousFirstVisibleItem;
            mPreviousVisibleItemCount = (mPreviousVisibleItemCount == -1) ? mCurrentVisibleItemCount
                    : mPreviousVisibleItemCount;

            checkAndHandleFirstVisibleCellChange();
            checkAndHandleLastVisibleCellChange();

            mPreviousFirstVisibleItem = mCurrentFirstVisibleItem;
            mPreviousVisibleItemCount = mCurrentVisibleItemCount;

            if (mUserScrollListener != null) {
                mUserScrollListener.onScroll(view, firstVisibleItem,
                        visibleItemCount, totalItemCount);
            }
        }

        private void isScrollCompleted() {
            if (mCurrentVisibleItemCount > 0
                    && mCurrentScrollState == SCROLL_STATE_IDLE) {
                if (mCellIsMobile && mIsMobileScrolling) {
                    handleMobileCellScroll();
                } else if (mIsWaitingForScrollFinish) {
                    touchEventsEnded();
                }
            }
        }

        public void checkAndHandleFirstVisibleCellChange() {
            if (mCurrentFirstVisibleItem != mPreviousFirstVisibleItem) {
                if (mCellIsMobile && mMobileItemId != INVALID_ID) {
                    updateNeighborViewsForId(mMobileItemId);
                    handleCellSwitch();
                }
            }
        }

        public void checkAndHandleLastVisibleCellChange() {
            int currentLastVisibleItem = mCurrentFirstVisibleItem
                    + mCurrentVisibleItemCount;
            int previousLastVisibleItem = mPreviousFirstVisibleItem
                    + mPreviousVisibleItemCount;
            if (currentLastVisibleItem != previousLastVisibleItem) {
                if (mCellIsMobile && mMobileItemId != INVALID_ID) {
                    updateNeighborViewsForId(mMobileItemId);
                    handleCellSwitch();
                }
            }
        }
    };

    public JigsawGridView(Context context) {
        super(context);
        init(context);
    }

    public JigsawGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JigsawGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public static boolean isPreLollipop() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }

    @Override
    public void setOnScrollListener(OnScrollListener scrollListener) {
        this.mUserScrollListener = scrollListener;
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHoverCell != null) {
            mHoverCell.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getX();
                mDownY = (int) event.getY();
                mActivePointerId = event.getPointerId(0);
                if (mIsEditMode && isEnabled()) {
                    layoutChildren();
                    int position = pointToPosition(mDownX, mDownY);
                    startDragAtPosition(position);
                } else if (!isEnabled()) {
                    return false;
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_ID) {
                    break;
                }

                int pointerIndex = event.findPointerIndex(mActivePointerId);

                mLastEventY = (int) event.getY(pointerIndex);
                mLastEventX = (int) event.getX(pointerIndex);
                int deltaY = mLastEventY - mDownY;
                int deltaX = mLastEventX - mDownX;

                if (mCellIsMobile) {
                    mHoverCellCurrentBounds.offsetTo(mHoverCellOriginalBounds.left
                            + deltaX + mTotalOffsetX, mHoverCellOriginalBounds.top
                            + deltaY + mTotalOffsetY);
                    mHoverCell.setBounds(mHoverCellCurrentBounds);
                    invalidate();
                    handleCellSwitch();
                    mIsMobileScrolling = false;
                    handleMobileCellScroll();
                    return false;
                }
                break;

            case MotionEvent.ACTION_UP:
                touchEventsEnded();

                if (mHoverCell != null) {
                    if (mDropListener != null) {
                        mDropListener.onActionDrop();
                    }
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                touchEventsCancelled();

                if (mHoverCell != null) {
                    if (mDropListener != null) {
                        mDropListener.onActionDrop();
                    }
                }
                break;

            case MotionEvent.ACTION_POINTER_UP:
                pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    touchEventsEnded();
                }
                break;

            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    public void setOnDropListener(OnDropListener dropListener) {
        this.mDropListener = dropListener;
    }

    public void setOnDragListener(OnDragListener dragListener) {
        this.mDragListener = dragListener;
    }

    public void startEditMode(int position) {
        requestDisallowInterceptTouchEvent(true);
        if (isPostHoneycomb())
            if (position != -1) {
                startDragAtPosition(position);
            }
        mIsEditMode = true;
    }

    public void stopEditMode() {
        mIsEditMode = false;
        requestDisallowInterceptTouchEvent(false);
    }

    public boolean isEditMode() {
        return mIsEditMode;
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mUserItemClickListener = listener;
        super.setOnItemClickListener(mLocalItemClickListener);
    }

    public void init(Context context) {
        super.setOnScrollListener(mScrollListener);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mSmoothScrollAmountAtEdge = (int) (SMOOTH_SCROLL_AMOUNT_AT_EDGE
                * metrics.density + 0.5f);
        mOverlapIfSwitchStraightLine = getResources().getDimensionPixelSize(
                R.dimen.dgv_overlap_if_switch_straight_line);
    }

    private void reorderElements(int originalPosition, int targetPosition) {
        if (mDragListener != null)
            mDragListener.onDragPositionsChanged(originalPosition,
                    targetPosition);
        getAdapterInterface().reorderItems(originalPosition, targetPosition);
    }

    private int getColumnCount() {
        return getAdapterInterface().getColumnCount();
    }

    private GridAdapter getAdapterInterface() {
        return ((GridAdapter) getAdapter());
    }

    private BitmapDrawable getAndAddHoverView(View v) {

        int w = v.getWidth();
        int h = v.getHeight();
        int top = v.getTop();
        int left = v.getLeft();

        Bitmap b = getBitmapFromView(v);

        BitmapDrawable drawable = new BitmapDrawable(getResources(), b);

        mHoverCellOriginalBounds = new Rect(left, top, left + w, top + h);
        mHoverCellCurrentBounds = new Rect(mHoverCellOriginalBounds);

        drawable.setBounds(mHoverCellCurrentBounds);

        return drawable;
    }

    private Bitmap getBitmapFromView(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private void updateNeighborViewsForId(long itemId) {
        idList.clear();
        int draggedPos = getPositionForID(itemId);
        for (int pos = getFirstVisiblePosition(); pos <= getLastVisiblePosition(); pos++) {
            if (draggedPos != pos && getAdapterInterface().canReorder(pos)) {
                idList.add(getId(pos));
            }
        }
    }

    public int getPositionForID(long itemId) {
        View v = getViewForId(itemId);
        if (v == null) {
            return -1;
        } else {
            return getPositionForView(v);
        }
    }

    public View getViewForId(long itemId) {
        int firstVisiblePosition = getFirstVisiblePosition();
        ListAdapter adapter = getAdapter();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            int position = firstVisiblePosition + i;
            long id = adapter.getItemId(position);
            if (id == itemId) {
                return v;
            }
        }
        return null;
    }

    private void startDragAtPosition(int position) {
        mTotalOffsetY = 0;
        mTotalOffsetX = 0;
        int itemNum = position - getFirstVisiblePosition();
        View selectedView = getChildAt(itemNum);
        if (selectedView != null) {
            mMobileItemId = getAdapter().getItemId(position);
            mHoverCell = getAndAddHoverView(selectedView);
            if (isPostHoneycomb())
                selectedView.setVisibility(View.INVISIBLE);
            mCellIsMobile = true;
            updateNeighborViewsForId(mMobileItemId);
            if (mDragListener != null) {
                mDragListener.onDragStarted(position);
            }
        }
    }

    private void handleMobileCellScroll() {
        mIsMobileScrolling = handleMobileCellScroll(mHoverCellCurrentBounds);
    }

    public boolean handleMobileCellScroll(Rect r) {
        int offset = computeVerticalScrollOffset();
        int height = getHeight();
        int extent = computeVerticalScrollExtent();
        int range = computeVerticalScrollRange();
        int hoverViewTop = r.top;
        int hoverHeight = r.height();

        if (hoverViewTop <= 0 && offset > 0) {
            smoothScrollBy(-mSmoothScrollAmountAtEdge, 0);
            return true;
        }

        if (hoverViewTop + hoverHeight >= height && (offset + extent) < range) {
            smoothScrollBy(mSmoothScrollAmountAtEdge, 0);
            return true;
        }

        return false;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    private void touchEventsEnded() {
        final View mobileView = getViewForId(mMobileItemId);
        if (mobileView != null && (mCellIsMobile || mIsWaitingForScrollFinish)) {
            mCellIsMobile = false;
            mIsWaitingForScrollFinish = false;
            mIsMobileScrolling = false;
            mActivePointerId = INVALID_ID;
            if (mScrollState != OnScrollListener.SCROLL_STATE_IDLE) {
                mIsWaitingForScrollFinish = true;
                return;
            }

            mHoverCellCurrentBounds.offsetTo(mobileView.getLeft(),
                    mobileView.getTop());

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                animateBounds(mobileView);
            } else {
                mHoverCell.setBounds(mHoverCellCurrentBounds);
                invalidate();
                reset(mobileView);
            }
        } else {
            touchEventsCancelled();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void animateBounds(final View mobileView) {
        TypeEvaluator<Rect> sBoundEvaluator = new TypeEvaluator<Rect>() {
            public Rect evaluate(float fraction, Rect startValue, Rect endValue) {
                return new Rect(interpolate(startValue.left, endValue.left,
                        fraction), interpolate(startValue.top, endValue.top,
                        fraction), interpolate(startValue.right,
                        endValue.right, fraction), interpolate(
                        startValue.bottom, endValue.bottom, fraction));
            }

            public int interpolate(int start, int end, float fraction) {
                return (int) (start + fraction * (end - start));
            }
        };

        ObjectAnimator hoverViewAnimator = ObjectAnimator.ofObject(mHoverCell,
                "bounds", sBoundEvaluator, mHoverCellCurrentBounds);
        hoverViewAnimator
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        invalidate();
                    }
                });
        hoverViewAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mHoverAnimation = false;
                updateEnableState();
                reset(mobileView);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mHoverAnimation = true;
                updateEnableState();
            }


        });
        hoverViewAnimator.start();
    }

    private void reset(View mobileView) {
        idList.clear();
        mMobileItemId = INVALID_ID;
        mobileView.setVisibility(View.VISIBLE);
        mHoverCell = null;

        // ugly fix for unclear disappearing items after reorder
        for (int i = 0; i < getLastVisiblePosition()
                - getFirstVisiblePosition(); i++) {
            View child = getChildAt(i);
            if (child != null) {
                child.setVisibility(View.VISIBLE);
            }
        }
        invalidate();
    }

    private void updateEnableState() {
        setEnabled(!mHoverAnimation && !mReorderAnimation);
    }

    private boolean isPostHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    private void touchEventsCancelled() {
        View mobileView = getViewForId(mMobileItemId);
        if (mCellIsMobile) {
            reset(mobileView);
        }
        mCellIsMobile = false;
        mIsMobileScrolling = false;
        mActivePointerId = INVALID_ID;

    }

    private void handleCellSwitch() {
        final int deltaY = mLastEventY - mDownY;
        final int deltaX = mLastEventX - mDownX;
        final int deltaYTotal = mHoverCellOriginalBounds.centerY()
                + mTotalOffsetY + deltaY;
        final int deltaXTotal = mHoverCellOriginalBounds.centerX()
                + mTotalOffsetX + deltaX;
        mMobileView = getViewForId(mMobileItemId);
        View targetView = null;
        float vX = 0;
        float vY = 0;
        Point mobileColumnRowPair = getColumnAndRowForView(mMobileView);
        for (Long id : idList) {
            View view = getViewForId(id);
            if (view != null) {
                Point targetColumnRowPair = getColumnAndRowForView(view);
                if ((aboveRight(targetColumnRowPair, mobileColumnRowPair)
                        && deltaYTotal < view.getBottom()
                        && deltaXTotal > view.getLeft()
                        || aboveLeft(targetColumnRowPair, mobileColumnRowPair)
                        && deltaYTotal < view.getBottom()
                        && deltaXTotal < view.getRight()
                        || belowRight(targetColumnRowPair, mobileColumnRowPair)
                        && deltaYTotal > view.getTop()
                        && deltaXTotal > view.getLeft()
                        || belowLeft(targetColumnRowPair, mobileColumnRowPair)
                        && deltaYTotal > view.getTop()
                        && deltaXTotal < view.getRight()
                        || above(targetColumnRowPair, mobileColumnRowPair)
                        && deltaYTotal < view.getBottom()
                        - mOverlapIfSwitchStraightLine
                        || below(targetColumnRowPair, mobileColumnRowPair)
                        && deltaYTotal > view.getTop()
                        + mOverlapIfSwitchStraightLine
                        || right(targetColumnRowPair, mobileColumnRowPair)
                        && deltaXTotal > view.getLeft()
                        + mOverlapIfSwitchStraightLine || left(
                        targetColumnRowPair, mobileColumnRowPair)
                        && deltaXTotal < view.getRight()
                        - mOverlapIfSwitchStraightLine)) {
                    float xDiff = Math.abs(GridUtil.getViewX(view)
                            - GridUtil.getViewX(mMobileView));
                    float yDiff = Math.abs(GridUtil.getViewY(view)
                            - GridUtil.getViewY(mMobileView));
                    if (xDiff >= vX && yDiff >= vY) {
                        vX = xDiff;
                        vY = yDiff;
                        targetView = view;
                    }
                }
            }
        }
        if (targetView != null) {
            final int originalPosition = getPositionForView(mMobileView);
            int targetPosition = getPositionForView(targetView);

            final GridAdapter adapter = getAdapterInterface();
            if (targetPosition == INVALID_POSITION
                    || !adapter.canReorder(originalPosition)
                    || !adapter.canReorder(targetPosition)) {
                updateNeighborViewsForId(mMobileItemId);
                return;
            }
            reorderElements(originalPosition, targetPosition);

            mDownY = mLastEventY;
            mDownX = mLastEventX;

            SwitchCellAnimator switchCellAnimator;

            if (isPostHoneycomb() && isPreLollipop()) // Between Android 3.0 and
                // Android L
                switchCellAnimator = new KitKatSwitchCellAnimator(deltaX,
                        deltaY);
            else if (isPreLollipop()) // Before Android 3.0
                switchCellAnimator = new PreHoneycombCellAnimator(deltaX,
                        deltaY);
            else
                // Android L
                switchCellAnimator = new LSwitchCellAnimator(deltaX, deltaY);

            updateNeighborViewsForId(mMobileItemId);

            switchCellAnimator.animateSwitchCell(originalPosition,
                    targetPosition);
        }
    }

    private boolean belowLeft(Point targetColumnRowPair,
                              Point mobileColumnRowPair) {
        return targetColumnRowPair.y > mobileColumnRowPair.y
                && targetColumnRowPair.x < mobileColumnRowPair.x;
    }

    private boolean belowRight(Point targetColumnRowPair,
                               Point mobileColumnRowPair) {
        return targetColumnRowPair.y > mobileColumnRowPair.y
                && targetColumnRowPair.x > mobileColumnRowPair.x;
    }

    private boolean aboveLeft(Point targetColumnRowPair,
                              Point mobileColumnRowPair) {
        return targetColumnRowPair.y < mobileColumnRowPair.y
                && targetColumnRowPair.x < mobileColumnRowPair.x;
    }

    private boolean aboveRight(Point targetColumnRowPair,
                               Point mobileColumnRowPair) {
        return targetColumnRowPair.y < mobileColumnRowPair.y
                && targetColumnRowPair.x > mobileColumnRowPair.x;
    }

    private boolean above(Point targetColumnRowPair, Point mobileColumnRowPair) {
        return targetColumnRowPair.y < mobileColumnRowPair.y
                && targetColumnRowPair.x == mobileColumnRowPair.x;
    }

    private boolean below(Point targetColumnRowPair, Point mobileColumnRowPair) {
        return targetColumnRowPair.y > mobileColumnRowPair.y
                && targetColumnRowPair.x == mobileColumnRowPair.x;
    }

    private boolean right(Point targetColumnRowPair, Point mobileColumnRowPair) {
        return targetColumnRowPair.y == mobileColumnRowPair.y
                && targetColumnRowPair.x > mobileColumnRowPair.x;
    }

    private boolean left(Point targetColumnRowPair, Point mobileColumnRowPair) {
        return targetColumnRowPair.y == mobileColumnRowPair.y
                && targetColumnRowPair.x < mobileColumnRowPair.x;
    }

    private Point getColumnAndRowForView(View view) {
        int pos = getPositionForView(view);
        int columns = getColumnCount();
        int column = pos % columns;
        int row = pos / columns;
        return new Point(column, row);
    }

    private long getId(int position) {
        return getAdapter().getItemId(position);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void animateReorder(final int oldPosition, final int newPosition) {
        boolean isForward = newPosition > oldPosition;
        List<Animator> resultList = new LinkedList<>();
        if (isForward) {
            for (int pos = Math.min(oldPosition, newPosition); pos < Math.max(
                    oldPosition, newPosition); pos++) {
                View view = getViewForId(getId(pos));
                if ((pos + 1) % getColumnCount() == 0) {
                    resultList.add(createTranslationAnimations(view,
                            -view.getWidth() * (getColumnCount() - 1), 0,
                            view.getHeight(), 0));
                } else {
                    resultList.add(createTranslationAnimations(view,
                            view.getWidth(), 0, 0, 0));
                }
            }
        } else {
            for (int pos = Math.max(oldPosition, newPosition); pos > Math.min(
                    oldPosition, newPosition); pos--) {
                View view = getViewForId(getId(pos));
                if ((pos + getColumnCount()) % getColumnCount() == 0) {
                    resultList.add(createTranslationAnimations(view,
                            view.getWidth() * (getColumnCount() - 1), 0,
                            -view.getHeight(), 0));
                } else {
                    resultList.add(createTranslationAnimations(view,
                            -view.getWidth(), 0, 0, 0));
                }
            }
        }

        AnimatorSet resultSet = new AnimatorSet();
        resultSet.playTogether(resultList);
        resultSet.setDuration(MOVE_DURATION);
        resultSet.setInterpolator(new AccelerateDecelerateInterpolator());
        resultSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mReorderAnimation = true;
                updateEnableState();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mReorderAnimation = false;
                updateEnableState();
            }
        });
        resultSet.start();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private AnimatorSet createTranslationAnimations(View view, float startX,
                                                    float endX, float startY, float endY) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX",
                startX, endX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "translationY",
                startY, endY);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        return animSetXY;
    }

    private interface SwitchCellAnimator {
        void animateSwitchCell(final int originalPosition,
                               final int targetPosition);
    }

    public interface OnDropListener {
        void onActionDrop();
    }

    public interface OnDragListener {
        void onDragStarted(int position);

        void onDragPositionsChanged(int oldPosition, int newPosition);
    }

    private class PreHoneycombCellAnimator implements SwitchCellAnimator {
        private int mDeltaY;
        private int mDeltaX;

        public PreHoneycombCellAnimator(int deltaX, int deltaY) {
            mDeltaX = deltaX;
            mDeltaY = deltaY;
        }

        @Override
        public void animateSwitchCell(int originalPosition, int targetPosition) {
            mTotalOffsetY += mDeltaY;
            mTotalOffsetX += mDeltaX;
        }
    }

    private class KitKatSwitchCellAnimator implements SwitchCellAnimator {

        private int mDeltaY;
        private int mDeltaX;

        public KitKatSwitchCellAnimator(int deltaX, int deltaY) {
            mDeltaX = deltaX;
            mDeltaY = deltaY;
        }

        private class AnimateSwitchViewOnPreDrawListener implements
                ViewTreeObserver.OnPreDrawListener {

            private final View mPreviousMobileView;
            private final int mOriginalPosition;
            private final int mTargetPosition;

            AnimateSwitchViewOnPreDrawListener(final View previousMobileView,
                                               final int originalPosition, final int targetPosition) {
                mPreviousMobileView = previousMobileView;
                mOriginalPosition = originalPosition;
                mTargetPosition = targetPosition;
            }

            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);

                mTotalOffsetY += mDeltaY;
                mTotalOffsetX += mDeltaX;

                animateReorder(mOriginalPosition, mTargetPosition);

                mPreviousMobileView.setVisibility(View.VISIBLE);

                if (mMobileView != null) {
                    mMobileView.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        }

        @Override
        public void animateSwitchCell(final int originalPosition,
                                      final int targetPosition) {
            assert mMobileView != null;
            getViewTreeObserver().addOnPreDrawListener(
                    new AnimateSwitchViewOnPreDrawListener(mMobileView,
                            originalPosition, targetPosition));
            mMobileView = getViewForId(mMobileItemId);
        }


    }

    private class LSwitchCellAnimator implements SwitchCellAnimator {

        private int mDeltaY;
        private int mDeltaX;

        public LSwitchCellAnimator(int deltaX, int deltaY) {
            mDeltaX = deltaX;
            mDeltaY = deltaY;
        }

        private class AnimateSwitchViewOnPreDrawListener implements
                ViewTreeObserver.OnPreDrawListener {
            private final int mOriginalPosition;
            private final int mTargetPosition;

            AnimateSwitchViewOnPreDrawListener(final int originalPosition,
                                               final int targetPosition) {
                mOriginalPosition = originalPosition;
                mTargetPosition = targetPosition;
            }

            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);

                mTotalOffsetY += mDeltaY;
                mTotalOffsetX += mDeltaX;

                animateReorder(mOriginalPosition, mTargetPosition);

                assert mMobileView != null;
                mMobileView.setVisibility(View.VISIBLE);
                mMobileView = getViewForId(mMobileItemId);
                assert mMobileView != null;
                mMobileView.setVisibility(View.INVISIBLE);
                return true;
            }
        }

        @Override
        public void animateSwitchCell(final int originalPosition,
                                      final int targetPosition) {
            getViewTreeObserver().addOnPreDrawListener(
                    new AnimateSwitchViewOnPreDrawListener(originalPosition,
                            targetPosition));
        }
    }
}