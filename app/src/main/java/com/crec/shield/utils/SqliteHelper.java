package com.crec.shield.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.crec.shield.entity.message.MessageItemData;
import com.crec.shield.entity.message.MessageItemResponse;
import com.crec.shield.entity.login.DataLogin;
import com.crec.shield.entity.login.LoginResponse;
//import com.crec.shield.entity.login.UserData;
import com.crec.shield.entity.project.search.HistoryTableEntity;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.lzy.okgo.OkGo;

import okhttp3.Call;
import okhttp3.Response;


import static com.crec.shield.global.StaticConstant.DB_NAME;
import static com.crec.shield.global.StaticConstant.DB_TABLE_HISTORY;
import static com.crec.shield.global.StaticConstant.DB_TABLE_HISTORY_CODE;
import static com.crec.shield.global.StaticConstant.DB_TABLE_HISTORY_PROJECT_ID;
import static com.crec.shield.global.StaticConstant.DB_TABLE_HISTORY_PROJECT_NAME;
import static com.crec.shield.global.StaticConstant.DB_TABLE_HISTORY_TIME;
import static com.crec.shield.global.StaticConstant.DB_TABLE_MESSAGE;
import static com.crec.shield.global.StaticConstant.DB_TABLE_MESSAGE_MESSAGEID;
import static com.crec.shield.global.StaticConstant.DB_TABLE_MESSAGE_STATUS;
import static com.crec.shield.global.StaticConstant.DB_TABLE_USER;
import static com.crec.shield.global.StaticConstant.DB_TABLE_USER_CODE;
import static com.crec.shield.global.StaticConstant.DB_TABLE_USER_NAME;
import static com.crec.shield.global.StaticConstant.DB_TABLE_USER_PROJECT_ID;
import static com.crec.shield.global.StaticConstant.DB_TABLE_USER_TOKEN;
import static com.crec.shield.global.StaticConstant.DB_TABLE_USER_TYPE;
import static com.crec.shield.global.StaticConstant.DB_VERSION;


/**
 *@company  vinelinx
 *@author  wangqi
 *@date  2018-09-01
 *@description   sqlite数据库的创建及增删改查
 */

public class SqliteHelper extends SQLiteOpenHelper{

    /*
     * @ context  上下文
     * @ name     数据库名称
     * @ factory  游标工厂
     * @ version  版本号
     * */
    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public SqliteHelper(Context context) {
        super(context, DB_NAME, null ,DB_VERSION);
    }

    /**
     *@company  vinelinx
     *@author  wangqi
     *@date   2018.9.3
     *@description   第一次创建数据库时被调用
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // 创建数据库时的回调
        Log.i("SqliteHelper", "onCreate");

        // 创建用户表
        createUserTable(db);

        // 创建历史记录表
        createHistoryTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 升级数据库时的回调
        Log.i("SqliteHelper", "onUpgrade");
    }


    /**
     *@company  vinelinx
     *@author  wangqi
     *@date   2018.9.5
     *@description   创建用户表
     */
    public void createUserTable(SQLiteDatabase db) {
        String cdSql = "create table "+DB_TABLE_USER+"("+DB_TABLE_USER_TOKEN+" varchar(50) primary key," +DB_TABLE_USER_CODE+" varchar(20),"+
                ""+DB_TABLE_USER_NAME+" varchar(50)," +
                ""+DB_TABLE_USER_PROJECT_ID+" varchar(50), "+DB_TABLE_USER_TYPE+" int)";
        db.execSQL(cdSql);
    }

    /**
     *@company  vinelinx
     *@author  wangqi
     *@date   2018.9.5
     *@description   创建历史记录表
     */
    public void createHistoryTable(SQLiteDatabase db){
        String htSql = "create table "+DB_TABLE_HISTORY+"("+DB_TABLE_HISTORY_CODE+" varchar(50)," +DB_TABLE_HISTORY_PROJECT_ID+" varchar(200),"+
                "" +DB_TABLE_HISTORY_PROJECT_NAME+" varchar(500)," +DB_TABLE_HISTORY_TIME+" datetime )";
        db.execSQL(htSql);
    }


    /**
     *@company  vinelinx
     *@author  wangqi
     *@date   2018.9.5
     *@description   创建消息记录表
     */
    public void createMessageTable(SQLiteDatabase db){
        String htSql = "create table "+DB_TABLE_MESSAGE+"("+DB_TABLE_MESSAGE_MESSAGEID+" varchar(500)," +DB_TABLE_MESSAGE_STATUS+" integer(5) )";
        db.execSQL(htSql);
    }





