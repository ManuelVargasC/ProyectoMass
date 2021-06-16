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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import pe.edu.proyectofinalmass.R;
import pe.edu.proyectofinalmass.ui.entidades.Productos;

public class EditeDeleteProductosFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    EditText etNombrePro;
    EditText etCategoriaPro;
    EditText etMarcaPro;
    EditText etStockPro;
    EditText etPrecioPro;
    EditText etVendidosPro;

    ProgressDialog pDialog;
    ImageButton btnConsultarPro;
    ImageView ivImagenPro;
    Button btnActualizarPro, btnEliminarPro;
    RequestQueue request;

    File fileImagen;
    Bitmap bitmap;

    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;

    private OnFragmentInteractionListener mListener;
    public EditeDeleteProductosFragment() {

    }

    public static EditeDeleteProductosFragment newInstance(String param1, String param2) {
        EditeDeleteProductosFragment fragment = new EditeDeleteProductosFragment();
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
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_edite_delete_productos, container, false);

        etNombrePro= (EditText) vista.findViewById(R.id.etNombrePro);
        etCategoriaPro= (EditText) vista.findViewById(R.id.etCategoriaPro);
        etMarcaPro= (EditText) vista.findViewById(R.id.etMarcaPro);
        etPrecioPro= (EditText) vista.findViewById(R.id.etPrecioPro);
        etStockPro= (EditText) vista.findViewById(R.id.etStockPro);
        etVendidosPro= (EditText) vista.findViewById(R.id.etVendidosPro);
        btnConsultarPro= (ImageButton) vista.findViewById(R.id.btnConsultarPro);
        ivImagenPro= (ImageView) vista.findViewById(R.id.ivImagenPro);
        btnActualizarPro=(Button) vista.findViewById(R.id.btnActualizarPro);
        btnEliminarPro=(Button) vista.findViewById(R.id.btnEliminarPro);
        request= Volley.newRequestQueue(getContext());

        btnConsultarPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });


        btnActualizarPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webServiceActualizar("http://192.168.0.7/bd_tiendamass/update_producto.php");
            }
        });

        btnEliminarPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webServiceEliminar("http://192.168.0.7/bd_tiendamass/delete_producto.php");
            }
        });
        return vista;

    }

    private void webServiceEliminar(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "El producto fue eliminadado", Toast.LENGTH_SHORT).show();
                limpiarFormulario();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("producto",etNombrePro.getText().toString());
                return parametros;
            }
        };
        request.add(stringRequest);
    }

    private void webServiceActualizar(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Operación exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("producto",etNombrePro.getText().toString());
                parametros.put("categoria",etCategoriaPro.getText().toString());
                parametros.put("marca",etMarcaPro.getText().toString());
                parametros.put("precio",etPrecioPro.getText().toString());
                parametros.put("stock",etStockPro.getText().toString());
                parametros.put("vendidos",etVendidosPro.getText().toString());
                return parametros;
            }
        };
        request.add(stringRequest);

    }

    private void cargarWebService() {
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();
        String url="http://192.168.0.7/bd_tiendamass/consultar_producto_nombre.php?producto="+etNombrePro.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.hide();

                Productos productos=new Productos();

                JSONArray json=response.optJSONArray("productos");
                JSONObject jsonObject=null;

                try {
                    jsonObject=json.getJSONObject(0);
                    productos.setCategoria(jsonObject.optString("categoria"));
                    productos.setRutaImagen(jsonObject.optString("ruta_imagen"));
                    productos.setPrecio(jsonObject.optString("precio"));
                    productos.setMarca(jsonObject.optString("marca"));
                    productos.setStock(jsonObject.optString("stock"));
                    productos.setVendidos(jsonObject.optString("vendidos"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                etCategoriaPro.setText(productos.getCategoria());
                etMarcaPro.setText(productos.getMarca());
                etPrecioPro.setText(productos.getPrecio());
                etStockPro.setText(productos.getStock());
                etVendidosPro.setText(productos.getVendidos());

                String urlImagen="http://192.168.0.7/bd_tiendamass/"+productos.getRutaImagen();
                //Toast.makeText(getContext(), "url: "+urlImagen, Toast.LENGTH_LONG).show();
                cargarWebServiceimagen(urlImagen);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), " No se econtraron datos "+error.toString(), Toast.LENGTH_LONG).show();
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
                ivImagenPro.setImageBitmap(response);
            }
        },0,0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), " No se encontraron datos ", Toast.LENGTH_LONG).show();
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
    private void limpiarFormulario(){
        etNombrePro.setText("");
        etCategoriaPro.setText("");
        etMarcaPro.setText("");
        etPrecioPro.setText("");
        etStockPro.setText("");
        etVendidosPro.setText("");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

}