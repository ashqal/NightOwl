package com.asha.nightowllib.handler.impls;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.asha.nightowllib.handler.ISkinHandler;
import com.asha.nightowllib.handler.annotations.OwlAttrScope;
import com.asha.nightowllib.handler.annotations.OwlStyleable;
import com.asha.nightowllib.handler.annotations.OwlSysStyleable;
import com.asha.nightowllib.paint.ColorBox;
import com.asha.nightowllib.paint.IOwlPaint;

import java.lang.reflect.Field;

import static com.asha.nightowllib.NightOwlUtil.getStaticFieldIntArraySafely;
import static com.asha.nightowllib.NightOwlUtil.insertSkinBox;
import static com.asha.nightowllib.NightOwlUtil.obtainSkinBox;
import static com.asha.nightowllib.paint.OwlPaintManager.queryPaint;

/**
 * Created by hzqiujiadi on 15/11/6.
 * hzqiujiadi ashqalcn@gmail.com
 */
public abstract class AbsSkinHandler implements ISkinHandler {
    private final static String ANDROID_XML = "http://schemas.android.com/apk/res/android";
    private static final String TAG = "AbsSkinHandler";

    @Override
    public void collect(int mode, View view, Context context, AttributeSet attrs) {
        Log.d(TAG, String.format("collected %s %s %s", view, context, attrs));
        ColorBox box = ColorBox.newInstance();
        onBeforeCollect(view,context,attrs,box);

        final Resources.Theme theme = context.getTheme();
        int systemStyleResId = 0;
        Class clz = this.getClass();

        // SystemStyleable
        OwlSysStyleable systemStyleable = (OwlSysStyleable) clz.getAnnotation(OwlSysStyleable.class);
        if ( systemStyleable != null ){
            String value = systemStyleable.value();
            systemStyleResId = attrs.getAttributeResourceValue(ANDROID_XML, value, 0);
        }

        // OwlStyleable
        Field[] fields = clz.getFields();
        for ( Field field : fields ){
            OwlStyleable owlStyleable = field.getAnnotation(OwlStyleable.class);
            if ( owlStyleable == null ) continue;

            Class scopeClz = field.getDeclaringClass();
            OwlAttrScope owlAttrScope = (OwlAttrScope) scopeClz.getAnnotation(OwlAttrScope.class);
            if ( owlAttrScope == null ) continue;

            int scope = owlAttrScope.value();
            int[] styleableResId = getStaticFieldIntArraySafely(field);
            if ( styleableResId == null )  continue;

            TypedArray a = theme.obtainStyledAttributes(attrs, styleableResId, 0, systemStyleResId);
            if ( a != null ){
                obtainStyle(view, box, scope, a);
                a.recycle();
            }
        }

        onAfterCollect(view,context,attrs,box);
        insertSkinBox(view, box);

        // first refresh
        box.refreshSkin(mode, view, true);
    }

    private void obtainStyle(View view
            , ColorBox box
            , int scope
            , @NonNull TypedArray a
            ){

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            // look for attr
            IOwlPaint paint = queryPaint(attr+scope);
            if ( paint == null) {
                Log.d(TAG, "Can't find paint of attr:" + attr + " scope:" + scope); continue; }
            Object[] values = paint.setup(view,a,attr);
            if ( values != null ) box.put(attr, scope, values);
        }
    }

    protected void onBeforeCollect(View view, Context context, AttributeSet attrs, ColorBox box){}

    protected void onAfterCollect(View view, Context context, AttributeSet attrs, ColorBox box){}

    @Override
    final public void onSkinChanged(int skin, View view) {
        ColorBox box = obtainSkinBox(view);
        if ( box != null ) box.refreshSkin(skin, view);
    }
}
