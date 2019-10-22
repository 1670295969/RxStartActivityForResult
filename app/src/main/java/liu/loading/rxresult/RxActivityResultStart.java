package liu.loading.rxresult;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class RxActivityResultStart {

    public static RxStartResultRequest on(Fragment fragment){
        return new RxStartResultRequest(fragment);
    }

    public static RxStartResultRequest on(FragmentActivity activity){
        return new RxStartResultRequest(activity);
    }



}
