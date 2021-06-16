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

import pe.edu.proyectofinalmass.ui.entidades.Categorias;
import pe.edu.proyectofinalmass.R;


public class CategoriasAdapter extends RecyclerView.Adapter<pe.edu.proyectofinalmass.ui.adapter.CategoriasAdapter.CategoriasHolder> {

    List<Categorias> listaCategoria;
    private RequestQueue request;
    private Context context;

    public CategoriasAdapter(List<Categorias> listaCategoria, Context context) {
        this.listaCategoria = listaCategoria;
        this.context=context;
        request= Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public pe.edu.proyectofinalmass.ui.adapter.CategoriasAdapter.CategoriasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detalle_categorias,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new CategoriasHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull pe.edu.proyectofinalmass.ui.adapter.CategoriasAdapter.CategoriasHolder holder, int position) {

        holder.tvIdCategoria.setText(listaCategoria.get(position).getIdcategoria().toString());
        holder.tvCategoria.setText(listaCategoria.get(position).getCategoria());

        if (listaCategoria.get(position).getRutaImagen()!=null){
            CargarImagenWebService(listaCategoria.get(position).getRutaImagen(), holder);
        }else{
            holder.ivImagenCategoria.setImageResource(R.drawable.img_base);
        }

    }

    private void CargarImagenWebService(String rutaImagen, final CategoriasHolder holder) {
        String urlImagen = "http://192.168.0.7/bd_tiendamass/" + rutaImagen;
        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.ivImagenCategoria.setImageBitmap(response);

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
        return listaCategoria.size();
    }

    public class CategoriasHolder extends RecyclerView.ViewHolder {

        TextView tvIdCategoria,tvCategoria;
        ImageView ivImagenCategoria;


        public CategoriasHolder(@NonNull View itemView) {
            super(itemView);
            tvIdCategoria= itemView.findViewById(R.id.tvIdCategoria);
            tvCategoria= itemView.findViewById(R.id.tvCategoria);
            ivImagenCategoria= itemView.findViewById(R.id.ivImagenCategoria);
        }
    }
}
