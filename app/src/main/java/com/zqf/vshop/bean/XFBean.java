package com.zqf.vshop.bean;

import java.util.ArrayList;

public class XFBean {

        public ArrayList<WS> ws;
        public class WS{
            public ArrayList<CW> cw;
        }
        public class CW{
            public String w;
        }

}