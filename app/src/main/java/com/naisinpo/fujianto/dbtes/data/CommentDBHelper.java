package com.naisinpo.fujianto.dbtes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.naisinpo.fujianto.dbtes.data.CommentContract.CommentEntry;

/**
 * Created by fujianto on 16/11/14.
 */
public class CommentDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    public CommentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //todo 2: Database creation sql statement
        final String SQL_CREATE_COMMENT_TABLE = "CREATE TABLE "
                + CommentEntry.TABLE_NAME + "(" + CommentEntry._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CommentEntry.COLUMN_COMMENT
                + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_COMMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(CommentDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        //todo 3: Create upgrade database
        db.execSQL("DROP TABLE IF EXISTS " + CommentEntry.TABLE_NAME);
        onCreate(db);
    }
}
