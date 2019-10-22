package liu.loading.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.start_fragment.*
import liu.loading.rxresult.RxActivityResultStart

class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context!!).inflate(R.layout.start_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_start_3.setOnClickListener {
            RxActivityResultStart.on(this).withIntent(101,ThirdActivity::class.java).go()
                .subscribe {
                    Toast.makeText(context,"StartFragment", Toast.LENGTH_SHORT).show()
                }
        }
    }


}