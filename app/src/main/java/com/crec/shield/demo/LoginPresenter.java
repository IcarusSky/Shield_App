package com.crec.shield.demo;

import android.database.sqlite.SQLiteDatabase;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.login.DataLogin;
import com.crec.shield.global.IstuaryGlobal;
import com.crec.shield.utils.DBManager;
import com.crec.shield.utils.SqliteHelper;

import javax.inject.Inject;

import static com.crec.shield.global.StaticConstant.DB_TABLE_USER;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

  private LoginContract.Model model;
  private SqliteHelper sqlHelper;
  private SQLiteDatabase db;

  //注意，提供Presenter 的实例化对象
  @Inject
  public LoginPresenter() {
    model = new LoginModel();
  }

  @Override
  public void getData(String user, String password, String cid) {
    if (!isViewAttached()) {
          //如果没有View引用就不加载数据
          return;
      }
      //显示正在加载进度条
      mView.showLoading();
        // 调用Model请求数据
        model.getNetData(user, password, cid, new Callback<DataLogin>() {

        @Override
        public void onSuccess(DataLogin data) {
            //调用view接口显示数据
            if (isViewAttached()) {
                mView.showData(data);
                clearDBData();
                initBaseDataToDB(data);
            }
        }

        @Override
        public void onFailure(String msg) {
            //调用view接口提示失败信息
            if (isViewAttached()) {
                mView.showToast(msg);
            }
        }

        @Override
        public void onError() {
            //调用view接口提示请求异常
            if (isViewAttached()) {
                mView.showError();
            }
        }

        @Override
        public void onComplete() {
            // 隐藏正在加载进度条
            if (isViewAttached()) {
                mView.hideLoading();
            }
        }
        });
    }

    public void  clearDBData(){
        sqlHelper = DBManager.getInstance(IstuaryGlobal.getContext());
        db = sqlHelper.getWritableDatabase();
        if (null != sqlHelper && null != db) {
            clearDutyInfo(sqlHelper, db);      //  清除用户信息
        }
    }

    private void clearDutyInfo(SqliteHelper sqlHelper, SQLiteDatabase db) {
        boolean isExist = sqlHelper.checkUserInfoExist(db);
        if (isExist) {
            String sql = "delete from " + DB_TABLE_USER ;
            DBManager.execSQL(db, sql.toString());
        }
    }

    public void initBaseDataToDB(DataLogin dataLogin){
        if (null != sqlHelper && null != db) {
            sqlHelper.UserInit(db, dataLogin);
        }
    }
}