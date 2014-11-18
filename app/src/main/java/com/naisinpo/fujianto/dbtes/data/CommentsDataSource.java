package com.naisinpo.fujianto.dbtes.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.naisinpo.fujianto.dbtes.data.CommentContract.CommentEntry;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fujianto on 16/11/14.
 */

//todo 5: Create DataSources to easy access db
public class CommentsDataSource {
    // Database fields
    private CommentDBHelper dbHelper;
    private SQLiteDatabase db;
    private String[] allColumns = { CommentEntry._ID,
            CommentEntry.COLUMN_COMMENT };
    private static String LOG_TAG  = CommentsDataSource.class.getSimpleName();

    public CommentsDataSource(Context context) {
        dbHelper = new CommentDBHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }

    public Comment createComment(String comment) {
        //Create ContentValues, Put value on it.
        ContentValues values = new ContentValues();
        values.put(CommentEntry.COLUMN_COMMENT, comment);

        //Insert value to DB
        long insertId = db.insert(CommentEntry.TABLE_NAME, null, values);

        //Get Comment Value from database, return it. It will be added to Adapter later.
        Cursor cursor = db.query(
                CommentEntry.TABLE_NAME,
                allColumns, CommentEntry._ID + " = " + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();

        return newComment;
    }


    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        db.delete(CommentEntry.TABLE_NAME, CommentEntry._ID + " = " + id, null);
    }

    public void deleteAllComment(){
        db.delete(CommentEntry.TABLE_NAME, null, null);
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = db.query(CommentEntry.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
       cursor.close();

        return comments;
    }
}
