package case9;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Case9 
{
	public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IOException {
        RSAUtil rsaUtil = new RSAUtil();
		try {
            String encryptedString = Base64.getEncoder().encodeToString(rsaUtil.encrypt("Dhiraj is the author"));
            System.out.println(encryptedString);
            String decryptedString = RSAUtil.decrypt(encryptedString);
            System.out.println(decryptedString);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }
    }
}
