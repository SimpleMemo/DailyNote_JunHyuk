package com.junhyuk.dailynote.view.post

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.junhyuk.dailynote.R
import com.junhyuk.dailynote.databinding.FragmentPostBinding
import com.junhyuk.dailynote.model.`object`.MemoObject
import com.junhyuk.dailynote.view.dialog.ShareDialog
import com.junhyuk.dailynote.viewmodel.post.PostFragmentViewModel
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.SoftBreakAddsNewLinePlugin
import io.noties.markwon.core.MarkwonTheme

/*
*
* 파일명: PostFragment
* 역할: 메모에 입력된 정보를 DB에 저장하는 Fragment
* 작성자: YangJunHyuk333
*
* */

class PostFragment : Fragment() {

    //binding, viewModel, viewModelFactory 선언
    private lateinit var binding: FragmentPostBinding
    private lateinit var viewModel: PostFragmentViewModel

    private val typed = TypedValue()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //dataBinding 설정
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)

        //viewModel 설정
        viewModel = ViewModelProvider(this@PostFragment,).get(PostFragmentViewModel::class.java)
        binding.myViewModel = viewModel

        //theme 설정
        requireActivity().theme.resolveAttribute(R.attr.colorPrimary, typed, true)

        //state, dataIndex 저장
        if (viewModel.stateData.value?.isNotEmpty() == true) {
            viewModel.setPosAndIndexObject()
        } else {
            viewModel.apply {
                setState(MemoObject.state)
                setDataIndex(MemoObject.dataIndex)
            }
        }

        //상단 Text 를 어떤 작업을 하느냐에 따라서 변경
        when (viewModel.stateData.value) {
            "UPDATE" -> binding.titleText.text = "Update Diary"
            "INSERT" -> binding.titleText.text = "Post Diary"
        }

        //제목과 내용을 Text 에 입력
        viewModel.apply {
            if (MemoObject.title.isNotEmpty()) {

                setTextValue(MemoObject.title, MemoObject.content)
                setContentTextData()

            } else {

                setObject()
                setContentTextData()

            }
        }

        //view 접근
        binding.apply {

            //Post
            postButton.setOnClickListener {

                MemoObject.bitmap = getBitmapFromView(contentText)
                val shareDialog = ShareDialog()
                shareDialog.show(parentFragmentManager, viewModel.contentData.value)

            }

            //뒤로가기
            backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }

        }

        return binding.root
    }

    //TextView 비트맵 변환
    private fun getBitmapFromView(view: View, defaultColor: Int = typed.data): Bitmap? {

        val fixedHeight =
            if (view.height < view.width) view.width
            else view.height
        val bitmap = Bitmap.createBitmap(view.width, fixedHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        canvas.drawColor(defaultColor)
        view.draw(canvas)

        return bitmap
    }

    //contentTextData
    private fun setContentTextData() {

        val contentTextData = "\n${viewModel.contentData.value}"
        val content = "## **${viewModel.titleData.value}** $contentTextData" //Title 과 Content 의 Data 값을 합침

        //해당 Text Data 들을 MarkDown 형식으로 적용
        val mark = Markwon.builder(requireContext()).usePlugin(object : AbstractMarkwonPlugin() {
            override fun configureTheme(builder: MarkwonTheme.Builder) {
                builder.headingBreakColor(typed.data)
            }
        })
            .usePlugin(SoftBreakAddsNewLinePlugin.create())
            .build()
        mark.setMarkdown(binding.contentText, content)

    }

}