package com.legalist.cytpto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.legalist.cytpto.adapter.CyrptoRecycleView
import com.legalist.cytpto.databinding.FragmentCyrptoBinding
import com.legalist.cytpto.model.CyrptoModelItem
import com.legalist.cytpto.sevice.CyrptoApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CyrptoFragment : Fragment(), CyrptoRecycleView.Listener {
    private var _binding: FragmentCyrptoBinding? = null
    private val binding get() = _binding!!
    private val Baseurl = "https://raw.githubusercontent.com/"
    private var cyrptoModelItem: ArrayList<CyrptoModelItem>? = null
    private var recycleview: CyrptoRecycleView? = null
    private var job: Job? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error:{${throwable.localizedMessage}}")


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCyrptoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layout = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layout
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Baseurl)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CyrptoApi::class.java)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        cyrptoModelItem = it?.let { it1 -> ArrayList(it1) }
                        cyrptoModelItem?.let {
                            recycleview = CyrptoRecycleView(it, this@CyrptoFragment)
                            binding.recyclerView.adapter = recycleview
                        }


                    }

                }
            }

        }

    }

    override fun onItemClick(cryptoModel: CyrptoModelItem) {
Toast.makeText(requireContext(),"Clicked on :${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }

}