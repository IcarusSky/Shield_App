package com.crec.shield.ui2_2.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.crec.shield.R;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.QualityUploadContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.presenter.QualityUploadPresenter;
import com.crec.shield.utils.GifSizeFilter;
import com.crec.shield.utils.SysApplication;
import com.crec.shield.utils.T;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class QualityUploadActivity extends BaseActivity<QualityUploadPresenter> implements QualityUploadContract.View {

    private Unbinder unbinder;
    private String string1;
    private String string2;
    private List<Uri> mSelected;
    //调用系统相册-选择图片
    private static final int IMAGE = 1;

    private static final int REQUEST_CODE_CHOOSE = 23;
    @BindView(R.id.et_up1)
    EditText mEtUp1;
    @BindView(R.id.et_up2)
    EditText mEtUp2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_quality_upload;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        unbinder = ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_upload_config)
    public void onViewClicked() {
        string1 = mEtUp1.getText().toString().trim();
        string2 = mEtUp2.getText().toString().trim();
        mPresenter.getData();
    }

    @OnClick(R.id.btn_pick)
    public void pick() {
        //调用相册
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE);

      /*  GalleryConfig config = new GalleryConfig.Build()
                .limitPickPhoto(3)
                .singlePhoto(false)
                .hintOfPick("this is pick hint")
                .filterMimeTypes(new String[]{"image/jpeg"})
                .build();
        GalleryActivity.openActivity(QualityUploadActivity.this, 1, config);
        */

/*        Matisse.from(QualityUploadActivity.this)
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .maxSelectable(9)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);*/

/*                .choose(MimeType.ofAll(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider","test"))
                .maxSelectable(9)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(REQUEST_CODE_CHOOSE);*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/*        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
        }*/



        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {


            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    //加载图片
    private void showImage(String imaePath){
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        ((ImageView)findViewById(R.id.image)).setImageBitmap(bm);
    }



    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //断开View引用
        mPresenter.detachView();
        unbinder.unbind();
    }

    @Override
    public void showToast(String msg) {
        T.showLong(this, msg);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
