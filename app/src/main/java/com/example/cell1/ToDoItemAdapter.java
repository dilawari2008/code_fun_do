package com.example.cell1;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Adapter to bind a ToDoItem List to a view
 */
public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

    /**
     * Adapter context
     */
    Context mContext;
    public CheckBox checkBox;
    public CheckBox checkBox1;
    public CheckBox checkBox2;
    public CheckBox checkBox3;

    private static ToDoItemAdapter instances;

    /**
     * Adapter View layout
     */
    int mLayoutResourceId;

    public ToDoItemAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        instances = this;

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final ToDoItem currentItem;
        currentItem = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        row.setTag(currentItem);
        checkall(row,currentItem);


        /*checkBox = (CheckBox) row.findViewById(R.id.checkToDoItem);
        checkBox.setText(currentItem.getText());
        checkBox.setChecked(false);
        checkBox.setEnabled(true);

        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (true) {
                    checkBox.setEnabled(false);
                    if (mContext instanceof ToDoActivity) {
                        ToDoActivity activity = (ToDoActivity) mContext;
                        activity.checkItem(currentItem);
                    }
                }
            }
        });

        checkBox1 = (CheckBox) row.findViewById(R.id.checkToDoItem1);
        checkBox1.setText(currentItem.getText1());
        checkBox1.setChecked(false);
        checkBox1.setEnabled(true);

        checkBox1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (true) {
                    checkBox1.setEnabled(false);
                    if (mContext instanceof ToDoActivity) {
                        ToDoActivity activity = (ToDoActivity) mContext;
                        activity.checkItem(currentItem);
                    }
                }
            }
        });

        checkBox2 = (CheckBox) row.findViewById(R.id.checkToDoItem2);
        checkBox2.setText(currentItem.getText2());
        checkBox2.setChecked(false);
        checkBox2.setEnabled(true);

        checkBox2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (true) {
                    checkBox2.setEnabled(false);
                    if (mContext instanceof ToDoActivity) {
                        ToDoActivity activity = (ToDoActivity) mContext;
                        activity.checkItem(currentItem);
                    }
                }
            }
        });

        checkBox3 = (CheckBox) row.findViewById(R.id.checkToDoItem3);
        checkBox3.setText(currentItem.getText3());
        checkBox3.setChecked(false);
        checkBox3.setEnabled(true);


        checkBox3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (true) {
                    //Toast.makeText(ToDoItemAdapter.this,"cancelled",Toast.LENGTH_LONG).show();
                    //Toast.makeText(getContext(),"cancelled",Toast.LENGTH_LONG).show();
                    //Log.d("aaaaaaaaaaaaaaaaaaaaaaa","entered");
                    checkBox3.setEnabled(false);
                    if (mContext instanceof ToDoActivity) {
                        ToDoActivity activity = (ToDoActivity) mContext;
                        activity.checkItem(currentItem);
                    }
                }
            }
        });*/

        return row;
    }

    public static ToDoItemAdapter getInstances() {
        return instances;
    }

    public void check(View row,ToDoItem currentItem)
    {
        checkBox = (CheckBox) row.findViewById(R.id.checkToDoItem);
        checkBox.setText(currentItem.getText());
        checkBox.setChecked(false);
        checkBox.setEnabled(true);
                if (true) {
                    checkBox.setEnabled(false);
                    if (mContext instanceof ToDoActivity) {
                        ToDoActivity activity = (ToDoActivity) mContext;
                        activity.checkItem(currentItem);
                    }
                }
            }

    public void check1(View row,ToDoItem currentItem)
    {
        checkBox1 = (CheckBox) row.findViewById(R.id.checkToDoItem1);
        checkBox1.setText(currentItem.getText1());
        checkBox1.setChecked(false);
        checkBox1.setEnabled(true);

                if (true) {
                    checkBox1.setEnabled(false);
                    if (mContext instanceof ToDoActivity) {
                        ToDoActivity activity = (ToDoActivity) mContext;
                        activity.checkItem(currentItem);
                    }
                }

    }

    public void check2(View row,ToDoItem currentItem)
    {
        checkBox2 = (CheckBox) row.findViewById(R.id.checkToDoItem2);
        checkBox2.setText(currentItem.getText2());
        checkBox2.setChecked(false);
        checkBox2.setEnabled(true);


                if (true) {
                    checkBox2.setEnabled(false);
                    if (mContext instanceof ToDoActivity) {
                        ToDoActivity activity = (ToDoActivity) mContext;
                        activity.checkItem(currentItem);
                    }
                }
            }


    public void check3(View row,ToDoItem currentItem)
    {
        checkBox3 = (CheckBox) row.findViewById(R.id.checkToDoItem3);
        checkBox3.setText(currentItem.getText3());
        checkBox3.setChecked(false);
        checkBox3.setEnabled(true);

                if (true) {
                    //Toast.makeText(ToDoItemAdapter.this,"cancelled",Toast.LENGTH_LONG).show();
                    //Toast.makeText(getContext(),"cancelled",Toast.LENGTH_LONG).show();
                    //Log.d("aaaaaaaaaaaaaaaaaaaaaaa","entered");
                    checkBox3.setEnabled(false);
                    if (mContext instanceof ToDoActivity) {
                        ToDoActivity activity = (ToDoActivity) mContext;
                        activity.checkItem(currentItem);
                    }
                }
            }

    public void checkall(View row,ToDoItem currentItem)
    {
        check(row,currentItem);
        check1(row,currentItem);
        check2(row,currentItem);
        check3(row,currentItem);
    }



}




