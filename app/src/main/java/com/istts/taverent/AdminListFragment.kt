package com.istts.taverent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.istts.taverent.databinding.FragmentAdminListBinding

class AdminListFragment : Fragment() {
    private lateinit var binding: FragmentAdminListBinding
    var WS_HOST = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminListBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WS_HOST = resources.getString(R.string.WS_HOST)


        viewPagerAdapter = ViewPagerAdapter(arrayListOf(UsersListFragment(),PenginapanListFragment()), activity as AppCompatActivity)
        binding.viewpager2.adapter = viewPagerAdapter

        binding.viewpager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position==0){
                    binding.button6.background = resources.getDrawable(R.color.clear)
                    binding.button5.background = resources.getDrawable(R.drawable.rectangle_white_with_bottom_border)
                }else{
                    binding.button5.background = resources.getDrawable(R.color.clear)
                    binding.button6.background = resources.getDrawable(R.drawable.rectangle_white_with_bottom_border)
                }
            }
        })

        binding.button6.setOnClickListener {
            binding.button5.background = resources.getDrawable(R.color.clear)
            binding.button6.background = resources.getDrawable(R.drawable.rectangle_white_with_bottom_border)
            binding.viewpager2.currentItem = 1
        }

        binding.button5.setOnClickListener {
            binding.button6.background = resources.getDrawable(R.color.clear)
            binding.button5.background = resources.getDrawable(R.drawable.rectangle_white_with_bottom_border)
            binding.viewpager2.currentItem = 0
        }


    }
}