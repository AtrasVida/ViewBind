package com.atrasvida.viewbind;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by AvidA on 7/22/2016
 */
public class ViewBind {
    static final String LOG = "LOG_ui";


    public void parseUi(android.view.View view) {
        fillUi(this, view);
    }


    public void joinView(Activity activity) {
        joinView(activity, activity.getWindow().getDecorView());
    }

    public void joinView(Fragment fragment) {
        joinView(fragment, fragment.getView());
    }

    public void joinView(Dialog dialog, Object fields, String tag) {
        joinView(fields, dialog.getWindow().getDecorView(), tag);
    }

    public void joinView(Object fields, android.view.View container) {
        joinView(fields, container, "");
    }

    public void joinView(final Object fields, android.view.View container, String tag) {
        r = container.getResources();
        packagename = container.getContext().getPackageName();

        for (Field field : fields.getClass().getDeclaredFields()) {

            if (field.isAnnotationPresent(Res.View.class)) {
                Annotation annotation = field.getAnnotations()[0];
                if (tag.equals(((Res.View) annotation).value()))
                    fillField(fields, container, field, getId(field.getName()));

            } else if (field.isAnnotationPresent(Res.View_.class)) {
                Annotation annotation = field.getAnnotations()[0];
                if (tag.equals(((Res.View_) annotation).value()))
                    fillField(fields, container, field, getId(upperCaseToLowerCase(field.getName())));

            } else if (field.isAnnotationPresent(Res.id.class)) {
                Annotation annotation = field.getAnnotations()[0];
                if (tag.equals(((Res.id) annotation).tag()))
                    fillField(fields, container, field, ((Res.id) annotation).value());

            } else if (field.isAnnotationPresent(Res.ids.class)) {
                Annotation annotation = field.getAnnotations()[0];
                if (tag.equals(((Res.ids) annotation).tag())) {
                    int[] ids = ((Res.ids) annotation).value();
                    fillField(fields, container, field, ids);
                }
            } else if (tag.equals("")) {

                if (field.isAnnotationPresent(Res.String.class)) {//++++++++++++++++++++++++++++++
                    fillString(fields, field, getStringId(field.getName()));

                } else if (field.isAnnotationPresent(Res.String_.class)) {
                    fillString(fields, field, getStringId(upperCaseToLowerCase(field.getName())));

                } else if (field.isAnnotationPresent(Res.StringId.class)) {
                    Annotation annotation = field.getAnnotations()[0];
                    fillString(fields, field, ((Res.StringId) annotation).value());

                } else if (field.isAnnotationPresent(Res.Strings.class)) {
                    Annotation annotation = field.getAnnotations()[0];
                    int[] ids = ((Res.Strings) annotation).value();
                    fillString(fields, field, ids);

                } else if (field.isAnnotationPresent(Res.Color.class)) {//---------------------------
                    fillColor(fields, field, getColorId(field.getName()));

                } else if (field.isAnnotationPresent(Res.Color_.class)) {
                    fillColor(fields, field, getColorId(upperCaseToLowerCase(field.getName())));

                } else if (field.isAnnotationPresent(Res.ColorId.class)) {
                    Annotation annotation = field.getAnnotations()[0];
                    fillColor(fields, field, ((Res.ColorId) annotation).value());

                } else if (field.isAnnotationPresent(Res.Colors.class)) {
                    Annotation annotation = field.getAnnotations()[0];
                    int[] ids = ((Res.Colors) annotation).value();
                    fillColor(fields, field, ids);
                }
            }
        }

        for (final Method method : fields.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Res.OnClick.class)) {
                Annotation annotation = method.getAnnotations()[0];

                if (tag.equals(((Res.OnClick) annotation).tag())) {
                    int id = ((Res.OnClick) annotation).value();
                    setListener(fields, method, container, id);
                }
            }
            if (method.isAnnotationPresent(Res.OnClicks.class)) {
                Annotation annotation = method.getAnnotations()[0];

                if (tag.equals(((Res.OnClicks) annotation).tag())) {
                    int[] ids = ((Res.OnClicks) annotation).value();
                    setListeners(fields, method, container, ids);
                }
            }
        }
    }

    private void fillColor(Object object, Field field, int id) {
        fillColor(object, field, new int[]{id});
    }

    private void fillColor(Object object, Field field, int id[]) {
        try {
            if (field.get(object) instanceof ArrayList) {
                ArrayList<Integer> a = new ArrayList<>();
                for (int anId : id) {
                    a.add(r.getColor(anId));
                }
                field.set(object, a);
            } else

                field.set(object, r.getColor(id[0]));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void fillString(Object object, Field field, int id) {
        fillString(object, field, new int[]{id});
    }

    private void fillString(Object object, Field field, int id[]) {
        try {
            if (field.get(object) instanceof ArrayList) {
                ArrayList<String> a = new ArrayList<>();
                for (int anId : id) {
                    a.add(r.getString(anId));
                }
                field.set(object, a);
            } else

                field.set(object, r.getString(id[0]));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void fillUi(Object object, android.view.View view) {
        Field[] fields = object.getClass().getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            fillField(object, view, field, getId(fieldName));
        }
    }

    private void fillField(Object object, android.view.View view, Field field, int id) {
        fillField(object, view, field, new int[]{id});
    }

    private void fillField(Object object, android.view.View view, Field field, int[] id) {

        try {
            if (field.get(object) instanceof ArrayList) {
                ArrayList<android.view.View> a = new ArrayList<>();
                for (int anId : id) {
                    a.add(view.findViewById(anId));
                }
                field.set(object, a);
            } else
                field.set(object, view.findViewById(id[0]));

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e(LOG, "Exception : Can not Access " + field.getName() + " --> " + field.getType().getCanonicalName());
            //e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Log.e(LOG, "Exception : Illegal Argument " + field.getName() + " --> " + field.getType().getCanonicalName());
            //e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG, "Exception : Error on --> View" + field.getName() + " --> " + field.getType().getCanonicalName());
        }

    }

    Resources r;
    String packagename;

    private int getId(String fieldId) {
        return r.getIdentifier(fieldId, "id", packagename);
    }

    private int getStringId(String fieldId) {
        return r.getIdentifier(fieldId, "string", packagename);
    }

    private int getColorId(String fieldId) {
        return r.getIdentifier(fieldId, "color", packagename);
    }

    private void setListeners(final Object fields, final Method method, View container, int[] ids) {
        for (int i = 0; i < ids.length; i++) {
            setListener(fields, method, container, ids[i]);
        }
    }


    private void setListener(final Object fields, final Method method, View container, int id) {
        Class<?>[] c = method.getParameterTypes();
        boolean isI = false;
        try {
            Class h = Class.forName(c[0].getName()).asSubclass(View.class);
            isI = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (c.length == 1) {
            setListener(fields, method, isI, container, id);
        } else
            setListener(fields, method, false, container, id);

    }

    private void setListener(final Object fields, final Method method, final boolean returnSelf, View container, int id) {
        View view = container.findViewById(id);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (returnSelf)
                        method.invoke(fields, v);
                    else
                        method.invoke(fields);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static String upperCaseToLowerCase(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                result += "_" + str.charAt(i);
            } else result += str.charAt(i);
        }
        return result.toLowerCase();
    }




    /*public static void fillViewsCaption(View v, ArrayList<Caption> captions) {
        ArrayList<View> allChildren = getAllChildren(v);
        for (View children : allChildren) {
            if (children.getTag() != null) {
                for (Caption caption : captions) {
                    if ((children.getTag().equals(caption.getTitle()))) {
                        if (children instanceof EditText) {
                            ((EditText) children).setHint(caption.getText());
                        } else if (children instanceof TextView) {
                            ((TextView) children).setText(caption.getText());
                        }
                    }

                }
            }
        }
    }

    private static ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }*/


}
