package com.crec.shield.global;

import android.content.Intent;

/**
 * Created by diaokaibin@gmail.com on 2017/4/27.
 */

public class AppConstant {

    public static final String FILE_NAME = "road_share_data";

    public static final Boolean isDebug = true;

    public interface SP {
        String onduty_info_updata_commit = "onduty_info_updata_commit";
    }

    public interface LOGINSTATUS {

        String code = "code";
        String username = "username";
        String password = "password";
        String project_id = "project_id";
        String project_name = "project_name";
        String company_id = "company_id";
        String company_name = "company_name";
        String type = "type";
        String token = "token";
    }

    public interface ALARM {
        String switchset = "switchset";
    }

    public interface MESSAGE{
        String flag = "0";
        String AlarmMoveize = "AlarmMoveize";
        String AlarmRiskLevel = "AlarmRiskLevel";
        String MessageCount = "MessageCount";
        String MessageType = "0";
    }

    public interface PROJECT{
        String  lineId  = "lineId" ;
        String projectId = "projectId";
        String lineTag = "lineTag";
        String CameraPlayerView = "CameraPlayerView";
    }


}
