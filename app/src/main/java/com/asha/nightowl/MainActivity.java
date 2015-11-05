package com.asha.nightowl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.asha.demohelperlib.DemoHelperActivity;
import com.asha.nightowllib.NightOwl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MainActivity extends DemoHelperActivity {


    static final String data = "ashqal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewCreated() {
        addButton("add Fragment", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MainFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.dh_main_container, fragment, "test")
                        .commit();
            }
        });

        addButton("modify static final", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow();
                LayoutInflater inflater1 = getLayoutInflater();
                LayoutInflater inflater2 = LayoutInflater.from(MainActivity.this);

                try {
                    DHLog("before:%s", data);
                    setFinalStatic(MainActivity.class, "data", "good");
                    DHLog("after:%s", data);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    static void setFinalStatic(Class clazz, String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        Field fieldArtField = Field.class.getDeclaredField("artField");
        fieldArtField.setAccessible(true);
        Object artField = fieldArtField.get(field);
        Class ArtFieldClz = artField.getClass();


        Field accessFlags = ArtFieldClz.getDeclaredField("accessFlags");
        accessFlags.setAccessible(true);
        int flag = accessFlags.getInt(artField);
        accessFlags.setInt(artField, flag & ~Modifier.FINAL);

        field.set(null, newValue);
        Log.e("ashqal", "after2:" + field.get(null));

    }

}
