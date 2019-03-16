package com.crec.shield.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.crec.shield.entity.login.DataLogin;

/**
 * @author wangqi
 * @company vinelinx
 * @date 2018.9.5
 * @description 操作数据库sqlite的增删改查
 */
public class DBManager {
    private static SqliteHelper sqlHelper;
    private String mToken;

    public DBManager(Context context){
        sqlHelper = new SqliteHelper(context);
    }
    public static SqliteHelper getInstance(Context context) {
        if (null == sqlHelper) {
            sqlHelper = new SqliteHelper(context);
        }
        return sqlHelper;
    }

    public static void execSQL(SQLiteDatabase db, String sql) {
        if (null != db && null != sql && !"".equals(sql)) {
            db.execSQL(sql);
            return;
        }

        Log.e("DBManager", "execSQL failed.");
    }


    /**
     * @author wangqi
     * @company vinelinx
     * @date 2018.9.5
     * @description 给sqlite的user表插入或者更新数据
     */
    /*public void inserToUser(DataLogin dataBean){
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        if (null == db || null == dataBean)
            return;

        try {
            Cursor cursor = db.query(SqliteHelper.DB_TABLE_USER, new String[]{SqliteHelper.DB_TABLE_MESSAGES_ID},
                    SqliteHelper.DB_TABLE_USER_CODE + "=?", new String[]{dataBean.getCode() + ""}, null, null, null, null);
            boolean hasInserted = false;
            while (cursor.moveToNext()) {
                hasInserted = true;
                break;
            }
            cursor.close();

            String sql = "";
            if (hasInserted) {
                // update
                sql = "update " + SqliteHelper.DB_TABLE_USER + " set " + SqliteHelper.DB_TABLE_USER_NAME + "='" + dataBean.getUsername()
                        + "', " + SqliteHelper.DB_TABLE_USER_TYPE+ "='" + dataBean.getType() + "', "+ SqliteHelper.DB_TABLE_USER_PASSWORD+"='"+dataBean.getPassword()+ "', "
                        + SqliteHelper.DB_TABLE_USER_PROJECT_ID+ "='" + dataBean.getProject_id()
                        + "' where "+ SqliteHelper.DB_TABLE_USER_CODE + "=" + dataBean.getCode();
            } else {
                // insert
                sql = "insert into " + SqliteHelper.DB_TABLE_USER + " values('" + dataBean.getCode() + "', '" + dataBean.getUsername() + "', '"+ dataBean.getPassword() + "', '"+ mToken + "', '"+ dataBean.getType() + "', '"+ dataBean.getProject_id() +"')";
            }

            // DBManager.execSQL(db, sqlStr);
        } catch (Exception e) {
            Log.e("saveUserToDB", e.getMessage());
        }
    }

    public void deleteToUser(){

    }

    public void queryToUser(){

        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        String sql = "select "

    }

    public void updateToUser(){

    }*/


}
