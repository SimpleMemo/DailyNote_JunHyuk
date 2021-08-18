package com.junhyuk.dailynote.view.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import androidx.lifecycle.liveData
import com.junhyuk.dailynote.R
import com.junhyuk.dailynote.model.database.MemoDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
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
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val widgetName = context?.packageName?.let { ComponentName(it, DailyWidgetProvider::class.java.name) }
        val widgetIds = appWidgetManager.getAppWidgetIds((widgetName))

        myScope.launch(Dispatchers.IO) {

            memoDao.getAll().collectLatest {
                title = it[0].memoTitle
                content = it[0].memoContent

                if(widgetIds.isNotEmpty()){
                    this@DailyWidgetProvider.onUpdate(context, appWidgetManager, widgetIds)
                }
            }

        }
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val widgetName = context?.packageName?.let { ComponentName(it, DailyWidgetProvider::class.java.name) }
        val widgetIds = appWidgetManager.getAppWidgetIds((widgetName))

        myScope.launch(Dispatchers.IO) {

            memoDao.getAll().collectLatest {
                title = it[0].memoTitle
                content = it[0].memoContent

                if(widgetIds.isNotEmpty()){
                    this@DailyWidgetProvider.onUpdate(context, appWidgetManager, widgetIds)
                }
            }

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

}