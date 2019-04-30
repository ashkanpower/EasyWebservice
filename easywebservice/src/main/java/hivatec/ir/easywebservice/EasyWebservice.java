package hivatec.ir.easywebservice;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static javax.xml.transform.OutputKeys.MEDIA_TYPE;

/**
 * Created by ashkan on 9/16/17.
 */

public class EasyWebservice {

    public static String JSON_ERROR = "Error parsing JSON";
    public static String REQUEST_ERROR = "Request error";
    public static String SERVER_ERROR = "Server error";

    private HashMap<String, Object> bodies = new HashMap<>();
    private HashMap<String, String> headers = new HashMap<>();

    private static HashMap<String, Object> gBodies = new HashMap<>();
    private static HashMap<String, String> gHeaders = new HashMap<>();

    OkHttpClient client = new OkHttpClient();

    private String urlStr = "";
    private String fakeJson = "";
    private Method method = Method.POST;
    private int fakeJsonDelay = 3000;

    public EasyWebservice(String url){

        this.urlStr = url;
    }


    public EasyWebservice method(Method method){

        this.method = method;
        return this;
    }

    public EasyWebservice fakeJson(String json) {

        this.fakeJson = json;
        return this;
    }

    public EasyWebservice fakeJsonDelay(int randStart, int randEnd) {

        this.fakeJsonDelay = (int) (Math.random() * (randEnd - randStart) + randStart);
        Log.i("webservice", "delay : " + fakeJsonDelay + "");
        return this;
    }



    public EasyWebservice addParam(String key, Object value) {

        this.bodies.put(key, value);
        return this;
    }


