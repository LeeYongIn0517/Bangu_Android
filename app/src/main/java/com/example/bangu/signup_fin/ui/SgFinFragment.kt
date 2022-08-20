package com.example.bangu.signup_fin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bangu.App
import com.example.bangu.R
import com.example.bangu.databinding.FragmentSignupFinBinding
import com.example.bangu.login.ui.LoginFragment
import com.example.bangu.signup_fin.data.model.Content

class SgFinFragment : Fragment() {
    private lateinit var binding: FragmentSignupFinBinding
    private var page = 0
    private val ITEMS_SIZE = 3
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupFinBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewmodel = SgFinViewModel()
        val adapter = SgFinAdapter()

        //서버에 데이터 초기요청 1번
        if(page == 0){
            viewmodel.requestSgFinMovieList(page,ITEMS_SIZE)
        }
        //닉네임 지정
        binding.tvNickname.text = App.signup_prefs.sp_nickname
        //리사이클뷰 어댑터 등록
        binding.signupfinRcyleview
            .adapter = adapter
        //서버에서 온 데이터를 관찰하는 옵저버
        viewmodel.contentList.observe(viewLifecycleOwner, Observer {
            adapter.setList(it as MutableList<Content>)
            adapter.notifyItemRangeInserted(page*ITEMS_SIZE,ITEMS_SIZE) //이해 100% 안됨
        })
        //스크롤 리스너
        binding.signupfinRcyleview.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                //스크롤이 끝에 도달했는지 확인
                if(!binding.signupfinRcyleview.canScrollHorizontally(1) && lastVisibleItemPosition == itemTotalCount){
                    adapter.deleteLoading()
                    Log.d("SgFinVM","requestSgFinMovieList")
                    viewmodel.requestSgFinMovieList(++page,ITEMS_SIZE)
                }
            }
        })
        //로그인화면으로 이동
        binding.signupfinText.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.singleFrame, LoginFragment()).commit()
        }
    }

}