package com.example.administrator.matata_android.httputils;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * 自定义OkhttpManager类管理https证书，添加头信息等
 */
public class OkhttpManager {
    static private OkhttpManager mOkhttpManager=null;
    private InputStream mTrustrCertificate;
    static public OkhttpManager getInstance()
    {
        if(mOkhttpManager==null)
        {
            mOkhttpManager=new OkhttpManager();
        }
        return mOkhttpManager;
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }


    public void setTrustrCertificates(InputStream in)
    {
        mTrustrCertificate=in;
    }


    public InputStream getTrustrCertificates()
    {
        return mTrustrCertificate;
    }

    public OkHttpClient build()
    {
        OkHttpClient okHttpClient=null;
        if(getTrustrCertificates()!=null)
        {
            X509TrustManager trustManager;
            SSLSocketFactory sslSocketFactory;
            try {
                trustManager = trustManagerForCertificates(getTrustrCertificates());
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[] { trustManager } , null);
                sslSocketFactory = sslContext.getSocketFactory();
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
            okHttpClient=new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20,TimeUnit.SECONDS)
                    .writeTimeout(20,TimeUnit.SECONDS)
                    //这里可以根据自己的机型设置同时连接的个数和时间,我这里8个,和每个保持时间15s
                    .connectionPool(new ConnectionPool(8,15,TimeUnit.SECONDS))
                    //添加拦截器
                    .addInterceptor(new SaveCookiesInterceptor())
                    .addInterceptor(new AddCookiesInterceptor())
                    .build();
        }
        else
        {
            okHttpClient=new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20,TimeUnit.SECONDS)
                    .writeTimeout(20,TimeUnit.SECONDS)
                    //这里可以根据自己的机型设置同时连接的个数和时间,我这里8个,和每个保持时间15s
                    .connectionPool(new ConnectionPool(8,15,TimeUnit.SECONDS))
                    //添加拦截器
                    .addInterceptor(new SaveCookiesInterceptor())
                    .addInterceptor(new AddCookiesInterceptor())
                    .build();
        }
        return okHttpClient;
    }
}

