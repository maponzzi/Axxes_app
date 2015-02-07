package com.axxesinternational.vias.axxes.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request;
import com.axxesinternational.vias.axxes.R;
import com.axxesinternational.vias.axxes.utils.AlbumStorageDirFactory;
import com.axxesinternational.vias.axxes.utils.BitmapManager;
//import com.axxesinternational.vias.axxes.volley.MultipartRequest;
//import com.axxesinternational.vias.axxes.volley.MultipartRequest1;
import com.axxesinternational.vias.axxes.requests.MultiPartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FotosFragment extends BaseVolleyFragment {

    private static final int TAKE_PICTURE = 1;
    //private static final int SELECT_PICTURE = 2;
    private static final String ALBUM_NAME = "Axxes_Mantenimientos";

    private Bitmap mImageBitmap;
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private ImageView pictureTaken;
    private String currentPhotoPath;
    private ImageButton startCameraBtn;
    Button saveBtn;

    private Map<String, String> Observ;
    private File ImageFile;


    public FotosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fotos, container, false);
        pictureTaken = (ImageView) view.findViewById(R.id.picture_taken);
        startCameraBtn = (ImageButton) view.findViewById(R.id.start_camera);
        saveBtn = (Button) view.findViewById(R.id.btn_save_photo);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        startCameraBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                takePictureFromCamera(v);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                makeRequest();
            }
        });
    }

    private void addPictureToGallery() {

        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        Log.d("CurrentPhotoPat", currentPhotoPath);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        intent.setData(contentUri);
        getActivity().sendBroadcast(intent);

    }


    private void makeRequest() {

        Log.d("Estado", "Empezando a crear el request");
        String url = "http://vias.axxesinternational.com/api/fotos/";
        Log.d("CurrentPhotoPath", currentPhotoPath);
        ImageFile = new File(currentPhotoPath);


        Observ = new HashMap<String, String>();
        Observ.put("fol", "1002001501");
        Observ.put("comenta", "Prueba de comentarios con foto");
        Observ.put("lat", "1.23");
        Observ.put("lng", "3.21");


        String stringPart = "img";


        JSONObject datos = new JSONObject();
        try {
            //datos.put("fol", "1002001501");
            datos.put("comenta", "Prueba de comentarios con foto");
            datos.put("lat", "1.23");
            datos.put("lng", "3.21");
            //datos.put("img", ImageFile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        

//        MultipartRequest1 mpRequest = new MultipartRequest1(url, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("ErrorSubida", error.toString());
//            }
//        }, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d("Subida:", response.toString());
//            }
//        }, ImageFile, stringPart);
        //mpRequest.addStringBody("fol", "1002001501");



        MultiPartRequest mpRequest = new MultiPartRequest(Request.Method.POST, url, datos, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Subida:", response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ErrorSubida", error.toString());
            }
        });
        //mpRequest.addFileUpload("img", ImageFile);
        //mpRequest.addStringUpload("fol", "1002001501");

        addToQueue(mpRequest);

    }


    public void takePictureFromCamera(View v){

        if (isIntentAvailable(getActivity(), MediaStore.ACTION_IMAGE_CAPTURE)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = null;
            try {
                f = AlbumStorageDirFactory.setUpPhotoFile(ALBUM_NAME);
                currentPhotoPath = f.getAbsolutePath();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            } catch(IOException e) {
                e.printStackTrace();
                f = null;
                currentPhotoPath = null;
            }

            startActivityForResult(intent, TAKE_PICTURE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("resultcode: ", Integer.toString(resultCode));
//        if (resultCode == RESULT_OK) {
            switch(requestCode) {
                case TAKE_PICTURE: {
                    if (currentPhotoPath != null) {
                        mImageBitmap = BitmapManager.setPic(pictureTaken, currentPhotoPath);
                        pictureTaken.setImageBitmap(mImageBitmap);
                        addPictureToGallery();
                        //currentPhotoPath = null;
                    }
                    break;
                }

            }
        //}

    }



    /**
     * Indicates whether the specified action can be used as an intent. This
     * method queries the package manager for installed packages that can
     * respond to an intent with the specified action. If no suitable package is
     * found, this method returns false.
     * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
     *
     * @param context The application's environment.
     * @param action The Intent action to check for availability.
     *
     * @return True if an Intent with the specified action can be sent and
     *         responded to, false otherwise.
     */
    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


}
