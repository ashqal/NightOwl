package com.asha.nightowllib.paint;

import android.util.SparseArray;

import com.asha.nightowllib.NightOwlTable;
import com.asha.nightowllib.handler.annotations.OwlAttr;
import com.asha.nightowllib.handler.annotations.OwlAttrScope;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.asha.nightowllib.NightOwlUtil.getStaticFieldIntSafely;
import static com.asha.nightowllib.NightOwlUtil.newInstanceSafely;

/**
 * Created by hzqiujiadi on 15/11/8.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class OwlPaintManager {
    private static final SparseArray<Class< ? extends IOwlPaint>> sPaints = new SparseArray<>();
    private static final Map<Class< ? extends IOwlPaint>,IOwlPaint> sPaintInstances = new HashMap<>();
    static {
        // traverse all subclass.
        Class<?>[] classes = NightOwlTable.class.getDeclaredClasses();
        for ( Class subClz : classes ){
            registerPaint(subClz);
        }
    }

    public static void registerPaint(Class subClz){
        // look for all OwlAttrScope
        OwlAttrScope owlAttrScope = (OwlAttrScope) subClz.getAnnotation(OwlAttrScope.class);
        if ( owlAttrScope == null ) return;
        int scope = owlAttrScope.value();

        // traverse all declared fields in this subclass
        Field[] fields = subClz.getDeclaredFields();
        for (Field field : fields){
            // look for all OwlAttr
            OwlAttr attr = field.getAnnotation(OwlAttr.class);
            if ( attr == null ) continue;
            int attrId =  getStaticFieldIntSafely(field);
            Class< ? extends IOwlPaint> targetClz = attr.value();
            sPaints.append( attrId + scope,targetClz );
        }
    }

    public static IOwlPaint queryPaint(int attrWithScope){
        Class< ? extends IOwlPaint> clz = sPaints.get(attrWithScope);
        if ( clz == null ) return null;
        IOwlPaint instance = sPaintInstances.get(clz);
        if ( instance == null ){
            instance = newInstanceSafely(clz);
            sPaintInstances.put(clz,instance);
        }
        return instance;
    }
}
