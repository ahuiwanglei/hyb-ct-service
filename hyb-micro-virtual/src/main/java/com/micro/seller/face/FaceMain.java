package com.micro.seller.face;

public class FaceMain {

    //设置APPID/AK/SK
//    public static final String APP_ID = "15038406";
//    public static final String API_KEY = "MokTwrNk8N6DALvKxDGVQY6o";
//    public static final String SECRET_KEY = "oeHmNbILCPe5TlCdKij5awNQamjGgD9x";
//
//    public static void main(String[] args) {
//        System.setProperty("aip.log4j.conf", "/Users/wanglei/Documents/workspace/hyb-ct-service/hyb-micro-seller/src/main/resources/log4j.properties");
//
//        // 初始化一个AipFace
//        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
//
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
////        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
////        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
//
//        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
//        // 也可以直接通过jvm启动参数设置此环境变量
//
//        // 调用接口
//        // 传入可选参数调用接口
//        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("face_field", "age,beauty,expression,face_shape,gender,glasses,landmark,race,quality,eye_status,emotion,face_type");
//        options.put("face_type", "LIVE");
//
//        String image = getImageStr("/Users/wanglei/Documents/myface.jpeg");
//
//        String imageType = "BASE64";
//
//        JSONObject res = client.detect(image,imageType, options);
//        System.out.println(res.toString());
//
//
////        JSONObject res2 = client.search();
//
//    }
//
//
//    /**
//     * @Description: 根据图片地址转换为base64编码字符串
//     * @Author:
//     * @CreateTime:
//     * @return
//     */
//    public static String getImageStr(String imgFile) {
//        InputStream inputStream = null;
//        byte[] data = null;
//        try {
//            inputStream = new FileInputStream(imgFile);
//            data = new byte[inputStream.available()];
//            inputStream.read(data);
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // 加密
//        BASE64Encoder encoder = new BASE64Encoder();
//        return encoder.encode(data);
//    }
}
