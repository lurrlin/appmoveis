package com.ifsc.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PaintView extends View {

    public enum DrawMode {
        FREEHAND,
        RECTANGLE,
        CIRCLE
    }

    private DrawMode currentMode = DrawMode.FREEHAND;
    private int currentColor = Color.BLACK;
    private final float strokeWidth = 8f;

    private Paint currentPaint;
    private Path currentPath;

    private float startX, startY;
    private float currentX, currentY;
    private boolean isDrawing = false;

    private static class DrawingShape {
        DrawMode mode;
        int color;
        float strokeWidth;
        Path path; // for freehand
        float startX, startY, endX, endY; // for rect and circle

        // Constructor for freehand
        public DrawingShape(DrawMode mode, int color, float strokeWidth, Path path) {
            this.mode = mode;
            this.color = color;
            this.strokeWidth = strokeWidth;
            this.path = path;
        }

        // Constructor for rect/circle
        public DrawingShape(DrawMode mode, int color, float strokeWidth, float startX, float startY, float endX, float endY) {
            this.mode = mode;
            this.color = color;
            this.strokeWidth = strokeWidth;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }
    }

    private final List<DrawingShape> shapes = new ArrayList<>();

    public PaintView(Context context) {
        super(context);
        init();
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        currentPaint = new Paint();
        currentPaint.setColor(currentColor);
        currentPaint.setStrokeWidth(strokeWidth);
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setAntiAlias(true);
        currentPaint.setStrokeCap(Paint.Cap.ROUND);
        currentPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    public void setDrawMode(DrawMode mode) {
        this.currentMode = mode;
    }

    public void setDrawingColor(int color) {
        this.currentColor = color;
        currentPaint.setColor(color);
    }

    public void clear() {
        shapes.clear();
        currentPath = null;
        isDrawing = false;
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // Draw all completed shapes
        for (DrawingShape shape : shapes) {
            Paint p = createPaint(shape.color, shape.strokeWidth);
            if (shape.mode == DrawMode.FREEHAND && shape.path != null) {
                canvas.drawPath(shape.path, p);
            } else if (shape.mode == DrawMode.RECTANGLE) {
                canvas.drawRect(
                        Math.min(shape.startX, shape.endX),
                        Math.min(shape.startY, shape.endY),
                        Math.max(shape.startX, shape.endX),
                        Math.max(shape.startY, shape.endY),
                        p
                );
            } else if (shape.mode == DrawMode.CIRCLE) {
                float radius = (float) Math.hypot(shape.endX - shape.startX, shape.endY - shape.startY);
                canvas.drawCircle(shape.startX, shape.startY, radius, p);
            }
        }

        // Draw shape currently in progress
        if (isDrawing) {
            if (currentMode == DrawMode.FREEHAND && currentPath != null) {
                canvas.drawPath(currentPath, currentPaint);
            } else if (currentMode == DrawMode.RECTANGLE) {
                canvas.drawRect(
                        Math.min(startX, currentX),
                        Math.min(startY, currentY),
                        Math.max(startX, currentX),
                        Math.max(startY, currentY),
                        currentPaint
                );
            } else if (currentMode == DrawMode.CIRCLE) {
                float radius = (float) Math.hypot(currentX - startX, currentY - startY);
                canvas.drawCircle(startX, startY, radius, currentPaint);
            }
        }
    }

    private Paint createPaint(int color, float width) {
        Paint p = new Paint();
        p.setColor(color);
        p.setStrokeWidth(width);
        p.setStyle(Paint.Style.STROKE);
        p.setAntiAlias(true);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setStrokeJoin(Paint.Join.ROUND);
        return p;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                currentX = x;
                currentY = y;
                isDrawing = true;
                if (currentMode == DrawMode.FREEHAND) {
                    currentPath = new Path();
                    currentPath.moveTo(x, y);
                }
                invalidate();
                return true;

            case MotionEvent.ACTION_MOVE:
                currentX = x;
                currentY = y;
                if (currentMode == DrawMode.FREEHAND && currentPath != null) {
                    currentPath.lineTo(x, y);
                }
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                performClick();
                currentX = x;
                currentY = y;
                if (currentMode == DrawMode.FREEHAND && currentPath != null) {
                    currentPath.lineTo(x, y);
                    shapes.add(new DrawingShape(DrawMode.FREEHAND, currentColor, strokeWidth, currentPath));
                    currentPath = null;
                } else if (currentMode == DrawMode.RECTANGLE) {
                    shapes.add(new DrawingShape(DrawMode.RECTANGLE, currentColor, strokeWidth, startX, startY, currentX, currentY));
                } else if (currentMode == DrawMode.CIRCLE) {
                    shapes.add(new DrawingShape(DrawMode.CIRCLE, currentColor, strokeWidth, startX, startY, currentX, currentY));
                }
                isDrawing = false;
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }
}