    /**
     *@company  vinelinx
     *@author  wangqi
     *@date   2018.9.3
     *@description   检查用户信息是否在SQLite数据库表中存在
     */
    public boolean checkUserInfoExist(SQLiteDatabase db) {
        boolean isExist = false;
        try {
            Cursor cursor = db.query(DB_TABLE_USER, new String[]{DB_TABLE_USER_CODE},
                    DB_TABLE_USER_CODE + "=?", new String[]{"1"}, null, null, null, null);
            while (cursor.moveToNext()) {
                isExist = true;
                break;
            }
            cursor.close();

        } catch (Exception e) {
            Log.e("checkDutyInfoExist", e.getMessage());
        }

        return isExist;
    }

    /**
     *@company  vinelinx
     *@author  wangqi
     *@date   2018.9.3
     *@description   检查名为user 的table 在SQLite数据库DB中是否存在
     */
    public boolean isTableExist(SQLiteDatabase db, String tableName) {
        boolean isTableExist = false;
        if (null != db) {
            String sql = "SELECT count(*) FROM sqlite_master WHERE type='table' AND name='" + tableName + "'";
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                if (cursor.getInt(0) == 1) {
                    isTableExist = true;
                }
                break;
            }
            cursor.close();
        }
        return isTableExist;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.i("SqliteHelper", "onOpen");
    }

    // 用户表初始化/更新
    public void  UserInit(SQLiteDatabase db, DataLogin dataBean) {
        saveUserToDB(db, dataBean);
    }

    // 历史记录表初始化/更新
//    public void  HistoryInit(SQLiteDatabase db, HistoryTableEntity history) {
//        saveHistoryToDB(db, history);
//    }

    // 消息表初始化/更新s
    public void  MessageInit(SQLiteDatabase db, MessageItemData message) {
        saveMessageToDB(db, message);
    }

    /*
     * 删除表
     * */
    public void deleteUser(SQLiteDatabase db){
        String sql = "DROP TABLE crec_shield.user";
        db.execSQL(sql);
    }

    /**
     * 保存用户表数据
     * @param dataBean
     */
    private void saveUserToDB(SQLiteDatabase db, DataLogin dataBean) {
        if (null == db || null == dataBean) return;
        boolean hasInserted = false;
        try {

            Cursor cursor = db.query(DB_TABLE_USER, new String[]{DB_TABLE_USER_CODE},
                    DB_TABLE_USER_CODE + "=?", new String[]{dataBean.getToken() + ""}, null, null, null, null);
            while (cursor.moveToNext()) {
                hasInserted = true;
                break;
            }
            cursor.close();

            String sqlStr = "";
            if (hasInserted) {
                // update
                sqlStr = "update " + DB_TABLE_USER + " set " + DB_TABLE_USER_TOKEN+ "='" + dataBean.getToken()+ DB_TABLE_USER_NAME + "='" + dataBean.getUsername()
                        + "', " + DB_TABLE_USER_TYPE+ "='" + dataBean.getType() + "', "
                        + DB_TABLE_USER_PROJECT_ID+ "='" + dataBean.getProject_id()
                        + "' where "+ DB_TABLE_USER_CODE + "=" + dataBean.getCode();
            } else {
                // insert
                sqlStr = "insert into " + DB_TABLE_USER + " values('" + dataBean.getToken() + "', '" + dataBean.getCode() + "', '"+ dataBean.getUsername() + "', '"+ dataBean.getProject_id() + "', '"+ dataBean.getType() +"')";
            }

            DBManager.execSQL(db, sqlStr);
        } catch (Exception e) {
            Log.e("saveUserToDB", e.getMessage());
        }
    }

    /**
     * 保存历史表数据
     * @param history
     */
