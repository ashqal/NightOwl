package android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by hzqiujiadi on 15/11/6.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class InjectLayoutInflater extends LayoutInflater {
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.app."
    };

    private static final String TAG = "InjectLayoutInflater";

    public InjectLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new InjectLayoutInflater(this,newContext);
    }

    @Override protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        for (String prefix : sClassPrefixList) {
            try {
                View view = createView(name, prefix, attrs);
                if (view != null) {
                    Log.d(TAG,"onCreateView1:" + view);
                    return view;
                }
            } catch (ClassNotFoundException e) {
                // In this case we want to let the base class take a crack
                // at it.
            }
        }
        View view2 = super.onCreateView(name, attrs);
        Log.d(TAG,"onCreateView1:" + view2);
        return view2;
    }

    @Override
    public View inflate(XmlPullParser parser, ViewGroup root, boolean attachToRoot) {
        View v = super.inflate(parser, root, attachToRoot);
        Log.d(TAG,"inflate:" + v);
        return v;
    }


    @Override
    protected View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        View v = super.onCreateView(parent, name, attrs);
        Log.d(TAG,"onCreateView2:" + v);
        return v;
    }
}
