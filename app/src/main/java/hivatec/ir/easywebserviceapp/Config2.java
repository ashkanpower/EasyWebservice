package hivatec.ir.easywebserviceapp;


import java.util.List;

public class Config2 {

    public List<Categories> categories;
    public List<OrderTypes> orderTypes;
    public List<Options> options;

    public static class Categories {
        /**
         * id : 152
         * name : تعمیر کاپشن
         * parentId : 58
         * image : http://www.otooapp.com/image/200x200/2DfMhMNM.png
         * basePrice :
         * weight : 0
         * showIndex : true
         * unit : دست
         */

        public int id;
        public String name;
        public int parentId;
        public String image;
        public String basePrice;
        public String weight;
        public boolean showIndex;
        public String unit;
    }

    public static class OrderTypes {
        /**
         * id : 1
         * name : معمولی(3 الی 4 روز)
         * addedPrice : -
         * isExpress : 0
         */

        public String id;
        public String name;
        public String addedPrice;
        public int isExpress;
    }

    public static class Options {
        /**
         * id : 203
         * name : شستشو
         * catId : 143
         * image : http://www.otooapp.com/images/option-default.jpg
         * addedPrice : ۰ تومان
         * isExpress : 0
         * price : ۰ تومان
         * basePrice : 0
         * rules : [{"price":0,"count":1}]
         */

        public int id;
        public String name;
        public int catId;
        public String image;
        public String addedPrice;
        public int isExpress;
        public String price;
        public int basePrice;
        public List<Rules> rules;

        public static class Rules {
            /**
             * price : 0
             * count : 1
             */

            public int price;
            public int count;
        }
    }

}
