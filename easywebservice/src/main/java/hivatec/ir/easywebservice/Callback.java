package hivatec.ir.easywebservice;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ashkan on 9/16/17.
 */

public interface Callback {


    public abstract class A<A> {

        Type tokenType;

        String key = "";



        public A() {

            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            tokenType = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public A(String keyA){

            this.key = keyA;

            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            tokenType = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);

        }

        public abstract void onSuccess(A a);
        public abstract void onError(String error);
    }


    public abstract class AB<A, B> {

        Type tokenType1;
        Type tokenType2;

        String keya = "";
        String keyb = "";

        public AB(String keyA, String keyB){

            this.keya = keyA;
            this.keyb = keyB;

            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            tokenType1 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
            tokenType2 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[1]);

        }

        public abstract void onSuccess(A a, B b);
        public abstract void onError(String error);
    }

    public abstract class ABC<A, B, C> {

        Type tokenType1;
        Type tokenType2;
        Type tokenType3;

        String keya = "";
        String keyb = "";
        String keyc = "";

        public ABC(String keyA, String keyB, String keyC){

            this.keya = keyA;
            this.keyb = keyB;
            this.keyc = keyC;

            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            tokenType1 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
            tokenType2 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[1]);
            tokenType3 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[2]);

        }

        public ABC() {

            this("","","");
        }

        public abstract void onSuccess(A a, B b, C c);
        public abstract void onError(String error);
    }

    public abstract class ABCD<A, B, C, D> {

        Type tokenType1;
        Type tokenType2;
        Type tokenType3;
        Type tokenType4;

        String keya = "";
        String keyb = "";
        String keyc = "";
        String keyd = "";

        public ABCD(String keyA, String keyB, String keyC, String keyD){

            this.keya = keyA;
            this.keyb = keyB;
            this.keyc = keyC;
            this.keyd = keyD;

            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            tokenType1 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
            tokenType2 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[1]);
            tokenType3 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[2]);
            tokenType4 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[3]);

        }

        public ABCD() {
            this("","","","");
        }

        public abstract void onSuccess(A a, B b, C c, D d);
        public abstract void onError(String error);
    }

    public abstract class ABCDE<A, B, C, D, E> {

        Type tokenType1;
        Type tokenType2;
        Type tokenType3;
        Type tokenType4;
        Type tokenType5;

        String keya = "";
        String keyb = "";
        String keyc = "";
        String keyd = "";
        String keye = "";

        public ABCDE(String keyA, String keyB, String keyC, String keyD, String keyE){

            this.keya = keyA;
            this.keyb = keyB;
            this.keyc = keyC;
            this.keyd = keyD;
            this.keye = keyE;

            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            tokenType1 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
            tokenType2 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[1]);
            tokenType3 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[2]);
            tokenType4 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[3]);
            tokenType5 = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[4]);

        }


        public abstract void onSuccess(A a, B b, C c, D d, E e);
        public abstract void onError(String error);
    }

}

