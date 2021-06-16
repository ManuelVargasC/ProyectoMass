package pe.edu.proyectofinalmass.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
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
import pe.edu.proyectofinalmass.ui.adapter.CategoriasAdapter;
import pe.edu.proyectofinalmass.ui.entidades.Categorias;


public class ListarCategoriasFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView rvCategoria;
    ArrayList<Categorias> listaCategoria;
    private ProgressDialog dialog;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    private Menu idPerfil;

    private OnFragmentInteractionListener mListener;
    public ListarCategoriasFragment() {

    }


    public static ListarCategoriasFragment newInstance(String param1, String param2) {
        ListarCategoriasFragment fragment = new ListarCategoriasFragment();
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
        View vista = inflater.inflate(R.layout.fragment_listar_categorias, container, false);
        listaCategoria=new ArrayList<>();
        rvCategoria = vista.findViewById(R.id.rvCategoria);
        rvCategoria.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvCategoria.setHasFixedSize(true);
        request= Volley.newRequestQueue(getContext());

        cargarWebService();
        return vista;
    }

    private void cargarWebService() {
        dialog=new ProgressDialog(getContext());
        dialog.setMessage("Consultando categorias");
        dialog.show();

        String url="http://192.168.0.7/bd_tiendamass/listarimagenes_url.php";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        dialog.hide();
        Log.d("ERROR: ", error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {
        Categorias categoria=null;
        JSONArray json=response.optJSONArray("categorias");
        try {
            for (int i=0;i<json.length();i++){
                categoria=new Categorias();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                categoria.setIdcategoria(jsonObject.optInt("idcategoria"));
                categoria.setCategoria(jsonObject.optString("categoria"));
                categoria.setRutaImagen(jsonObject.optString("ruta_imagen"));
                listaCategoria.add(categoria);
            }
            dialog.hide();
            CategoriasAdapter adapter=new CategoriasAdapter(listaCategoria, getContext());
            rvCategoria.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            dialog.hide();
        }

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