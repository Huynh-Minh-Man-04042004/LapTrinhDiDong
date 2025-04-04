package com.hcmute.baitap09;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hcmute.baitap09.R;
import com.hcmute.baitap09.ServiceAPI;
import com.hcmute.baitap09.ImageUpload;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImagesActivity extends AppCompatActivity {

    private Button btnChoose, btnUpload;
    private ImageView imageViewChoose, imageViewUpload;
    private EditText editTextUserName;
    private TextView textViewUsername;
    private Uri mUri;
    private ProgressDialog mProgressDialog;

    public static final int MY_REQUEST_CODE = 100;
    public static final String TAG = MainActivity.class.getName();

    // Permission arrays for storage access
    public static String[] storge_permissions = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storge_permissions_33 = {
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_AUDIO,
            android.Manifest.permission.READ_MEDIA_VIDEO
    };

    // Mapping function
    private void AnhXa() {
        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        imageViewUpload = findViewById(R.id.imgMultipart);
        editTextUserName = findViewById(R.id.editUserName);
        textViewUsername = findViewById(R.id.tvUsername);
        imageViewChoose = findViewById(R.id.imgChoose);
    }

    // Permissions checker
    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storge_permissions_33;
        } else {
            p = storge_permissions;
        }
        return p;
    }

    // Check permissions and open gallery
    private void CheckPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissions(permissions(), MY_REQUEST_CODE);
        }
    }

    // Open Gallery to pick image
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    // Handle result of image selection
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        mUri = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUri);
                            imageViewChoose.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    // Upload image to server
    public void UploadImage1() {
        mProgressDialog.show();

        // Get username input
        String username = editTextUserName.getText().toString().trim();
        RequestBody requestUsername = RequestBody.create(MediaType.parse("multipart/form-data"), username);

        // Get the image path
        String IMAGE_PATH = RealPathUtil.getRealPath(this, mUri);
        Log.e(TAG, IMAGE_PATH);

        File file = new File(IMAGE_PATH);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part partBodyAvatar = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

        // Call API to upload the image
        ServiceAPI.serviceAPI.upload(requestUsername, partBodyAvatar).enqueue(new Callback<List<ImageUpload>>() {
            @Override
            public void onResponse(Call<List<ImageUpload>> call, Response<List<ImageUpload>> response) {
                mProgressDialog.dismiss();
                List<ImageUpload> imageUpload = response.body();
                if (imageUpload != null && !imageUpload.isEmpty()) {
                    for (ImageUpload image : imageUpload) {
                        textViewUsername.setText(image.getUsername());
                        Glide.with(UploadImagesActivity.this)
                                .load(image.getAvatar())
                                .into(imageViewUpload);
                        Toast.makeText(UploadImagesActivity.this, "Success", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(UploadImagesActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ImageUpload>> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(UploadImagesActivity.this, "API call failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_images);

        // Call mapping function
        AnhXa();

        // Initialize ProgressDialog
        mProgressDialog = new ProgressDialog(UploadImagesActivity.this);
        mProgressDialog.setMessage("Please wait upload....");

        // Set button click listeners
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckPermission(); // Check and request permissions
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUri != null) {
                    UploadImage1(); // Upload image if URI is not null
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }
}