package com.crec.shield.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.crec.shield.R;
import com.crec.shield.base.AppComponent;
import com.crec.shield.base.AppModule;
import com.crec.shield.base.DaggerAppComponent;
import com.facebook.stetho.Stetho;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import org.litepal.LitePal;

import java.util.logging.Level;


public class IstuaryGlobal extends Application {

    private static IstuaryGlobal mInst;
    private AppComponent mAppcomponent;
    public static Context context;
    public static Handler mainHandler;
    private static final String TAG = "tag";
    static {
        //设置全局的Header构建器 防止内存溢出（可以在初始化中设置\可以在布局和代码中设置刷新样式）
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //全局设置主题颜色
                layout.setPrimaryColorsId(R.color.white, R.color.text_com_color);
                //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));
                // 指定为经典Header，默认是 贝塞尔雷达Header
                return new ClassicsHeader(context);

            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mainHandler = new Handler();
        context = getApplicationContext();
        initLog();
        initOkGo();
        initBugly();
        initFaceBook();
        initOkGo();
        initSQL();
        mInst = this;
        mAppcomponent = DaggerAppComponent.     //Dagger +自己定义的component名字
                builder()
                .appModule(new AppModule(this))  // 自己定义的App级别Module的名字
                .build();
    }

    public static IstuaryGlobal getInst() {
        return mInst;
    }

    public AppComponent getAppComponent() {
        return mAppcomponent;
    }
    private void initFaceBook() {
        Stetho.initializeWithDefaults(this);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    private void initLog() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }


    private void initSQL() {

        LitePal.initialize(this);
    }


    private void initBugly() {
        //Bugly.init(context, "8f43391147", false);
        //Bugly.init(context, "876384d4a1", false); // 正式
        //Bugly.init(context, "625c3f5b12", false);

    }



    private void initOkGo() {

        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文
//        headers.put("Accept-Language", contentLanguage);
//        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey2", "这里支持中文参数");
        //-----------------------------------------------------------------------------------//

        //必须调用初始化
        OkGo.init(this);

        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()

                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, false)

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(15000)  //全局的连接超时时间
                    .setReadTimeOut(15000)     //全局的读取超时时间
                    .setWriteTimeOut(15000)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)

                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
                    .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
//                    .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

                    //可以设置https的证书,以下几种方案根据需要自己设置
                    .setCertificates();                                  //方法一：信任所有证书,不安全有风险
//              .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
//              .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
//              //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//               .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//

            //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//               .setHostnameVerifier(new SafeHostnameVerifier())

            //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })

            //这两行同上，不需要就不要加入
//                    .addCommonHeaders(headers)  //设置全局公共头
//                    .addCommonParams(params);   //设置全局公共参数

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }
    public static Context getContext() {
               return context;
        }
}

