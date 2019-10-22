package liu.loading.rxresult;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.reactivex.subjects.BehaviorSubject;

public class StartResultFragment extends Fragment {

    private volatile boolean isInited = false;

    /**
     * 这个参数作用是首次fragment创建还未加入到相应的 fragmentmanager中临时做保留的，
     */
    private IntentResult startForResult;
    private final BehaviorSubject<IntentResult> mActivityResultSubject = BehaviorSubject.create();


    public BehaviorSubject<IntentResult> getResultSubject() {
        return mActivityResultSubject;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isInited = true;
        if (startForResult != null) {
            startActivityForResult(startForResult.resultIntent, startForResult.requestCode);
        }
        startForResult = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mActivityResultSubject.onNext(IntentResult.create(requestCode, resultCode, data));
    }

    public void startForResult(Intent intent, int requestCode) {
        if (isInited) {
            startActivityForResult(intent, requestCode);
        } else {
            startForResult = IntentResult.create(requestCode, 0, intent);
        }


    }
}
