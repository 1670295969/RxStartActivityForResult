package liu.loading.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import liu.loading.rxresult.RxActivityResultStart

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_fragment.setOnClickListener {
            startActivity(Intent(this,StartFragmentActivity::class.java))
        }

        btn_activity.setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))
        }
    }
}
