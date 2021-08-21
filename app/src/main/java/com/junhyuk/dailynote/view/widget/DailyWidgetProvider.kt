package com.junhyuk.dailynote.view.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import com.junhyuk.dailynote.R
import com.junhyuk.dailynote.model.database.MemoDao
import com.junhyuk.dailynote.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class DailyWidgetProvider : AppWidgetProvider() {

    private val job = SupervisorJob()
    private val myScope = CoroutineScope(Dispatchers.Main + job)

    private var title = ""
    private var content = ""

    @Inject
    lateinit var memoDao: MemoDao

    //위젯 갱신 주기에 따라 위젯을 갱신할 때 호출
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        val views = RemoteViews(context?.packageName, R.layout.widget_daily)
        views.setTextViewText(R.id.widgetTitle, title)
        views.setTextViewText(R.id.widgetContent, content)
        appWidgetManager?.updateAppWidget(appWidgetIds, views)

        //앱 실행
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.component = ComponentName(context!!, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        views.setOnClickPendingIntent(R.id.widget, pendingIntent)
        appWidgetManager?.updateAppWidget(appWidgetIds, views)

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(ComponentName(context!!, DailyWidgetProvider::class.java))
        val myWidget = DailyWidgetProvider()
        myWidget.onUpdate(context, AppWidgetManager.getInstance(context), ids)

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val widgetName = context.packageName?.let { ComponentName(it, DailyWidgetProvider::class.java.name) }
        val widgetIds = appWidgetManager.getAppWidgetIds((widgetName))

        getDiaryData(appWidgetManager, widgetIds, context)

    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val widgetName = context?.packageName?.let { ComponentName(it, DailyWidgetProvider::class.java.name) }
        val widgetIds = appWidgetManager.getAppWidgetIds((widgetName))

        if (context != null) {
            getDiaryData(appWidgetManager, widgetIds, context)
        }

    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        myScope.cancel()
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }

    private fun getDiaryData(appWidgetManager: AppWidgetManager, widgetIds: IntArray, context: Context){
        myScope.launch(Dispatchers.IO) {

            memoDao.getAll().collectLatest {
                if(it.isNotEmpty()){
                    title = it[0].memoTitle
                    content = it[0].memoContent
                }else{
                    title = "작성된 일기가 없습니다."
                    content = "작성된 일기가 없습니다."
                }

                if(widgetIds.isNotEmpty()){
                    this@DailyWidgetProvider.onUpdate(context, appWidgetManager, widgetIds)
                }
            }

        }
    }

}