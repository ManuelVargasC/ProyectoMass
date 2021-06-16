package pe.edu.proyectofinalmass.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.edu.proyectofinalmass.R;
import pe.edu.proyectofinalmass.ui.entidades.Productos;


public class ConsultarProductoFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    ArrayList<Productos> listaProductos;
    EditText etNombreP;
    TextView tvCategoriaP, tvMarcaP, tvPrecioProducto;
    Button btnConsultarP;
    ImageView ivImagenP;
    private ProgressDialog pDialog;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;


    public ConsultarProductoFragment() {

    }



    public static ConsultarProductoFragment newInstance(String param1, String param2) {
        ConsultarProductoFragment fragment = new ConsultarProductoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_consultar_producto, container, false);
        etNombreP = (EditText) vista.findViewById(R.id.etNombreP);
        tvCategoriaP=(TextView) vista.findViewById(R.id.tvCategoriaP);
        tvMarcaP= (TextView) vista.findViewById(R.id.tvMarcaP);
        tvPrecioProducto= (TextView) vista.findViewById(R.id.tvPrecioProducto);
        btnConsultarP=(Button) vista.findViewById(R.id.btnConsultarP);
        ivImagenP=(ImageView) vista.findViewById(R.id.ivImagenP);
        request= Volley.newRequestQueue(getContext());

        btnConsultarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });
        return vista;
         }

    private void cargarWebService() {
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();
        String url="http://192.168.0.7/bd_tiendamass/consultar_producto_nombre.php?producto="+etNombreP.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.hide();

                Productos producto=new Productos();

                JSONArray json=response.optJSONArray("productos");
                JSONObject jsonObject=null;

                try {
                    jsonObject=json.getJSONObject(0);
                    producto.setCategoria(jsonObject.optString("categoria"));
                    producto.setRutaImagen(jsonObject.optString("ruta_imagen"));
                    producto.setMarca(jsonObject.optString("marca"));
                    producto.setPrecio(jsonObject.optString("precio"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                tvCategoriaP.setText("CATEGORIA: "+producto.getCategoria());
                tvMarcaP.setText("MARCA: "+producto.getMarca());
                tvPrecioProducto.setText("PRECIO: "+producto.getPrecio());

                String urlImagen="http://192.168.0.7/bd_tiendamass/"+producto.getRutaImagen();
                //Toast.makeText(getContext(), "url: "+urlImagen, Toast.LENGTH_LONG).show();
                cargarWebServiceimagen(urlImagen);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });
        request.add(jsonObjectRequest);

    }

    private void cargarWebServiceimagen(String urlImagen) {
        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ivImagenP.setImageBitmap(response);
            }
        },0,0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar ", Toast.LENGTH_LONG).show();
            }
        });
        request.add(imageRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        pDialog.hide();
        Toast.makeText(getContext(),"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}