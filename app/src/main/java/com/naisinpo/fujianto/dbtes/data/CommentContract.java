package com.naisinpo.fujianto.dbtes.data;

import android.provider.BaseColumns;

/**
 * Created by fujianto on 16/11/14.
 */
public class CommentContract {
    public static final class CommentEntry implements BaseColumns{
        //TODO 1: Create Table & Column Property
        //Table _id automatically added By BaseColumns

        public static final String TABLE_NAME = "comment";
        public static final String COLUMN_COMMENT = "comment";
    }
}
