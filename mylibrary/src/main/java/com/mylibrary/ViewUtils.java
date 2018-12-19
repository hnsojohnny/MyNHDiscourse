package com.mylibrary;

import android.app.Activity;
import android.view.View;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils {

    public static void inject(Activity activity){
        inject(new ViewFinder(activity),activity);
    }

    public static void inject(View view){
        inject(new ViewFinder(view),view);
    }

    public static void inject(View view, Object object){
        inject(new ViewFinder(view),object);
    }

    private static void inject(ViewFinder finder, Object object){
        injectLayout(finder, object);
        injectField(finder, object);
        injectEvent(finder, object);
    }

    private static void injectLayout(ViewFinder finder, Object object){
        Class clz = object.getClass();
        LayoutById layoutById = (LayoutById) clz.getAnnotation(LayoutById.class);
        int layoutId = layoutById.value();
        if(layoutById != null){
            finder.setContentView(layoutId);
        }
    }

    private static void injectField(ViewFinder finder, Object object){
        Class clz = object.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            ViewById viewById = field.getAnnotation(ViewById.class);
            if(viewById != null){
                int viewId = viewById.value();
                View view = finder.findViewById(viewId);
                try {
                    field.set(object,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectEvent(ViewFinder finder, Object object){
        Class clz = object.getClass();
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods){
            method.setAccessible(true);
            ViewOnClick viewOnClick = method.getAnnotation(ViewOnClick.class);
            if (viewOnClick != null) {
                int[] viewId = viewOnClick.value();
                for (int i = 0;i<viewId.length;i++) {
                    View view = finder.findViewById(viewId[i]);
                    view.setOnClickListener(new DeclaredOnClickListener(method, view));
                }
            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener{

        private Method method;
        private View view;

        public DeclaredOnClickListener(Method method, View view) {
            this.method = method;
            this.view = view;
        }

        @Override
        public void onClick(View v) {
            try {
                method.invoke(view.getContext(), view);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(
                        "Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException(
                        "Could not execute method for android:onClick", e);
            }
        }
    }
}
