package pe.edu.proyectofinalmass.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.edu.proyectofinalmass.R;
import pe.edu.proyectofinalmass.ui.entidades.Productos;


public class ConsultarStockFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    ArrayList<Productos> listaProductos;
    EditText etNombrePS,etMarcaPS, etPrecioPS, etStockPS, etVendidosPS;
    Button btnConsultarPS;
    private ProgressDialog pDialog;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private OnFragmentInteractionListener mListener;
    public ConsultarStockFragment() {

    }

    public static ConsultarStockFragment newInstance(String param1, String param2) {
        ConsultarStockFragment fragment = new ConsultarStockFragment();
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
        View vista =  inflater.inflate(R.layout.fragment_consultar_stock, container, false);
        etNombrePS = (EditText) vista.findViewById(R.id.etNombrePS);
        etMarcaPS=(EditText) vista.findViewById(R.id.etMarcaPS);
        etPrecioPS= (EditText) vista.findViewById(R.id.etPrecioPS);
        etStockPS= (EditText) vista.findViewById(R.id.etStockPS);
        etVendidosPS = (EditText) vista.findViewById(R.id.etVendidosPS);
        btnConsultarPS=(Button) vista.findViewById(R.id.btnConsultarPS);
        request= Volley.newRequestQueue(getContext());

        btnConsultarPS.setOnClickListener(new View.OnClickListener() {
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
        String url="http://192.168.0.7/bd_tiendamass/consultar_producto_nombrestock.php?producto="+etNombrePS.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.hide();

                Productos producto=new Productos();

                JSONArray json=response.optJSONArray("productos");
                JSONObject jsonObject=null;

                try {
                    jsonObject=json.getJSONObject(0);
                    producto.setMarca(jsonObject.optString("marca"));
                    producto.setPrecio(jsonObject.optString("precio"));
                    producto.setStock(jsonObject.optString("stock"));
                    producto.setVendidos(jsonObject.optString("vendidos"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                etMarcaPS.setText("MARCA: "+producto.getMarca());
                etPrecioPS.setText("PRECIO: "+producto.getPrecio());
                etStockPS.setText("STOCK: "+producto.getStock());
                etVendidosPS.setText("VENDIDOS: "+producto.getVendidos());

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

    @Override
    public void onErrorResponse(VolleyError error) {
        pDialog.hide();
        Toast.makeText(getContext(),"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {

    }




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
        void onFragmentInteraction(Uri uri);
    }
}