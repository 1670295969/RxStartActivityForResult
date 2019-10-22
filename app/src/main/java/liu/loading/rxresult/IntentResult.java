package liu.loading.rxresult;

import android.content.Intent;

public class IntentResult {

    public int requestCode;
    public int resultCode;
    public Intent resultIntent;

    public static IntentResult create(int requestCode, int resultCode, Intent intent) {
        IntentResult res = new IntentResult();
        res.requestCode = requestCode;
        res.resultCode = resultCode;
        res.resultIntent = intent;
        return res;

    }


}