    public EasyWebservice addParam(JSONObject json) {


        Iterator<String> iter = json.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                Object value = json.get(key);
                //String val = value.toString();
                this.bodies.put(key, value);
            } catch (JSONException e) {
                // Something went wrong!
            }
        }

        return this;
    }


    public EasyWebservice addParam(Object object) {

        try {
            JSONObject json = new JSONObject(new Gson().toJson(object));
            addParam(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return this;
    }


    public EasyWebservice addHeader(String key, String value) {

        this.headers.put(key, value);
        return this;
    }

    public static void addGlobalHeader(String key, String value){

        getGlobals();
        gHeaders.put(key, value);
        putGlobals();
    }

    public static void addGlobalParam(String key, Object value){

        getGlobals();
        gBodies.put(key, value);
        putGlobals();
    }

    private static void getGlobals(){

        //gHeaders = SharedPreference.getObject("easywebservice_gheaders", new TypeToken<HashMap<String, String>>() {}.getClass());

//        String headers = SharedPreference.getString("easywebservice_gheaders", "");
//        String bodies = SharedPreference.getString("easywebservice_gbodies", "");
//        Gson gson = new Gson();
//
//        gHeaders = gson.fromJson(headers, new TypeToken<HashMap<String,String>>() {}.getType());
//        gBodies = gson.fromJson(bodies, new TypeToken<HashMap<String,Object>>() {}.getType());
    }

    private static void putGlobals(){

        //gHeaders = SharedPreference.getObject("easywebservice_gheaders", new TypeToken<HashMap<String, String>>() {}.getClass());

//        Gson gson = new Gson();
//
//        String headers = gson.toJson(gHeaders);
//        String bodies = gson.toJson(gBodies);
//
//        SharedPreference.putString("easywebservice_gheaders", headers);
//        SharedPreference.putString("easywebservice_gbodies", bodies);
    }


    public static void removeGlobalHeader(String key){

        gHeaders.remove(key);
    }

    public static void removeGlobalBody(String key){

        gBodies.remove(key);
    }

    private interface CallbackString {
        void onSuccess(String res);

        void onError(String error);
    }

    @SuppressLint("StaticFieldLeak")
    private void _perform(final CallbackString callback) {

        if(fakeJson != null && !fakeJson.equals("")){

            Log.i("webservice", (method == Method.POST ? "POST : " : "GET : ") + urlStr);
            Log.i("webservice", "headers : " + new Gson().toJson(headers));
            Log.i("webservice", "bodies : " + new Gson().toJson(bodies));

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Log.i("webservice", "--> " + urlStr + " : " + fakeJson);
                    callback.onSuccess(fakeJson);
                }
            }, fakeJsonDelay);

            return;
        }

        getGlobals();

        AsyncTask task = new AsyncTask() {

            @Override
            protected OkHttpResponse doInBackground(Object[] objects) {

                MultipartBody.Builder mBody = new MultipartBody.Builder();
                mBody.setType(MultipartBody.FORM);

                HttpUrl.Builder httpBuider = HttpUrl.parse(urlStr).newBuilder();


                Request.Builder builder = new Request.Builder();

                for (HashMap.Entry<String, String> entry : gHeaders.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    builder.addHeader(key, value);
                }

                for (HashMap.Entry<String, String> entry : headers.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    builder.addHeader(key, value);
                }


                Request request = null;
                fillBodyParams(mBody);

                switch (method){

                    case POST: default:
                        request = builder.url(httpBuider.build()).post(mBody.build()).build();
                        break;
                    case GET:
                        fillQueryParams(httpBuider);
                        request = builder.url(httpBuider.build()).get().build();
                        break;
                    case PATCH:
                        request = builder.url(httpBuider.build()).patch(mBody.build()).build();
                        break;
                    case PUT:
                        request = builder.url(httpBuider.build()).put(mBody.build()).build();
                        break;
                    case DELETE:
                        request = builder.url(httpBuider.build()).delete(mBody.build()).build();
                        break;
                }


                try {

                    Log.i("webservice", (method.toString()) + httpBuider.build().toString());
                    Log.i("webservice", "headers : " + new Gson().toJson(headers));
                    Log.i("webservice", "bodies : " + new Gson().toJson(bodies));

                    Response res = client.newCall(request).execute();
                    OkHttpResponse cres = new OkHttpResponse();
                    cres.code = res.code();
                    cres.res = res;

                    try {

                        if (res.isSuccessful()) {
                            cres.body = res.body().string();
                            Log.i("webservice", "--> " + urlStr + " : " + cres.body);
                            return cres;
                        } else {
                            Log.e("webservice", "--> " + urlStr + " : " + res);
                            Log.e("webservice", "--> response body : " + "\n" + res.body().string());

                            cres.error = res.message() ;
                            return  cres;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        cres.error = res.message();
                        return  cres;
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    return null;
                }

            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);


                try {
                    if (o == null) {
                        callback.onError(REQUEST_ERROR);
                        return;
                    }

                    OkHttpResponse res = (OkHttpResponse) o;

                    if (res.res.isSuccessful()) {
                        callback.onSuccess(res.body);
                    } else {
                        callback.onError(res.error);
                    }
                }catch (Exception e){

                    Log.e("webservice" , " error while calling callback : " + urlStr + " : " + e.toString());
                    e.printStackTrace();
                }


            }
        };

        task.execute();

    }


    private void fillBodyParams(MultipartBody.Builder mBody){

        for (HashMap.Entry<String, Object> entry : gBodies.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof File) {

                File file = (File) value;
                mBody.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(MEDIA_TYPE), file));
            } else if (value instanceof String) {

                mBody.addFormDataPart(key, value.toString());
            } else {

                mBody.addFormDataPart(key, new Gson().toJson(value));
            }
        }

        for (HashMap.Entry<String, Object> entry : bodies.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof File) {

                File file = (File) value;
                mBody.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(MEDIA_TYPE), file));
            } else if (value instanceof String) {

                mBody.addFormDataPart(key, value.toString());
            } else {

				String json = new Gson().toJson(value);

				if(value instanceof JSONObject){
					json = json.substring("{\"nameValuePairs\":".length(), json.length() - 1);
				}

				if(value instanceof JSONArray){
					json = json.substring("{\"values\":".length(), json.length() - 1);
				}

				mBody.addFormDataPart(key, json);
            }
        }
    }


    private String getJsonObject(JSONObject object){
    	return new Gson().toJson(object);
	}


    private void fillQueryParams(HttpUrl.Builder urlBuilder){

        for (HashMap.Entry<String, Object> entry : gBodies.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {

                urlBuilder.addQueryParameter(key, value.toString());
            }
        }

        for (HashMap.Entry<String, Object> entry : bodies.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {

                urlBuilder.addQueryParameter(key, value.toString());
            }
        }
    }

    public <A> void call(final Callback.A<A> callback) {

        _perform(new CallbackString() {
            @Override
            public void onSuccess(String res) {


                try {


                    Object obj = getJsonValue(res, callback.key, callback.tokenType);

                    //if A is String, and key is null then sending back json string
                    if(obj == null){

                        callback.onSuccess((A) res);
                    }else {

                        callback.onSuccess((A) obj);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                }
            }

            @Override
            public void onError(String error) {

                callback.onError(error);

            }
        });
    }

    public <A, B> void call(final Callback.AB<A, B> callback) {

        _perform(new CallbackString() {
            @Override
            public void onSuccess(String res) {


                try {


                    Object obja = getJsonValue(res, callback.keya, callback.tokenType1);
                    Object objb = getJsonValue(res, callback.keyb, callback.tokenType2);

                    callback.onSuccess((A) obja, (B) objb);


                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                }
            }

            @Override
            public void onError(String error) {

                callback.onError(error);

            }
        });
    }


    public <A, B, C> void call(final Callback.ABC<A, B, C> callback) {

        _perform(new CallbackString() {
            @Override
            public void onSuccess(String res) {


                try {


                    Object obja = getJsonValue(res, callback.keya, callback.tokenType1);
                    Object objb = getJsonValue(res, callback.keyb, callback.tokenType2);
                    Object objc = getJsonValue(res, callback.keyc, callback.tokenType3);

                    callback.onSuccess((A) obja, (B) objb, (C) objc);


                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                }
            }

            @Override
            public void onError(String error) {

                callback.onError(error);

            }
        });
    }


    public <A, B, C, D> void call(final Callback.ABCD<A, B, C, D> callback) {

        _perform(new CallbackString() {
            @Override
            public void onSuccess(String res) {


                try {


                    Object obja = getJsonValue(res, callback.keya, callback.tokenType1);
                    Object objb = getJsonValue(res, callback.keyb, callback.tokenType2);
                    Object objc = getJsonValue(res, callback.keyc, callback.tokenType3);
                    Object objd = getJsonValue(res, callback.keyd, callback.tokenType4);
                    callback.onSuccess((A) obja, (B) objb, (C) objc, (D) objd);


                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                }
            }

            @Override
            public void onError(String error) {

                callback.onError(error);

            }
        });
    }

    public <A, B, C, D, E> void call(final Callback.ABCDE<A, B, C, D, E> callback) {

        _perform(new CallbackString() {
            @Override
            public void onSuccess(String res) {


                try {


                    Object obja = getJsonValue(res, callback.keya, callback.tokenType1);
                    Object objb = getJsonValue(res, callback.keyb, callback.tokenType2);
                    Object objc = getJsonValue(res, callback.keyc, callback.tokenType3);
                    Object objd = getJsonValue(res, callback.keyd, callback.tokenType4);
                    Object obje = getJsonValue(res, callback.keyd, callback.tokenType5);
                    callback.onSuccess((A) obja, (B) objb, (C) objc, (D) objd, (E) obje);


                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    callback.onError(JSON_ERROR);
                }
            }

            @Override
            public void onError(String error) {

                callback.onError(error);

            }
        });
    }


    private static <T> Object getPrimitiveValues(JSONObject json, String key, Type type) throws JSONException {


        if (type == Integer.class) {

            return json.getInt(key);
        }

        if (type == String.class) {

            return json.getString(key);
        }

        if (type == Boolean.class) {

            return json.getBoolean(key);
        }

        if (type == Double.class) {

            return json.getDouble(key);
        }


        return null;
    }

    private static <T> Object getJsonValue(String jsonStr, String key, final Type type) throws JSONException, NumberFormatException {

        try {

            jsonStr = jsonStr.replace("\n", "");

            Gson gSon = new GsonBuilder().create();

            if (jsonStr.equals("")) {
                return null;
            }

            String firstChar = String.valueOf(jsonStr.charAt(0));

            if (firstChar.equalsIgnoreCase("[")) {
                //json array

                return gSon.fromJson(jsonStr, type);

            } else if (firstChar.equalsIgnoreCase("{")) {
                //json object


                JSONObject json = new JSONObject(jsonStr);

                if (json.has(key)) {

                    Object jsonKey = json.get(key);

                    if (jsonKey instanceof JSONArray) {
                        // It's an array

                        return gSon.fromJson(jsonKey.toString(), type);

                    } else if (jsonKey instanceof JSONObject) {
                        // It's an object

                        return gSon.fromJson(jsonKey.toString(), type);
                    } else {

                        // It's something else, like a string or number
                        return getPrimitiveValues(json, key, type);
                    }
                }else{

                    return gSon.fromJson(jsonStr, type);
                }

            } else {

                if (type == Integer.class) {
                    return Integer.parseInt(jsonStr);
                }

                if (type == String.class) {

                    return jsonStr;
                }

                if (type == Boolean.class) {

                    return Boolean.parseBoolean(jsonStr);
                }

                if (type == Double.class) {

                    return Boolean.parseBoolean(jsonStr);
                }
            }

        } catch (JSONException e) {
			Log.e("webservice", "error while parsing " + key);
			Log.e("webservice", e.toString());
            e.printStackTrace();
            return null;
        }catch (IllegalStateException e){
            Log.e("webservice", "error while parsing " + key);
			Log.e("webservice", e.toString());
			e.printStackTrace();
            return null;
        }catch (JsonSyntaxException e){
            Log.e("webservice", "error while parsing " + key);
			Log.e("webservice", e.toString());
			e.printStackTrace();
            return null;
        }catch (Exception e){
            Log.e("webservice", "error while parsing " + key);
			Log.e("webservice", e.toString());
			e.printStackTrace();
            return null;
        }

        return null;
    }


    private class OkHttpResponse {

        Response res;
        String body;
        int code;
        String error;
    }
}
