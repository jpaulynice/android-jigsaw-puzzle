package com.jigdraw.draw.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jigdraw.draw.R;
import com.jigdraw.draw.views.DrawingView;

import java.util.UUID;

/**
 * Main activity class
 */
public class MainActivity extends Activity implements OnClickListener {
    private DrawingView drawView;
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn;
    private float currBrush, smallBrush, mediumBrush, largeBrush, largestBrush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.draw_btn) {
            handleDrawButton();
        } else if (view.getId() == R.id.erase_btn) {
            handleEraseButton();
        } else if (view.getId() == R.id.new_btn) {
            handleNewButton();
        } else if (view.getId() == R.id.save_btn) {
            handleSaveButton();
        }
    }

    /**
     *
     * @param view
     */
    public void paintClicked(View view) {
        drawView.setErase(false);
        drawView.setBrushSize(drawView.getLastBrushSize());

        if (view != currPaint) {
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            //update ui
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint = (ImageButton) view;
        }
    }

    private void init(){
        setContentView(R.layout.activity_main);

        initLayout();
        initBrushes();
        initView();
        initButtons();
    }

    private void initLayout(){
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
    }

    private void initView(){
        drawView = (DrawingView) findViewById(R.id.drawing);
        drawView.setBrushSize(mediumBrush);
    }

    private void initBrushes(){
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);
        largestBrush = getResources().getInteger(R.integer.largest_size);
    }

    private void initButtons(){
        //draw button
        drawBtn = (ImageButton) findViewById(R.id.draw_btn);
        drawBtn.setOnClickListener(this);

        //erase button
        eraseBtn = (ImageButton) findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);

        //new button
        newBtn = (ImageButton) findViewById(R.id.new_btn);
        newBtn.setOnClickListener(this);

        //save button
        saveBtn = (ImageButton) findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);
    }

    private void handleDrawButton() {
        //draw button clicked
        final Dialog brushDialog = new Dialog(this);
        //listen for clicks on size buttons
        ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);
        smallBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.setErase(false);
                drawView.setBrushSize(smallBrush);
                drawView.setLastBrushSize(smallBrush);
                brushDialog.dismiss();
            }
        });
        ImageButton mediumBtn = (ImageButton) brushDialog.findViewById(R.id.medium_brush);
        mediumBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.setErase(false);
                drawView.setBrushSize(mediumBrush);
                drawView.setLastBrushSize(mediumBrush);
                brushDialog.dismiss();
            }
        });
        ImageButton largeBtn = (ImageButton) brushDialog.findViewById(R.id.large_brush);
        largeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.setErase(false);
                drawView.setBrushSize(largeBrush);
                drawView.setLastBrushSize(largeBrush);
                brushDialog.dismiss();
            }
        });

        ImageButton largestBtn = (ImageButton) brushDialog.findViewById(R.id.largest_brush);
        largestBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.setErase(false);
                drawView.setBrushSize(largestBrush);
                drawView.setLastBrushSize(largestBrush);
                brushDialog.dismiss();
            }
        });
        //show and wait for user interaction
        brushDialog.show();
    }

    private void handleEraseButton() {
        //switch to erase - choose size
        final Dialog brushDialog = new Dialog(this);
        brushDialog.setTitle("Eraser size:");
        //size buttons
        ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);
        smallBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.setErase(true);
                drawView.setBrushSize(smallBrush);
                brushDialog.dismiss();
            }
        });
        ImageButton mediumBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);

        mediumBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.setErase(true);
                drawView.setBrushSize(mediumBrush);
                brushDialog.dismiss();
            }
        });
        ImageButton largeBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);

        largeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.setErase(true);
                drawView.setBrushSize(largeBrush);
                brushDialog.dismiss();
            }
        });
        brushDialog.show();
    }

    private void handleNewButton() {
        //new button
        AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
        newDialog.setTitle("New drawing");
        newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
        newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                drawView.startNew();
                dialog.dismiss();
            }
        });
        newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        newDialog.show();
    }

    private void handleSaveButton() {
        //save drawing
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing");
        saveDialog.setMessage("Save drawing to device Gallery?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //save drawing
                drawView.setDrawingCacheEnabled(true);
                //attempt to save
                String imgSaved = MediaStore.Images.Media.insertImage(
                        getContentResolver(), drawView.getDrawingCache(),
                        UUID.randomUUID().toString() + ".png", "drawing");
                //feedback
                if (imgSaved != null) {
                    Toast savedToast = Toast.makeText(getApplicationContext(),
                            "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                    savedToast.show();
                } else {
                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                            "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                    unsavedToast.show();
                }
                drawView.destroyDrawingCache();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        saveDialog.show();
    }
}
