package com.example.manifestexplorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import test.AXMLPrinter;


public class ManifestInfoActivity extends AppCompatActivity{
    private String fileText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppliInfo app;
        AXMLPrinter axmlPrinterInstance = new AXMLPrinter();
        setContentView(R.layout.manifestinfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        app = getIntent().getExtras().getParcelable("app");
        TextView tv = findViewById(R.id.textView);
        TextView tv2 = findViewById(R.id.textView2);
        tv2.setText(app.getTitle());
        ImageView iv = findViewById(R.id.imageView);
        iv.setImageBitmap(app.getIcon());
        try {
            ZipFile apk = new ZipFile(app.getPublicSourceDir());
            ZipEntry manifest = apk.getEntry("AndroidManifest.xml");
            if (manifest != null){
                InputStream stream = apk.getInputStream(manifest);
                axmlPrinterInstance.startParsing(stream);
                //BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                /*StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.d("line: ", line);
                    out.append(line.trim());
                }*/
                fileText = axmlPrinterInstance.obtainString();
                stream.close();
            }
            apk.close();
            tv.setText(fileText);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            tv.setText("Manifest File not found");
        }


    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
