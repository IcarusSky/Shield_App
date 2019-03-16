package com.crec.shield.network;

public class Url {


   public static final String BASE_URL = "http://10.0.0.14:8082";//正式库



    public static final String LOGIN = "/v1/mobile/user/login";
    public static final String LOGOUT= "/v1/mobile/user/logout";

    public static final String FEED_BACK = "/v1/mobile/setting/feedback/submit";

    public static final String MESSAGE_MESSAGE_LIST = "/v1/mobile/message/getMessageList";//获取消息
    public static final String MESSAGE_ADD_MESSAGE_LIST = "/v1/mobile/message/addMessageIsRead";// 添加消息已读记录

    public static final String OVERVIEW_CAMERA_AREA_CONDITION = "/v1/mobile/overview/cameras/getAreaCondition";//获取片区查询条件
    public static final String OVERVIEW_CAMERA_LINE_CONDITION= "/v1/mobile/overview/cameras/getLineCondition"; //获取线路查询条件
    public static final String OVERVIEW_CAMERA_PROJECT_CONDITION= "/v1/mobile/overview/cameras/getProjectCondition"; //获取项目查询条件
    public static final String OVERVIEW_CAMERA_CONDITION= "/v1/mobile/overview/cameras/getCameraByCondition";//获取出该某条线路底下用户关注的摄像头

    public static final String OVERVIEW_FOLLOW_CAMERA = "/v1/mobile/overview/follow/getFollowCamera";   //获取用户关注摄像头
    public static final String OVERVIEW_FOLLOW_PROJECT = "/v1/mobile/overview/follow/getFollowProject"; //获取用户关注项目
    public static final String OVERVIEW_FOLLOW_ADD_CAMERA_ATTENTION = "/v1/mobile/overview/follow/addCameraAttention"; //添加摄像头的关注
    public static final String OVERVIEW_FOLLOW_ADD_PROJECT_ATTENTION = "/v1/mobile/overview/follow/addProjectAttention"; //添加项目的关注
    public static final String OVERVIEW_FOLLOW_REMOVE_CAMERA_ATTENTION = "/v1/mobile/overview/follow/removeCameraAttention"; // 取消摄像头的关注
    public static final String OVERVIEW_FOLLOW_REMOVE_PROJECT_ATTENTION = "/v1/mobile/overview/follow/removeProjectAttention";  // 取消项目的关注

    public static final String OVERVIEW_FUTURE_AWEEK_RISKRANKING = "/v1/mobile/overview/getFuture7DaysRiskRanking";//获取概览界面未来7天的风险源排名
    public static final String OVERVIEW_FUTURE_AWEEK_RISKSTATISTICS = "/v1/mobile/overview/getFuture7DaysRiskStatistics";//获取概览界面未来7天的风险源统计
    public static final String OVERVIEW_PROGRESS_RANK_OF_DAY = "/v1/mobile/overview/getOverviewProgressRankOfDay";//获取概览界面掘进功效日排名
    public static final String OVERVIEW_PROGRESS_RANK_OF_MONTH = "/v1/mobile/overview/getOverviewProgressRankOfMonth";//获取概览界面掘进功效月排名
    public static final String OVERVIEW_PROGRESS = "/v1/mobile/overview/getOverviewProgressStatistics"; //获取进度统计数据
    public static final String OVERVIEW_START_ARRIVALS = "/v1/mobile/overview/getOverviewStartArrivals";//始发到达


    public static final String PROJECT_PROGRESS_TODAYRING = "/v1/mobile/project/progress/getpercent";//根据项目id和线路id得到某一条线路的进度完成百分比数据
    public static final String PROJECT_PROGRESS_RINGRATIO = "/v1/mobile/project/progress/getComparedPercent";//根据项目id和线路id得到某一条线路的进度环比数据
    public static final String PROJECT_PROGRESS_OVERVIEW = "/v1/mobile/project/progress/getOverView";//根据项目id和线路id得到某一条线路的总体概览数据
    public static final String PROJECT_PROGRESS_SHIELD_GANTT = "/v1/mobile/project/progress/getShieldGantt";//获取盾构机甘特图
    public static final String PROJECT_PROGRESS_WORK_EFFICIENCY = "/v1/mobile/project/progress/getWorkEfficiency";//根据项目id和线路id得到某一条线路的白夜班工效统计图数据
    public static final String PROJECT_CAMERA = "/v1/mobile/project/camera/getCameraList"; //根据某条线路id，获取该线路的所有的摄像机信息
    public static final String PROJECT_QUALITY =  "/v1/mobile/project/quality/getQualityList"; //获取线路的换片质量


    public static final String PROJECT_RISK_CURRENCY =  "/v1/mobile/project/risk/getCurrencyRisk"; //获取线路的当前风险点和临近风险点
    public static final String PROJECT_RISK_GROUND =  "/v1/mobile/project/risk/getGroundSetting"; //获取线路的地面沉降图数据
    public static final String PROJECT_RISK_LIST =  "/v1/mobile/project/risk/getRiskList"; //获取线路的风险源列表
    public static final String PROJECT_RISK_GANTT =  "/v1/mobile/project/risk/getRiskResGanttData"; //获取风险源甘特图


    public static final String PROJECT_SEARCH_CONDITION = "/v1/mobile/project/search/getSearchCondition";  //搜索条件
    public static final String PROJECT_SEARCH_RESULT = "/v1/mobile/project/search/getSearchResult";//搜索结果
    public static final String PROJECT_ADD_SEARCH_HISTORY = "/v1/mobile/project/search/addSearchHistoryResult";// 添加历史查询结果
    public static final String PROJECT_GET_SEARCH_HISTORY = "/v1/mobile/project/search/getSearchHistoryResult";//  获取查询历史记录

    public static final String PROJECT_STATUS_DUCTPIECE_H = "/v1/mobile/project/status/getDuctPieceHorizontalData"; //获取盾构机水平偏差前点
    public static final String PROJECT_STATUS_DUCTPIECE_V = "/v1/mobile/project/status/getDuctPieceVerticalData";//APP端获取盾构机垂直偏差后点
    public static final String PROJECT_STATUS_POINT_PARAMETER = "/v1/mobile/project/status/getPointParameter";//APP端获取盾构机点位信息
    public static final String PROJECT_STATUS_REAL = "/v1/mobile/project/status/getRealStatus";//APP端获取盾构机状态概览
    public static final String PROJECT_STATUS_POINTITEM = "/v1/mobile/project/status/getPointItem";//APP端获取盾构机点位参数

 //2.2版本新添加接口
    public static final String COMPANY_MANAGER_DATA = "/v1/mobile/company/pandect/getManagerData";//获取公司管理数据
    public static final String COMPANY_GROUP_POINT_PARAMETER = "/v1/mobile/project/status/getGroupPointParameter";//获取公司管理数据


}

