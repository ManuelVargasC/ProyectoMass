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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pe.edu.proyectofinalmass.R;
import pe.edu.proyectofinalmass.RegistroUsuarioActivity;
import pe.edu.proyectofinalmass.ui.entidades.Validaciones;


public class RegistrarProveedorFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

   EditText etnNombreProveedor, etRazonSocialProveedor, etRucProveedor, etDireccionProveedor,
            etTelefonoProveedor;
    Button btnRegistrarProveedor;

    ProgressDialog pDialog;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    private EditeDeleteProductosFragment.OnFragmentInteractionListener mListener;
    public RegistrarProveedorFragment() {

    }

    public static RegistrarProveedorFragment newInstance(String param1, String param2) {
        RegistrarProveedorFragment fragment = new RegistrarProveedorFragment();
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
        View vista = inflater.inflate(R.layout.fragment_registrar_proveedor, container, false);
        etTelefonoProveedor=(EditText) vista.findViewById(R.id.etnTelefonoProveedor);
        etnNombreProveedor= (EditText) vista.findViewById(R.id.etnNombreProveedor);
        etRazonSocialProveedor= (EditText) vista.findViewById(R.id.etRazonSocialProveedor);
        etRucProveedor= (EditText) vista.findViewById(R.id.etRucProveedor);
        etDireccionProveedor= (EditText) vista.findViewById(R.id.etDireccionProveedor);
        btnRegistrarProveedor=(Button) vista.findViewById(R.id.btnRegistrarProveedor);

        request= Volley.newRequestQueue(getContext());

        btnRegistrarProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebserice();

            }
        });
        return vista;
    }


    private void cargarWebserice() {

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();

    String url = "http://192.168.0.7/bd_tiendamass/insertar_proveedor2.php?nombre="+etnNombreProveedor.getText().toString()+
            "&razon_social="+etRazonSocialProveedor.getText().toString()+
            "&ruc="+etRucProveedor.getText().toString()+
            "&direccion="+etDireccionProveedor.getText().toString()+
            "&telefono="+etTelefonoProveedor.getText().toString();

    url=url.replace(" ", "%20");

    jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
    request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        pDialog.hide();
        Toast.makeText(getContext(), "No se pudo registrar " + error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(), "Se ha Registrado exitosamente",Toast.LENGTH_SHORT).show();
        pDialog.hide();
        etTelefonoProveedor.setText("");
        etnNombreProveedor.setText("");
        etRucProveedor.setText("");
        etRazonSocialProveedor.setText("");
        etDireccionProveedor.setText("");



    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditeDeleteProductosFragment.OnFragmentInteractionListener) {
            mListener = (EditeDeleteProductosFragment.OnFragmentInteractionListener) context;
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