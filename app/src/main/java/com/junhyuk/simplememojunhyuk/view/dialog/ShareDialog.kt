package com.junhyuk.simplememojunhyuk.view.dialog

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.junhyuk.simplememojunhyuk.R
import com.junhyuk.simplememojunhyuk.application.MyApplication
import com.junhyuk.simplememojunhyuk.databinding.DialogShareBinding
import com.junhyuk.simplememojunhyuk.model.`object`.MemoObject
import com.junhyuk.simplememojunhyuk.model.database.MemoData
import com.junhyuk.simplememojunhyuk.viewmodel.dialog.ShareDialogViewModel
import com.junhyuk.simplememojunhyuk.viewmodel.dialog.ShareDialogViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/*
*
* 파일명: ShareDialog
* 역할: 정말 공유할 것인지 묻는 Dialog
* 작성자: YangJunHyuk333
*
* */

class ShareDialog : BottomSheetDialogFragment() {

    //binding, viewModel, viewModelFactory 선언
    private lateinit var binding: DialogShareBinding
    private lateinit var viewModel: ShareDialogViewModel
    private lateinit var viewModelFactory: ShareDialogViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //dataBinding 설정
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_share, container, false)

        //viewModel 설정
        viewModelFactory = ShareDialogViewModelFactory()
        viewModel = ViewModelProvider(this@ShareDialog, viewModelFactory).get(ShareDialogViewModel::class.java)

        //제목과 내용을 Object 에 입력, bitmap, state 랑 dataIndex 저장
        viewModel.apply {
            if(MemoObject.title.isNotEmpty()) {
                setTextValue(MemoObject.title, MemoObject.content)
            }else{
                setObject()
            }

            if(MemoObject.bitmap != null){
                setBitmap(MemoObject.bitmap!!)
            }else{
                setBitmapObject()
            }

            if(stateData.value?.isNotEmpty() == true){
                setPosAndIndexObject()
            }else{
                setState(MemoObject.state)
                setDataIndex(MemoObject.dataIndex)
            }
        }

        //view 접근
        binding.apply {

            //shareButtonClickAction
            shareButton.setOnClickListener {

                //Bitmap 저장 및 공유
                val uri = saveToGallery(requireActivity(), MemoObject.bitmap!!)
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/png"
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                startActivity(Intent.createChooser(intent, "Share"))
                Toast.makeText(MyApplication.INSTANCE, "Saved", Toast.LENGTH_SHORT).show()

                insertOrUpdate() //저장 혹은 수정(수정)
                dismiss() //다이얼로그 종료
                requireActivity().finish() //Activity 종료

            }

            //cancelButtonClickAction
            cancelButton.setOnClickListener {

                insertOrUpdate() //저장 혹은 수정(수정)
                dismiss() //다이얼로그 종료
                requireActivity().finish() //Activity 종료

            }
        }

        return binding.root
    }

    //Theme 적용
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    //갤러리 저장
    private fun saveToGallery(context: Context, bitmap: Bitmap, albumName: String = "DailyNote"): Uri {
        val dateInfo = MemoObject.title
        val filename = "$dateInfo.png"
        val write: (OutputStream) -> Boolean = {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    "${Environment.DIRECTORY_DCIM}/$albumName"
                )
            }

            val uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ) ?: return Uri.EMPTY
            context.contentResolver.openOutputStream(uri)?.let(write)
            return uri

        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .toString() + File.separator + albumName
            val file = File(imagesDir)
            if (!file.exists()) {
                file.mkdir()
            }
            val image = File(imagesDir, filename)
            write(FileOutputStream(image))
            return Uri.parse(imagesDir)
        }
    }

    //Insert 혹은 Update
    private fun insertOrUpdate(){
        //MemoObject.state == UPDATE
        when (viewModel.stateData.value) {
            "UPDATE" -> {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.update(MemoObject.dataIndex, MemoObject.title, MemoObject.content)
                }
            }

            //MemoObject.state == INSERT
            "INSERT" -> {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.insert(MemoData(MemoObject.title, MemoObject.content))
                }
            }

            //만약 아무런 값도 입력이 안되어 있다면
            else -> {
                Toast.makeText(MyApplication.applicationContext(), "오류가 생겼습니다. 개발자에게 문의해주세요!", Toast.LENGTH_LONG).show()
                requireActivity().finish()
            }
        }
    }

}