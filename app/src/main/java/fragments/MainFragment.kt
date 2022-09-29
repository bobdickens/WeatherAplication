package fragments

import adapters.VpAdapter
import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityRequestPreparer
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.helper.widget.Carousel
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.ListFragment
import com.constanta.weatheraplication.R
import com.constanta.weatheraplication.databinding.ActivityMainBinding
import com.constanta.weatheraplication.databinding.FragmentMain2Binding
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : Fragment() {
    private val fList = listOf(
        DaysFragment.newInstance(),
        HoursFragment, newInstance()
    )
    private val tList = listOf(
        "HOURS",
        "DAYS"
    )
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMain2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMain2Binding.inflate(inflater, container, false)
        return binding.root
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }
    private fun init() = with(binding){
        val adapter = VpAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout3,vp){
            tab, pos -> tab.text = tList[pos]
        }
    }
    private fun permissionListener(){
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission(){
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()

    }
}