//    private void saveHistoryToDB(SQLiteDatabase db, HistoryTableEntity history) {
//        if (null == db || null == history) return;
//        boolean hasInserted = false;
//        try {
//
//            Cursor cursor = db.query(DB_TABLE_HISTORY, new String[]{DB_TABLE_HISTORY_PROJECT_ID},
//                    DB_TABLE_HISTORY_PROJECT_ID+"=?", new String[]{history.getProject_id()}, null, null, null, null);
//            while (cursor.moveToNext()) {
//                hasInserted = true;
//                break;
//            }
//            cursor.close();
//
//            String sqlStr = "";
//            if (hasInserted) {
//                // update
//                sqlStr = "update " + DB_TABLE_HISTORY + " set " + DB_TABLE_HISTORY_TIME+ "='" + history.getDate()+"' where "+DB_TABLE_HISTORY_PROJECT_ID+"='"+history.getProject_id()+"'" ;
//            } else {
//                // insert
//                sqlStr = "insert into " + DB_TABLE_HISTORY + " values('" + history.getCode() + "', '" + history.getProject_id()  + "', '"+ history.getProject_name() + "', '"+ history.getDate() + "')";
//            }
//            DBManager.execSQL(db, sqlStr);
//        } catch (Exception e) {
//            Log.e("saveHistoryToDB", e.getMessage());
//        }
//    }

    /**
     * 保存消息表数据
     * @param message
     */
    private void saveMessageToDB(SQLiteDatabase db, MessageItemData message) {
        if (null == db || null == message) return;
        boolean hasInserted = false;
        try {

            Cursor cursor = db.query(DB_TABLE_MESSAGE, new String[]{DB_TABLE_MESSAGE_MESSAGEID},
                    DB_TABLE_MESSAGE_MESSAGEID+"=?", new String[]{message.getMessageId()}, null, null, null, null);
            while (cursor.moveToNext()) {
                hasInserted = true;
                break;
            }
            cursor.close();

            String sqlStr = "";
            if (!hasInserted) {
                // insert
                sqlStr = "insert into " + DB_TABLE_MESSAGE + " values('" + message.getMessageId() + "', '" + 1  + "')";
                DBManager.execSQL(db, sqlStr);
            }
        } catch (Exception e) {
            Log.e("saveMessageToDB", e.getMessage());
        }
    }

    /**
     * 改变消息状态
     */
    public void changeMessageToDB(SQLiteDatabase db, String messageId) {
        if (null == db || null == messageId) return;
        boolean hasInserted = false;
        try {
            Cursor cursor = db.query(DB_TABLE_MESSAGE, new String[]{DB_TABLE_MESSAGE_MESSAGEID},
                    DB_TABLE_MESSAGE_MESSAGEID+"=?", new String[]{messageId}, null, null, null, null);
            while (cursor.moveToNext()) {
                hasInserted = true;
                break;
            }
            cursor.close();

            String sqlStr = "";
            if (hasInserted) {
                // update
                sqlStr = "update " + DB_TABLE_MESSAGE + " set " + DB_TABLE_MESSAGE_STATUS+ "='" + 0+"' where "+DB_TABLE_MESSAGE_MESSAGEID+"='"+messageId+"'" ;
                DBManager.execSQL(db, sqlStr);
            }
        } catch (Exception e) {
            Log.e("saveMessageToDB", e.getMessage());
        }
    }


    /**
     * 查询用户表数据token
     */
    public String  queryUser(SQLiteDatabase db){
        String sql = "select * from user";
        String token = null;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex("token");
            token = cursor.getString(index);
        }
        cursor.close();
        return token;

    }

    /**
     * 查询用户表数据code
     */
    public String  queryUserCode(SQLiteDatabase db){
        String sql = "select * from user";
        String code = null;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex("code");
            code = cursor.getString(index);
        }
        cursor.close();
        return code;

    }

    /**
     * 查询用户表数据projectId
     */
    public String  queryUserProjectId(SQLiteDatabase db){
        String sql = "select * from user";
        String projectId = null;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex("project_id");
            projectId = cursor.getString(index);
        }
        cursor.close();
        return projectId;

    }

    /**
     * 查询用户表数据type
     */
    public Integer  queryUserType(SQLiteDatabase db){
        String sql = "select * from user";
        Integer type = null;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex("type");
            type = cursor.getInt(index);
        }
        cursor.close();
        return type;

    }

    /**
     * 查询历史表数据project_id，project_name
     */
    public List<HistoryTableEntity> queryHistory(SQLiteDatabase db){
        String sql = "select distinct project_id,project_name,history_time from history order by history_time desc";
        List<HistoryTableEntity> projectInfo =  new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        HistoryTableEntity temhis = null;
        while (cursor.moveToNext()){
            temhis = new HistoryTableEntity();
            temhis.setProjectId(cursor.getString(0));
            temhis.setProjectName(cursor.getString(1));
            projectInfo.add(temhis);
        }
        cursor.close();
        return projectInfo;
    }

    /**
     * 查询消息表数据状态
     */
    public Integer  queryMessageStatus(SQLiteDatabase db, MessageItemData message){
        String sql = "select * from message where messageId = '"+message.getMessageId()+"'";
        Integer status = null;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex("status");
            status = cursor.getInt(index);
        }
        cursor.close();
        return status;

    }

}
