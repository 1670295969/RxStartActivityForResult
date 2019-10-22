package liu.loading.rxresult;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * 请求的requestCode 不能一致
 */
public class RxStartResultRequest implements Handler.Callback {


    private static final String FRAGMENT_TAG = "RxStartResultRequest";

    public static int TYPE_START_FOR_ACTIVITY = 1;
    public static int TYPE_START_FOR_FRAGMENT = 2;

    private static int ID_REMOVE_SUPPORT_FRAGMENT_MANAGER = 11;

    FragmentActivity mActivity;
    Fragment mFragment;
    Intent sendIntent;
    int fromType;
    int requestCode;
    private Context mContext;

    private Handler mHandler;

    private Map<FragmentManager,StartResultFragment> paddingFragmentMap = new HashMap<>();
    private RxStartResultRequest(){
        mHandler = new Handler(Looper.getMainLooper(),this);
    }

    public RxStartResultRequest(FragmentActivity activity) {
        this();
        mActivity = activity;
        mContext = mActivity;
        fromType = TYPE_START_FOR_ACTIVITY;
    }

    public RxStartResultRequest(Fragment fragment) {
        this();
        mFragment = fragment;
        mContext = mFragment.getContext();
        fromType = TYPE_START_FOR_FRAGMENT;
    }

    public RxStartResultRequest withIntent(int requestCode, Class<?> activityClazz) {
        return withIntent(requestCode, new Intent(mContext, activityClazz));
    }

    public RxStartResultRequest withIntent(int requestCode, Intent intent) {
        this.requestCode = requestCode;
        this.sendIntent = intent;
        return this;
    }

    /**
     * 在合适的位置可以取消订阅
     * @return
     */
    public Observable<IntentResult> go() {
        try{
            StartResultFragment resultFragment = startForResult();
            return resultFragment.getResultSubject().filter(new Predicate<IntentResult>() {
                @Override
                public boolean test(IntentResult intentResult) throws Exception {
                    return requestCode == intentResult.requestCode;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return Observable.empty();
    }

    private FragmentManager getFragmentManager() {
        if (fromType == TYPE_START_FOR_ACTIVITY) {
            return mActivity.getSupportFragmentManager();
        } else {
            return mFragment.getChildFragmentManager();
        }
    }


    private StartResultFragment startForResult() {
        StartResultFragment resultFragment;
        FragmentManager fm = getFragmentManager();
        resultFragment = getAndInsertFragment(fm);
        resultFragment.startForResult(sendIntent,requestCode);
        return resultFragment;
    }

    private StartResultFragment getAndInsertFragment(FragmentManager fm) {
        StartResultFragment fragment = (StartResultFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if(fragment == null){
            //这个地方是仿照Glide写的
            fragment = paddingFragmentMap.get(fm);
        }
        if (fragment == null){
            fragment = new StartResultFragment();
            paddingFragmentMap.put(fm,fragment);
            fm.beginTransaction().add(fragment,FRAGMENT_TAG).commitAllowingStateLoss();
            mHandler.obtainMessage(ID_REMOVE_SUPPORT_FRAGMENT_MANAGER,fm).sendToTarget();
        }
        return fragment;
    }


    @Override
    public boolean handleMessage(Message msg) {
        if(msg.what == ID_REMOVE_SUPPORT_FRAGMENT_MANAGER){
            FragmentManager fragmentManager = (FragmentManager) msg.obj;
            paddingFragmentMap.remove(fragmentManager);
            return true;
        }
        return false;
    }
}
