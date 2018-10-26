package com.example.cell1;

/**
 * Represents an item in a ToDo list
 */
public class ToDoItem {

    /**
     * Item text
     */
    @com.google.gson.annotations.SerializedName("latitude")
    private String mText;
    @com.google.gson.annotations.SerializedName("longitude")
    private String mText1;
    @com.google.gson.annotations.SerializedName("victim")
    private String mText2;
    @com.google.gson.annotations.SerializedName("aquaintance")
    private String mText3;


    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    /**
     * Indicates if the item is completed
     */
    @com.google.gson.annotations.SerializedName("victim_safe")
    private boolean mComplete;

    /**
     * ToDoItem constructor
     */
    public ToDoItem() {

    }

    @Override
    public String toString() {
        return getText();
    }

    /**
     * Initializes a new ToDoItem
     *
     * @param text
     *            The item text
     * @param id
     *            The item id
     */
    public ToDoItem(String text,String text1,String text2,String text3, String id) {
        this.setText(text);
        this.setText1(text1);
        this.setText2(text2);
        this.setText3(text3);
        this.setId(id);
    }

    /**
     * Returns the item text
     */
    public String getText() {
        return mText;
    }
    public String getText1() {
        return mText1;
    }
    public String getText2() {
        return mText2;
    }
    public String getText3() {
        return mText3;
    }

    /**
     * Sets the item text
     *
     * @param text
     *            text to set
     */
    public final void setText(String text) {
        mText = text;
    }
    public final void setText1(String text) {
        mText1 = text;
    }
    public final void setText2(String text) {
        mText2 = text;
    }
    public final void setText3(String text) {
        mText3 = text;
    }




    /**
     * Returns the item id
     */
    public String getId() {
        return mId;
    }

    /**
     * Sets the item id
     *
     * @param id
     *            id to set
     */
    public final void setId(String id) {
        mId = id;
    }

    /**
     * Indicates if the item is marked as completed
     */
    public boolean isComplete() {
        return mComplete;
    }

    /**
     * Marks the item as completed or incompleted
     */
    public void setComplete(boolean complete) {
        mComplete = complete;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ToDoItem && ((ToDoItem) o).mId == mId;
    }
}