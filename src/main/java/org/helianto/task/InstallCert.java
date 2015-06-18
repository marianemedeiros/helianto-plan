package org.helianto.task;
/*
 * Copyright 2006 Sun Microsystems, Inc.  All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

	/**
	 * Adaptei esta classe a partir de dicas do GUJ. Acidentalmente apaguei os créditos,
	 * se alguém puder indicé-los, agradeéo.
	 * 
	 * Sua finalidade é verificar se a conexéo com o servidor pode funcionar de forma 
	 * independente. 
	 * 
	 * Ela requer uma keystore no formato pkcs12, de onde séo extraédas as chaves para
	 * estabelecer uma conexéo SSL.
	 * 
	 * Os certificados apresentados pelo servidor não sobreescrevem aqueles que já estáo na
	 * jvm em "cacerts", ao invés disto é criado um novo armazém de acordo com campos estáticos
	 * desta classe.
	 * 
	 * @author originalmente?
	 * @author Mauricio Fernandes de Castro
	 *
	 */
	public class InstallCert {
		
//		private static String CONNECTION_KEYSTORE_LOCATION = "path/to/pfx";
//		private static String CONNECTION_KEYSTORE_PASSWORD = "secret";
//		private static String EXTRACTED_KEYSTORE_LOCATION = "jssecacerts";

		private static String CONNECTION_KEYSTORE_LOCATION = "/home/mauricio/iserv.pfx";
		private static String CONNECTION_KEYSTORE_PASSWORD = "013067634-9";
		private static String EXTRACTED_KEYSTORE_LOCATION = "cacerts";
	
		/**
		 * Bootstrap the code...
		 * 
		 * @param args
		 * @throws Exception
		 */
	    public static void main(String[] args) throws Exception {
		String host;
		int port;
		char[] passphrase;
		if ((args.length == 1) || (args.length == 2)) {
		    String[] c = args[0].split(":");
		    host = c[0];
		    port = (c.length == 1) ? 443 : Integer.parseInt(c[1]);
		    String p = (args.length == 1) ? "changeit" : args[1];
		    passphrase = p.toCharArray();
		} else {
		    System.out.println("Usage: java InstallCert <host>[:port] [passphrase]");
		    return;
		}
	
		// armazém de chaves confiéveis
		KeyStore ts = openTrustStore(passphrase);
		// gerenciador das chaves do usuário
		X509TrustManager tm = openTrustManager(ts, CONNECTION_KEYSTORE_LOCATION, CONNECTION_KEYSTORE_PASSWORD.toCharArray());
		
		SSLSocket socket = customSocketFactory((TrustManagerDecorator) tm, host, port);
		socket.setSoTimeout(10000);
		try {
		    System.out.println("Starting SSL handshake...");
		    socket.startHandshake();
		    socket.close();
		    System.out.println();
		    System.out.println("No errors, certificate is already trusted");
		} catch (SSLException e) {
		    System.out.println();
		    e.printStackTrace(System.out);
		}
	
		X509Certificate[] chain = ((TrustManagerDecorator) tm).chain;
		if (chain == null) {
		    System.out.println("Could not obtain server certificate chain");
		    return;
		}
	
		BufferedReader reader =
			new BufferedReader(new InputStreamReader(System.in));
	
		System.out.println();
		System.out.println("Server sent " + chain.length + " certificate(s):");
		System.out.println();
		MessageDigest sha1 = MessageDigest.getInstance("SHA1");
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		for (int i = 0; i < chain.length; i++) {
		    X509Certificate cert = chain[i];
		    System.out.println
		    	(" " + (i + 1) + " Subject " + cert.getSubjectDN());
		    System.out.println("   Issuer  " + cert.getIssuerDN());
		    sha1.update(cert.getEncoded());
		    System.out.println("   sha1    " + toHexString(sha1.digest()));
		    md5.update(cert.getEncoded());
		    System.out.println("   md5     " + toHexString(md5.digest()));
		    System.out.println();
		}
	
		System.out.println("Enter certificate to add to trusted keystore or 'q' to quit: [1]");
		String line = reader.readLine().trim();
		int k;
		try {
		    k = (line.length() == 0) ? 0 : Integer.parseInt(line) - 1;
		} catch (NumberFormatException e) {
		    System.out.println("KeyStore not changed");
		    return;
		}
	
		X509Certificate cert = chain[k];
		String alias = host + "-" + (k + 1);
		ts.setCertificateEntry(alias, cert);
	
		OutputStream out = new FileOutputStream(EXTRACTED_KEYSTORE_LOCATION);
		ts.store(out, passphrase);
		out.close();
	
		System.out.println();
		System.out.println(cert);
		System.out.println();
		System.out.println
			("Added certificate to keystore 'jssecacerts' using alias '"
			+ alias + "'");
	    }
	    
	    /**
	     * Abre o armazém de chaves confiéveis.
	     * 
	     * @param passphrase
	     * 
	     * @throws Exception
	     */
	    public static KeyStore openTrustStore(char[] passphrase) throws Exception {
	    	File file = new File("jssecacerts");
	    	if (file.isFile() == false) {
	    	    char SEP = File.separatorChar;
	    	    File dir = new File(System.getProperty("java.home") + SEP
	    		    + "lib" + SEP + "security");
	    	    file = new File(dir, "jssecacerts");
	    	    if (file.isFile() == false) {
	    		file = new File(dir, "cacerts");
	    	    }
	    	}
	    	System.out.println("Loading KeyStore " + file + "...");
	    	InputStream in = new FileInputStream(file);
	    	KeyStore ts = KeyStore.getInstance(KeyStore.getDefaultType());
	    	ts.load(in, passphrase);
	    	in.close();
	    	return ts;
	    }
	    
	    /**
	     * Gerenciador de chaves do usuário.
	     * 
	     * @param ts
	     * @param keyStoreLocation
	     * @param passphrase
	     * 
	     * @throws Exception
	     */
	    public static X509TrustManager openTrustManager(KeyStore ts, String keyStoreLocation, char[] passphrase) throws Exception {
	    	KeyStore ksKeys = KeyStore.getInstance("pkcs12");
	    	ksKeys.load(new FileInputStream(keyStoreLocation), passphrase);
	
	    	KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
	    	kmf.init(ksKeys, passphrase);
	    	
	    	TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	    	tmf.init(ts);
	    	X509TrustManager tm = new TrustManagerDecorator((X509TrustManager) tmf.getTrustManagers()[0], kmf);
	    	return tm;
	    }
	    
	    /**
	     * Uma fébrica de conexées SSL.
	     * 
	     * @param tm
	     * @param host
	     * @param port
	     * 
	     * @throws Exception
	     */
	    public static SSLSocket customSocketFactory(TrustManagerDecorator tm, String host, int port) throws Exception {
	    	SSLContext context = tm.createSSLContext();
	    	SSLSocketFactory factory = context.getSocketFactory();
	    	System.out.println("Opening connection to " + host + ":" + port + "...");
	    	SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
	    	return socket;
	    }
	
	    private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();
	
	    
	    /**
	     * Queremos somente caracteres que o usuário possa ler...
	     * 
	     * @param bytes
	     */
	    private static String toHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 3);
		for (int b : bytes) {
		    b &= 0xff;
		    sb.append(HEXDIGITS[b >> 4]);
		    sb.append(HEXDIGITS[b & 15]);
		    sb.append(' ');
		}
		return sb.toString();
	    }
	
	    /**
	     * Decorador para preservar a cadeia de confianéa durante a verificação e
	     * auxiliar na criação de contextos SSL.
	     */
	    private static class TrustManagerDecorator implements X509TrustManager {
	
			private final X509TrustManager tm;
			private X509Certificate[] chain;
			private KeyManagerFactory kmf;
		
			TrustManagerDecorator(X509TrustManager tm, KeyManagerFactory kmf) {
			    this.tm = tm;
			    this.kmf = kmf;
			}
		
			// not used
			public X509Certificate[] getAcceptedIssuers() {
			    throw new UnsupportedOperationException();
			}
		
			// not used
			public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			    throw new UnsupportedOperationException();
			}
		
			public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			    this.chain = chain;
			    tm.checkServerTrusted(chain, authType);
			}
			
			public SSLContext createSSLContext() throws Exception {
		    	SSLContext context = SSLContext.getInstance("TLS");
		    	context.init(kmf.getKeyManagers(), new TrustManager[] {tm}, null);
		    	return context;
			}
	    }
	
	}
