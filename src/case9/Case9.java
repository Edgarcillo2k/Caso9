package case9;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Case9 
{
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        RSA keyPairGenerator = new RSA();
        keyPairGenerator.writeToFile("RSA/publicKey", keyPairGenerator.getPublicKey().getEncoded());
        keyPairGenerator.writeToFile("RSA/privateKey", keyPairGenerator.getPrivateKey().getEncoded());
        System.out.println(Base64.getEncoder().encodeToString(keyPairGenerator.getPublicKey().getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(keyPairGenerator.getPrivateKey().getEncoded()));
    }
}
