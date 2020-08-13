package com.di.base.net.usable.security;


import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * https证书验证
 */
public class SSLSocketClient {
//    public static InputStream getCertificateFiles() {
//        InputStream certificate = null;
//        try {
//            certificate = AppTool.getInstance().getApplication().getAssets().open("domain.crt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return certificate;
//    }

    /**
     * 载入证书
     */
//    public static SSLSocketFactory getSSLSocketFactory(InputStream... certificates) {
//        try {
//            //用我们的证书创建一个keystore
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            keyStore.load(null);
//            int index = 0;
//            for (InputStream certificate : certificates) {
//                String certificateAlias = "server" + Integer.toString(index++);
//                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
//                try {
//                    if (certificate != null) {
//                        certificate.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            //创建一个trustmanager，只信任我们创建的keystore
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            TrustManagerFactory trustManagerFactory =
//                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore);
//            sslContext.init(
//                    null,
//                    trustManagerFactory.getTrustManagers(),
//                    new SecureRandom()
//            );
//            return sslContext.getSocketFactory();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static X509TrustManager getTrustManager() {
//        TrustManagerFactory trustManagerFactory;
//        TrustManager[] trustManagers = new TrustManager[0];
//
//        try {
//            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init((KeyStore) null);
//            trustManagers = trustManagerFactory.getTrustManagers();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }
//
//        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
//            throw new IllegalStateException("Unexpected default trust managers:"
//                    + Arrays.toString(trustManagers));
//        }
//
//        return (X509TrustManager) trustManagers[0];
//    }

    //获取HostnameVerifier
    public static HostnameVerifier getHostnameVerifier() {
        return (String s, SSLSession sslSession) -> true;
    }

    //获取这个SSLSocketFactory
    //通过这个类我们可以获得SSLSocketFactory，这个东西就是用来管理证书和信任证书的
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取TrustManager
    public static TrustManager[] getTrustManager() {
        //不校检证书链
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        //不校检客户端证书
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        //不校检服务器证书
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                        //OKhttp3.0以前返回null,3.0以后返回new X509Certificate[]{};
                    }
                }
        };
    }
}
