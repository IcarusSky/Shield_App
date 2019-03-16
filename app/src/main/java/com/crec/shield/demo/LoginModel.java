package com.crec.shield.demo;

import com.crec.shield.base.Callback;
import com.crec.shield.entity.login.DataLogin;
import com.crec.shield.entity.login.LoginResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.utils.Base64Util;
import com.crec.shield.utils.SPUtils;
import com.lzy.okgo.OkGo;

import okhttp3.Call;
import okhttp3.Response;

public class LoginModel implements LoginContract.Model{

    private static DataLogin dataLogin;

    @Override
    public void getNetData(final String user, final String password, final String cid, final Callback callback){
        final String userUtil = Base64Util.encodeData(user);
        final String passwordUtil = Base64Util.encodeData(password);
        OkGo.post(Url.BASE_URL + Url.LOGIN)
                .params("loginName", userUtil)
                .params("password", passwordUtil)
                .params("cid", cid)
                .execute(new JsonCallback<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse loginResponse, Call call, Response response) {
                        if (loginResponse.getCode()== 1) {
                            dataLogin = loginResponse.getData();
                            String mToken = dataLogin.getToken();
                            String code = dataLogin.getCode();
                            String username= dataLogin.getUsername();
                            String orgId= dataLogin.getCompany_id();
                            Integer type= dataLogin.getType();
                            SPUtils.put(AppConstant.LOGINSTATUS.code, code);
                            SPUtils.put(AppConstant.LOGINSTATUS.token, mToken);
                            SPUtils.put(AppConstant.LOGINSTATUS.username, username);
                            SPUtils.put(AppConstant.LOGINSTATUS.password, passwordUtil);
                            SPUtils.put(AppConstant.LOGINSTATUS.type, type);
                            SPUtils.put(AppConstant.LOGINSTATUS.company_id, orgId);
                            if (type == 0 || type == 1 || type == 3) {
                                String company_name = dataLogin.getCompany_name();
                                SPUtils.put(AppConstant.LOGINSTATUS.company_name,company_name);
                            }else if(type == 2 || type == 4) {
                                String project_id= dataLogin.getProject_id();
                                String project_name = dataLogin.getProject_name();
                                SPUtils.put(AppConstant.LOGINSTATUS.project_id, project_id);
                                SPUtils.put(AppConstant.PROJECT.projectId, project_id); // 测试用
                                SPUtils.put(AppConstant.PROJECT.lineId, "8fc7d0e5ed2649188377bd381fdd3c7f"); // 测试用
                                SPUtils.put(AppConstant.LOGINSTATUS.project_name,project_name);
                            }
                            callback.onSuccess(dataLogin);
                            callback.onComplete();
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callback.onFailure("请求失败：参数有误");
                        callback.onComplete();
                    }
                });
    }
}








