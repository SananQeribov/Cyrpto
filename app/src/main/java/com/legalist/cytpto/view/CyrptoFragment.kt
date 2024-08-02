package com.legalist.cytpto.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.legalist.cytpto.adapter.CyrptoRecycleView
import com.legalist.cytpto.databinding.FragmentCyrptoBinding
import com.legalist.cytpto.model.CyrptoModelItem
import com.legalist.cytpto.viewmodel.CyrptoViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope


class CyrptoFragment() : Fragment(), CyrptoRecycleView.Listener,AndroidScopeComponent {
    private var _binding: FragmentCyrptoBinding? = null
    private val binding get() = _binding!!
    private val Baseurl = "https://raw.githubusercontent.com/"
    private var cyrptoModelItem: ArrayList<CyrptoModelItem>? = null
    private var recycleview: CyrptoRecycleView? = null
    private val viewModel by viewModel<CyrptoViewModel>()
    private var job: Job? = null
    override val scope by fragmentScope()
    private val scoped by inject<String> (qualifier = named("Hello"))
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
        //viewModel = ViewModelProvider(this).get(CyrptoViewModel::class.java)
        viewModel.fromGetDataApi()
        observData()
        println(scoped)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observData() {
        viewModel.cyrptoList.observe(viewLifecycleOwner, Observer {
            binding.recyclerView.visibility = View.VISIBLE
            recycleview =
                CyrptoRecycleView(ArrayList(it.data ?: arrayListOf()), this@CyrptoFragment)
            binding.recyclerView.adapter = recycleview


        })
        viewModel.cyrptoError.observe(viewLifecycleOwner, Observer { eror ->
            eror.also {
                if (it.data == true) {
                    binding.CyrptoEdittext.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.CyrptoProgressbar.visibility = View.GONE
                } else {
                    binding.CyrptoEdittext.visibility = View.GONE


                }


            }


        })
        viewModel.cyrptoLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading.also {
                if (it.data == true) {
                    binding.CyrptoProgressbar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.CyrptoEdittext.visibility = View.GONE
                } else {
                    binding.CyrptoProgressbar.visibility = View.GONE

                }
            }


        })


    }


    override fun onItemClick(cryptoModel: CyrptoModelItem) {
        Toast.makeText(requireContext(), "Clicked on :${cryptoModel.currency}", Toast.LENGTH_LONG)
            .show()
    }

}