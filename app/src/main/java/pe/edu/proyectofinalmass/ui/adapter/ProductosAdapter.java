package pe.edu.proyectofinalmass.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import pe.edu.proyectofinalmass.R;
import pe.edu.proyectofinalmass.ui.entidades.Categorias;
import pe.edu.proyectofinalmass.ui.entidades.Productos;

public class ProductosAdapter extends RecyclerView.Adapter<pe.edu.proyectofinalmass.ui.adapter.ProductosAdapter.ProductosHolder>{

    List<Productos> listaProductos;
    private RequestQueue request;
    private Context context;

    public ProductosAdapter(List<Productos> listaProductos, Context context){
        this.listaProductos = listaProductos;
        this.context=context;
        request= Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public  pe.edu.proyectofinalmass.ui.adapter.ProductosAdapter.ProductosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detalle_productos,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new ProductosHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull pe.edu.proyectofinalmass.ui.adapter.ProductosAdapter.ProductosHolder holder, int position) {
        holder.tvProducto.setText(listaProductos.get(position).getProducto());
        holder.tvPrecio.setText(listaProductos.get(position).getPrecio());

        if (listaProductos.get(position).getRutaImagen()!=null){
            CargarImagenWebService(listaProductos.get(position).getRutaImagen(), holder);
        }else{
            holder.ivImagenProducto.setImageResource(R.drawable.img_base);
        }
    }


    private void CargarImagenWebService(String rutaImagen, final ProductosHolder holder) {
        String urlImagen = "http://192.168.0.7/bd_tiendamass/" + rutaImagen;
        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.ivImagenProducto.setImageBitmap(response);

            }
        }, 0,0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(imageRequest);
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ProductosHolder extends RecyclerView.ViewHolder {
        TextView tvProducto,tvPrecio;
        ImageView ivImagenProducto;

        public ProductosHolder(@NonNull View itemView) {
            super(itemView);
            tvProducto= itemView.findViewById(R.id.tvProducto);
            tvPrecio= itemView.findViewById(R.id.tvPrecio);
            ivImagenProducto= itemView.findViewById(R.id.ivImagenProducto);
        }
    }
}
