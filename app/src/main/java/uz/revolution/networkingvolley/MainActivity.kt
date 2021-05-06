package uz.revolution.networkingvolley

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.revolution.networkingvolley.adapters.UserAdapter
import uz.revolution.networkingvolley.databinding.ActivityMainBinding
import uz.revolution.networkingvolley.models.User
import uz.revolution.networkingvolley.networkhelper.NetworkHelper

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var requestQueue: RequestQueue
    var uri = "https://api.github.com/users"
    private var list: List<User>? = null
    private var adapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // disable night mode settings
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        requestQueue = Volley.newRequestQueue(this)


        val networkHelper = NetworkHelper(this)

        if (networkHelper.isNetworkConnected()) {
            val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, uri, null,
                { response ->
                    val type = object : TypeToken<List<User>>() {}.type
                    list = Gson().fromJson(response.toString(), type)
                    adapter = list?.let { UserAdapter(it, this) }
                    binding.rv.adapter = adapter
                }) {

            }
            requestQueue.add(jsonArrayRequest)
        }

    }

}