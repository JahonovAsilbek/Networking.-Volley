package uz.revolution.networkingvolley.adapters

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import uz.revolution.networkingvolley.databinding.ItemUserBinding
import uz.revolution.networkingvolley.models.User

class UserAdapter(var list: List<User>, var context: Context) :
    RecyclerView.Adapter<UserAdapter.VH>() {

    inner class VH(var itemUserBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {

        fun onBind(user: User) {

            val imageRequest = ImageRequest(
                user.avatar_url,
                object : Response.Listener<Bitmap> {
                    override fun onResponse(response: Bitmap?) {
                        itemUserBinding.avatar.setImageBitmap(response)
                    }
                },
                0,
                0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.ARGB_8888,
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {

                    }
                })

            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(imageRequest)

            itemUserBinding.login.text = user.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}