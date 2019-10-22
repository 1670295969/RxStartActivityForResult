# RxStartActivityForResult
startActivity流程与RxJava结合 简化流程,不用每次都写onActivityForResult
使用示例：
#### 使用示例： 
```
RxActivityResultStart
.on(this)//可以是fragment 也可以是activity
.withIntent(101,ThirdActivity::class.java)
.go()
.subscribe { Toast.makeText(context,"StartFragment", Toast.LENGTH_SHORT).show() }
```
