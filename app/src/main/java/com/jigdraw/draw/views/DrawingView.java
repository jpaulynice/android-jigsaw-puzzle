package com.jigdraw.draw.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.jigdraw.draw.R;

/**
 * Custom view to represent the drawing view the user use to draw.
 *
 * @author Jay Paulynice
 */
public class DrawingView extends View {
    /** the draw path */
    private Path drawPath;

    /** draw and canvas paint */
    private Paint drawPaint, canvasPaint;

    /** default color */
    private int paintColor = 0xFF660000;

    /** the drawing canvas */
    private Canvas drawCanvas;

    /** the canvas bitmap */
    private Bitmap canvasBitmap;

    /** current and last brush size */
    private float brushSize, lastBrushSize;

    /** whether erase is set */
    private boolean erase = false;

    /**
     * Create new drawing view with context and attributes and
     * setting up the default parameters.
     *
     * @param context the context
     * @param attrs   the attributes
     */
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    /**
     * Init parameters
     */
    private void setupDrawing() {
        //prepare for drawing and setup paint stroke properties
        brushSize = getResources().getInteger(R.integer.medium_size);
        lastBrushSize = brushSize;
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        //redraw
        invalidate();
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    /**
     * Set new color for drawing
     *
     * @param newColor the new color
     */
    public void setColor(String newColor) {
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    /**
     * Set new brush size for drawing
     *
     * @param newSize the new color
     */
    public void setBrushSize(float newSize) {
        brushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        drawPaint.setStrokeWidth(brushSize);
    }

    /**
     * @return last brush size
     */
    public float getLastBrushSize() {
        return lastBrushSize;
    }

    /**
     * Set last brush size
     *
     * @param lastSize the brush size
     */
    public void setLastBrushSize(float lastSize) {
        lastBrushSize = lastSize;
    }

    /**
     * Set erase to true when the erase button is clicked.
     *
     * @param isErase {@code true} if erase is clicked {@code false} otherwise
     */
    public void setErase(boolean isErase) {
        erase = isErase;
        Xfermode xfermode = erase ? new PorterDuffXfermode(PorterDuff.Mode.CLEAR) : null;
        drawPaint.setXfermode(xfermode);
    }

    /**
     * Create new canvas
     */
    public void startNew() {
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    /**
     * @return the paint color
     */
    public int getPaintColor() {
        return paintColor;
    }

    public Bitmap getCanvasBitmap() {
        return canvasBitmap;
    }

    public Canvas getDrawCanvas() {
        return drawCanvas;
    }
}