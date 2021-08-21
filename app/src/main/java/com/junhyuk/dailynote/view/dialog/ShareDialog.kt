package com.junhyuk.dailynote.view.dialog

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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.junhyuk.dailynote.R
import com.junhyuk.dailynote.databinding.DialogShareBinding
import com.junhyuk.dailynote.model.`object`.MemoObject
import com.junhyuk.dailynote.model.database.MemoData
import com.junhyuk.dailynote.model.repository.MemoRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

/*
*
* 파일명: ShareDialog
* 역할: 정말 공유할 것인지 묻는 Dialog, 이미지 파일을 공유 및 갤러리에 저장
* 작성자: YangJunHyuk333
*
* */

@AndroidEntryPoint
class ShareDialog : BottomSheetDialogFragment() {

    //binding, viewModel, viewModelFactory 선언
    private val binding by lazy { DialogShareBinding.inflate(layoutInflater) }

    //Inject
    @Inject
    lateinit var repository: MemoRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //view 접근
        with(binding) {

            //shareButtonClickAction
            shareButton.setOnClickListener {

                //Bitmap 저장 및 공유
                val uri = saveToGallery(requireActivity(), MemoObject.bitmap!!)
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/png"
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                startActivity(Intent.createChooser(intent, "Share"))
                Toast.makeText(context, "갤러리에 일기가 저장되었습니다.", Toast.LENGTH_SHORT).show()

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

        //안드로이드 버전이 Q 이상일 때
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_DCIM}/$albumName")
            }

            val uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ) ?: return Uri.EMPTY

            context.contentResolver.openOutputStream(uri)?.let(write)
            return uri

        } else { //안드로이드 버전이 Q 미만일 때

            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + albumName

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
        when (MemoObject.state) {
            "UPDATE" -> {
                CoroutineScope(Dispatchers.IO).launch {
                    update(MemoData(MemoObject.id, MemoObject.title, MemoObject.content))
                }
            }

            //MemoObject.state == INSERT
            "INSERT" -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val now: Long = System.currentTimeMillis()
                    insert(MemoData(now.toInt(), MemoObject.title, MemoObject.content))
                }
            }

            //만약 아무런 값도 입력이 안되어 있다면
            else -> {
                Toast.makeText(context, "오류가 생겼습니다. 개발자에게 문의해주세요!", Toast.LENGTH_LONG).show()
                requireActivity().finish()
            }
        }
    }

    //viewModel 로
    //Memo DB 수정
    private suspend fun update(memo: MemoData) {
        repository.update(memo)
    }

    //Memo DB 삽입
    private suspend fun insert(memo: MemoData) {
        repository.insert(memo)
    }

}