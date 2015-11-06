package android.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

/**
 * Created by hzqiujiadi on 15/11/6.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class FactoryWrapper {

    public static LayoutInflater.Factory wrap(LayoutInflater.Factory factory){
        return new FactoryWrapperImpl(factory);
    }

    public static LayoutInflater.Factory2 wrapF2(LayoutInflater.Factory2 factory){
        return new Factory2WrapperImpl(factory);
    }

    public static LayoutInflater.Factory2 wrapPrivate(LayoutInflater.Factory2 factory,InjectedLayoutInflater inflater){
        return new PrivateFactoryWrapperImpl(factory,inflater);
    }

    public static class FactoryWrapperImpl implements LayoutInflater.Factory {
        private LayoutInflater.Factory mFactory;
        private FactoryWrapperImpl(LayoutInflater.Factory factory) {
            mFactory = factory;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            View v = mFactory.onCreateView(name,context,attrs);
            InjectedLayoutInflater.handleOnCreateView(v, name, attrs);
            return v;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class Factory2WrapperImpl implements LayoutInflater.Factory2 {
        private LayoutInflater.Factory2 mFactory;

        private Factory2WrapperImpl(LayoutInflater.Factory2 factory) {
            mFactory = factory;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            View v = mFactory.onCreateView(name,context,attrs);
            InjectedLayoutInflater.handleOnCreateView(v, name, attrs);
            return v;
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View v = mFactory.onCreateView(name,context,attrs);
            InjectedLayoutInflater.handleOnCreateView(v, name, attrs);
            return v;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class PrivateFactoryWrapperImpl implements LayoutInflater.Factory2 {
        private LayoutInflater.Factory2 mFactory;
        private InjectedLayoutInflater mInflater;

        private PrivateFactoryWrapperImpl(LayoutInflater.Factory2 factory, InjectedLayoutInflater inflater) {
            mFactory = factory;
            mInflater = inflater;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            View v = mFactory.onCreateView(name,context,attrs);
            if ( v == null ) v = lastStep2CreateView(null,name,context,attrs);
            InjectedLayoutInflater.handleOnCreateView(v, name, attrs);
            return v;
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View v = mFactory.onCreateView(name,context,attrs);
            if ( v == null ) v = lastStep2CreateView(parent,name,context,attrs);
            InjectedLayoutInflater.handleOnCreateView(v, name, attrs);
            return v;
        }

        private View lastStep2CreateView(View parent, String name, Context context, AttributeSet attrs){
            View view = null;
            Object[] tmpConstructorArgs = mInflater.getConstructorArgs();

            final Object lastContext = tmpConstructorArgs[0];
            tmpConstructorArgs[0] = context;
            try {
                if (-1 == name.indexOf('.')) {
                    view = mInflater.onCreateView(parent, name, attrs);
                } else {
                    view = mInflater.createView(name, null, attrs);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                tmpConstructorArgs[0] = lastContext;
            }
            return view;
        }

        public LayoutInflater.Factory2 getCoreFactory() {
            return mFactory;
        }
    }
}
