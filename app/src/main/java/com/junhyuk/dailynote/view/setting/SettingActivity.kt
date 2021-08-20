package com.junhyuk.dailynote.view.setting

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.junhyuk.dailynote.R
import com.junhyuk.dailynote.application.MyApplication
import com.junhyuk.dailynote.databinding.ActivitySettingBinding
import com.junhyuk.dailynote.model.`object`.ThemeManager

/*
*
* 파일명: SettingActivity
* 역할: Android Application 설정을 하는 곳
* 작성자: YangJunHyuk333
*
* */

class SettingActivity : AppCompatActivity() {

    //binding 선언
    private val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //view 접근
        with(binding) {
            
            //뒤로가기 버튼
            backButton.setOnClickListener {
                finish()
            }

            //현재 테마 상태
            if(MyApplication.pref.darkModeState != ThemeManager.DEFAULT){
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_NO -> lightCheck.isChecked = true //lightMode 라면 lightCheck 버튼 활성화
                    Configuration.UI_MODE_NIGHT_YES -> darkCheck.isChecked = true //darkMode 라면 darkCheck 버튼 활성화
                }
            }else systemCheck.isChecked = true //위의 항목에 해당되지 않는다면 systemCheck 버튼 활성화

            //테마 설정 선택
            selectedTheme.setOnCheckedChangeListener { group, checkedId ->
                when(checkedId){

                    //lightMode
                    R.id.lightCheck -> {
                        group.check(R.id.lightCheck)
                        setTheme(ThemeManager.LIGHT)
                    }

                    //darkMode
                    R.id.darkCheck -> {
                        group.check(R.id.darkCheck)
                        setTheme(ThemeManager.DARK)
                    }

                    //systemMode
                    R.id.systemCheck -> {
                        group.check(R.id.systemCheck)
                        setTheme(ThemeManager.DEFAULT)
                    }

                }

            }

        }
    }

    private fun setTheme(themeState: String){
        ThemeManager.applyTheme(themeState)
        MyApplication.pref.darkModeState = themeState
    }

}