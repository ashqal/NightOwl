package android.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hzqiujiadi on 15/11/6.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class LayoutInfaterWrapper extends LayoutInflater {

    private static final String TAG = "LayoutInfaterWrapper";
    private LayoutInflater mDelegate;
    private Method mMethodRInflate;
    private Method mMethodCreateViewFromTag;
    protected LayoutInfaterWrapper(LayoutInflater original) {
        super(null);
        mDelegate = original;
        try {
            mMethodCreateViewFromTag = LayoutInflater.class.getDeclaredMethod("createViewFromTag"
                    , View.class
                    , String.class
                    , Context.class
                    , AttributeSet.class
                    , boolean.class);
            mMethodCreateViewFromTag.setAccessible(true);
            mMethodRInflate = LayoutInflater.class.getDeclaredMethod("rInflate"
                    , XmlPullParser.class
                    , View.class
                    , Context.class
                    , AttributeSet.class
                    , boolean.class);
            mMethodRInflate.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void rInflate(XmlPullParser parser, View parent,Context context,AttributeSet attrs, boolean finishInflate) throws XmlPullParserException, IOException {
        Log.d(TAG,String.format("rInflate %s %s %s",parser,parent,attrs));
        try {
            mMethodRInflate.invoke(mDelegate,parser,parent,context,attrs,finishInflate);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public View createViewFromTag(View parent, String name, Context context, AttributeSet attrs,
                                  boolean ignoreThemeAttr) {
        View v = null;
        try {
            v = (View) mMethodCreateViewFromTag.invoke(mDelegate,parent,name,context,attrs,ignoreThemeAttr);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Log.d(TAG,String.format("createViewFromTag %s %s %s",name,attrs,v));
        return v;
    }


    public static LayoutInflater wrap(LayoutInflater original) {
        return new LayoutInfaterWrapper(original);
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        LayoutInflater newDelegate = mDelegate.cloneInContext(newContext);
        return new LayoutInfaterWrapper(newDelegate);
    }

    @Override
    public Context getContext() {
        return mDelegate.getContext();
    }

    @Override
    public void setFactory(Factory factory) {
        mDelegate.setFactory(factory);
    }

    @Override
    public void setFactory2(Factory2 factory) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mDelegate.setFactory2(factory);
        }
    }

    @Override
    public Filter getFilter() {
        return mDelegate.getFilter();
    }

    @Override
    public void setFilter(Filter filter) {
        mDelegate.setFilter(filter);
    }

    @Override
    public View inflate(int resource, ViewGroup root) {
        return mDelegate.inflate(resource, root);
    }

    @Override
    public View inflate(XmlPullParser parser, ViewGroup root) {
        return mDelegate.inflate(parser, root);
    }

    @Override
    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        return mDelegate.inflate(resource, root, attachToRoot);
    }

    @Override
    public View inflate(XmlPullParser parser, ViewGroup root, boolean attachToRoot) {
        return mDelegate.inflate(parser, root, attachToRoot);
    }

    @Override
    public View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        return mDelegate.onCreateView(name, attrs);
    }

    @Override
    public View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return mDelegate.onCreateView(parent, name, attrs);
        }
        return null;
    }
}
