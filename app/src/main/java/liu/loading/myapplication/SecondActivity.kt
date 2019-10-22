package liu.loading.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import liu.loading.rxresult.RxActivityResultStart

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        btn_activity.setOnClickListener {
            RxActivityResultStart
                .on(this)
                .withIntent(102,ThirdActivity::class.java)
                .go()
                .subscribe {
                    Toast.makeText(this,"SecondActivity",Toast.LENGTH_SHORT).show()
                }
        }
    }
}
