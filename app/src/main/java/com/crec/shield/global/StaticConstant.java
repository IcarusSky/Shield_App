package com.crec.shield.global;

public class StaticConstant {


    public static final String TITLES_ID_1 = "进度";
    public static final String TITLES_ID_2 = "状态";
    public static final String TITLES_ID_3 = "视频";
    public static final String TITLES_ID_4 = "风险";
    public static final String TITLES_ID_5 = "质量";

    // 实时视频
    public static final String CAMERA_WITHOUT_PROJECT = "暂无开工项目";

    public static final String APPROACHING_ARRIVAL_PROMPT = "到达提示：";
    public static final String APPROACHING_START_PROMPT = "始发提示：";
    public static final String APPROACHING_ARRIVAL = "即将到达";
    public static final String APPROACHING_HAVING_ARRIVED = "已经到达";
    public static final String APPROACHING_HAVING = "已于";
    public static final String APPROACHING_ARRIVED = "到达";
    public static final String APPROACHING_START = "即将动工";
    public static final String REMAINING_RING = "剩余";
    public static final String ESTIMMATE = "预计";
    public static final String ARRIVAL = "到达";
    public static final String DISTANCE_ARRIVAL = "距离到达还有";
    public static final String DAY = "天";
    public static final String RING = "环";
    public static final String TOTAL_RING = "总计";
    public static final String START_DATE = "预计开始时间：";
    public static final String PROPEL_RANK_DATA_DAY = "day";
    public static final String PROPEL_RANK_DATA_MONTH = "month";
    public static final String PROPEL_RANK_DATA_YESTODAY = "yestoday";
    public static final String PROPEL_RANK_DATA_SEVENDAY = "sevenday";
    public static final String WARN_MOVESIZE = "导向偏移警告";
    public static final String WARN_RISK = "风险源警告";
    public static final String _MOVESIZE = ":偏移量";
    public static final String _MOVESIZ_UNIT = "mm";
    public static final String _RISK_LEVEL = "：风险源等级";
    public static final String WITHOUT_DATA = "暂无数据";
    public static final String WITHOUT_LINE_DATA = "该项目下暂无已开工线路";

    public static final String ADD_CAMERA_ATTENTION_TOAST = "您已关注该摄像机!";
    public static final String FAILED_ADD_CAMERA_ATTENTION_TOAST = "关注该摄像机失败!请检查您的网络";
    public static final String REMOVE_CAMERA_ATTENTION_TOAST = "您已取消关注该摄像机!";
    public static final String FAILED_REMOVE_CAMERA_ATTENTION_TOAST = "取消关注该摄像机失败!请检查您的网络";
    public static final String ADD_PROJECT_ATTENTION_TOAST = "您已关注该项目!";
    public static final String FAILED_ADD_PROJECT_ATTENTION_TOAST = "关注该项目失败!请检查您的网络";
    public static final String REMOVE_PROJECT_ATTENTION_TOAST = "您已取消关注该项目!";
    public static final String FAILED_REMOVE_PROJECT_ATTENTION_TOAST = "取消关注该项目失败!请检查您的网络";
    public static final String FAILED_ACTION_TOAST = "操作失败！";

    public static final String ACCOUNT_NUMBER = "账号：";
    public static final Integer ALL = 1;
    public static final Integer EXCEPTION = 0;

    public static final String DB_NAME = "crec_shield";     //  数据库名称
    public static final int DB_VERSION = 1;                  //  默认版本1

    public static final String DB_TABLE_USER = "user";                          //表名
    public static final String DB_TABLE_USER_TOKEN = "token";
    public static final String DB_TABLE_USER_CODE = "code";                     //用户code
    public static final String DB_TABLE_USER_NAME = "username";                //用户名字
    public static final String DB_TABLE_USER_PASSWORD = "password";           //密码
    public static final String DB_TABLE_USER_PROJECT_ID = "project_id";
    public static final String DB_TABLE_USER_TYPE = "type";                    //项目级或者公司级

    public static final String DB_TABLE_HISTORY = "history";                   // 历史记录表名
    public static final String DB_TABLE_HISTORY_CODE = "code";                  // 用户code
    public static final String DB_TABLE_HISTORY_PROJECT_ID = "project_id";    // 项目id
    public static final String DB_TABLE_HISTORY_PROJECT_NAME = "project_name";    // 项目id
    public static final String DB_TABLE_HISTORY_TIME = "history_time";        // 记录时间

    public static final String DB_TABLE_MESSAGE = "message";                   // 消息表名
    public static final String DB_TABLE_MESSAGE_MESSAGEID = "messageId";      // 消息id
    public static final String DB_TABLE_MESSAGE_STATUS = "status";                 // 消息状态（已读/未读）

    public static final String DB_TABLE_HISTORY_NO = "暂无数据";        // 记录时间

}
