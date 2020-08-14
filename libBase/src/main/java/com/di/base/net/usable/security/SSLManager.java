package com.di.base.net.usable.security;

import androidx.annotation.Nullable;

import com.di.base.tool.ApplicationTool;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class SSLManager {

    /**
     * 证书名称，要放到assets目录下面
     */
    private static final String CERTIFICATE_FILENAME = "";

    private static volatile SSLManager sInstance;

    private SSLManager() {
    }

    public static SSLManager getInstance() {
        if (sInstance == null) {
            synchronized (SSLManager.class) {
                if (sInstance == null) {
                    sInstance = new SSLManager();
                }
            }
        }
        return sInstance;
    }

    private SSLContext getSSLContext() {
        SSLContext sslContext = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            InputStream certificateIS = ApplicationTool.getInstance().getApplication().getAssets().open(CERTIFICATE_FILENAME);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            String certificateAlias = Integer.toString(0);
            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificateIS));
            //SSL、SSLv2、SSLv3、TLS、TLSv1、TLSv1.1、TLSv1.2 协议
            sslContext = SSLContext.getInstance("TLS");
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    /**
     * 获取对应的证书信息
     */
    public SSLSocketFactory getSSLSocketFactory() {
        SSLContext sslContext = getSSLContext();
        if (sslContext == null) {
            return null;//直接返回null就行，okHttp中有默认的值
        }
        return sslContext.getSocketFactory();
    }

    /**
     * 获取对应的证书信息
     */
    public X509TrustManager getTrustManager() {
        return trustManager(getSSLSocketFactory());
    }

    /**
     * 获取对应的证书信息
     *
     * @param sslSocketFactory 需要传入这个东西！！！
     */
    @Nullable
    public X509TrustManager trustManager(SSLSocketFactory sslSocketFactory) {
        try {
            Class<?> sslContextClass = Class.forName("sun.security.ssl.SSLContextImpl");
            Object context = readFieldOrNull(sslSocketFactory, sslContextClass, "context");
            if (context == null) return null;
            return readFieldOrNull(context, X509TrustManager.class, "trustManager");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Nullable
    static <T> T readFieldOrNull(Object instance, Class<T> fieldType, String fieldName) {
        for (Class<?> c = instance.getClass(); c != Object.class; c = c.getSuperclass()) {
            try {
                Field field = c.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(instance);
                if (!fieldType.isInstance(value)) return null;
                return fieldType.cast(value);
            } catch (NoSuchFieldException ignored) {
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            }
        }

        // Didn't find the field we wanted. As a last gasp attempt, try to find the value on a delegate.
        if (!fieldName.equals("delegate")) {
            Object delegate = readFieldOrNull(instance, Object.class, "delegate");
            if (delegate != null) return readFieldOrNull(delegate, fieldType, fieldName);
        }

        return null;
    }

    //分割线-----上面是需要添加证书的时候的处理，下面的逻辑是信任所有的证书

    /**
     * 添加证书，可以保证接口请求更加安全，之前的开发过程中，一般不会写死证书，
     *
     * 原因：
     * 1.申请正规的证书，需要向相关机构申请，会花费一定的费用；
     * 2.申请下来的证书有一定的时间期限，到时间之后，需要续费或更换证书；
     * 3.加入更换证书，之前使用旧版本证书的程序，无法正常访问接口；
     *
     * 为了解决上述的不必要的麻烦，一般Android端会信任所有的证书，但是对于iOS需要配置相应的证书信息；
     * */

    /**
     * 信任所有证书的实现：
     * */

    /**
     * 获取hostname验证类
     * */
    public HostnameVerifier getHostnameVerifier(){
        return new HostnameVerifier(){

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;//允许所有证书
            }
        };
    }

    public SSLContext getNoVerifySSLContext(){
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext;
    }


    /**
     * 暴露两个方法，一个支持证书验证，一个信任所有证书
     * */

    /**
     * 一个支持证书验证，记得配置{@see CERTIFICATE_FILENAME}
     * */
    public OkHttpClient.Builder OkHttpSupportCertVerify(OkHttpClient.Builder okHttpClientBuilder){
        return okHttpClientBuilder.sslSocketFactory(getSSLSocketFactory(), getTrustManager());
    }

    /**
     * 信任所有证书
     * */
    public OkHttpClient.Builder OkHttpSupportAllCerts(OkHttpClient.Builder okHttpClientBuilder){
        SSLSocketFactory sslSocketFactory = getNoVerifySSLContext().getSocketFactory();
        X509TrustManager trustManager = trustManager(sslSocketFactory);
        return  okHttpClientBuilder
                .hostnameVerifier(getHostnameVerifier())
                .sslSocketFactory(sslSocketFactory, trustManager);
    }
}
